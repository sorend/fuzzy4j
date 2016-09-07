/*
 * Copyright (c) 2012, Søren Atmakuri Davidsen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
