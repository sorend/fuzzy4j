package fuzzy4j.flc;

import fuzzy4j.aggregation.Aggregation;
import fuzzy4j.flc.defuzzify.Defuzzify;
import fuzzy4j.sets.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of a fuzzy logic controller.
 *
 * @see ControllerBuilder
 * @author Soren <sorend@gmail.com>
 */
public class FLC {

    private Logger LOG = Logger.getLogger(FLC.class.getName());

    private Variable[] inputs;
    private Variable[] outputs;

    private Aggregation tnorm;
    private Aggregation tconorm;
    private Aggregation activationNorm;
    private Aggregation accumulationTconorm;
    private Defuzzify defuzzifier;
    private List<IfThenRule> rules;

    public FLC(Aggregation tnorm,
               Aggregation tconorm,
               Aggregation activationNorm,
               Aggregation accumulationTconorm,
               Defuzzify defuzzifier,
               List<IfThenRule> rules) {
        this.tnorm = tnorm;
        this.tconorm = tconorm;
        this.activationNorm = activationNorm;
        this.accumulationTconorm = accumulationTconorm;
        this.defuzzifier = defuzzifier;
        this.rules = rules;
    }

    public Map<Variable, Double> apply(final InputInstance input) {

        Map<Variable, FuzzyFunction> fuzzyResult = applyFuzzy(input);

        Map<Variable, Double> res = new HashMap();
        for (Map.Entry<Variable, FuzzyFunction> entry : fuzzyResult.entrySet()) {
            double value = defuzzifier.apply(entry.getKey(), entry.getValue());
            res.put(entry.getKey(), value);
        }

        return res;
    }

    public Map<Variable, FuzzyFunction> applyFuzzy(final InputInstance input) {

        // create accessor for the instance.
        InputInstanceAccessor accessor = new InputInstanceAccessor() {
            @Override
            public double valueOf(Variable variable, Term label) {
                Double v = input.get(variable);
                if (v == null)
                    return 0.0;
                else
                    return label.set.apply(v);
            }

            @Override
            public Aggregation orFunction() {
                return FLC.this.tconorm;
            }

            @Override
            public Aggregation andFunction() {
                return FLC.this.tnorm;
            }
        };

        Map<Variable, List<FuzzyFunction>> varOutput = new LinkedHashMap();

        // loop through the rules
        for (IfThenRule rule : rules) {

            // apply the rule
            double firingStrength = rule.rule.apply(accessor);

            if (LOG.isLoggable(Level.FINE))
                LOG.fine("\trule(" + rule + ") -> " + firingStrength);

            if (firingStrength > 0.0) {
                // use aggregation on the
                if (!varOutput.containsKey(rule.then.variable))
                    varOutput.put(rule.then.variable, new ArrayList());

                // add the bounded set to the outputs.
                varOutput.get(rule.then.variable)
                        .add(new CompositeFunction(activationNorm,
                                rule.then.value.set,
                                new ConstantFunction(firingStrength)));
            }
        }

        // aggregate the outputs.
        Map<Variable, FuzzyFunction> result = new HashMap();
        for (Map.Entry<Variable, List<FuzzyFunction>> entry : varOutput.entrySet()) {
            FuzzyFunction cf = new CompositeFunction(accumulationTconorm,
                    entry.getValue().toArray(new FuzzyFunction[entry.getValue().size()]));
            result.put(entry.getKey(), cf);
        }

        return result;
    }
}
