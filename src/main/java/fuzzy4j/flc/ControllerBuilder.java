package fuzzy4j.flc;

import fuzzy4j.aggregation.Mean;
import fuzzy4j.aggregation.Minimum;
import fuzzy4j.flc.defuzzify.Centroid;
import fuzzy4j.flc.defuzzify.Defuzzify;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of a fuzzy logic controller.
 *
 * Soren (sorend@gmail.com)
 */
public class ControllerBuilder {

    public static <V> Map<V, Double> map(V[] keys, double[] values) {
        Map<V, Double> m = new LinkedHashMap<V, Double>();
        for (int i = 0; i < keys.length; i++)
            m.put(keys[i], values[i]);
        return m;
    }

    public static ControllerBuilder newBuilder() {
        return new ControllerBuilder();
    }

    private Mean tnorm = Minimum.INSTANCE;
    private Mean tconorm = fuzzy4j.aggregation.Maximum.INSTANCE;
    private Mean activationNorm = Minimum.INSTANCE;
    private Mean accumulationTconorm = fuzzy4j.aggregation.Maximum.INSTANCE;
    private Defuzzify defuzzifier = new Centroid();

    private List<IfThenRule> rules = new ArrayList();

    private ControllerBuilder() {
    }

    public List<IfThenRule> getRules() {
        return rules;
    }

    public ControllerBuilder andFunction(Mean aggregation) {
        this.tnorm = aggregation;
        return this;
    }

    public ControllerBuilder orFunction(Mean aggregation) {
        this.tconorm = aggregation;
        return this;
    }

    public ControllerBuilder activationFunction(Mean aggregation) {
        this.activationNorm = aggregation;
        return this;
    }

    public ControllerBuilder accumulationFunction(Mean aggregation) {
        this.accumulationTconorm = aggregation;
        return this;
    }

    public ControllerBuilder defuzzifier(Defuzzify defuzzifier) {
        this.defuzzifier = defuzzifier;
        return this;
    }

    public RuleBuilder when() {
        return new RuleBuilder(this);
    }

    public ControllerBuilder addRule(IfThenRule rule) {
        this.rules.add(rule);
        return this;
    }

    public FLC create() {
        return new FLC(tnorm, tconorm,
                activationNorm,
                accumulationTconorm,
                defuzzifier,
                rules);
    }

}
