package fuzzy4j.aggregation.weighted;

import fuzzy4j.aggregation.Aggregation;

/**
 * Wrap an unweighted aggregation as a weighted one (disregarding all weights)
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class AsWeightedAggregation implements WeightedAggregation {

    public static AsWeightedAggregation asWeighted(Aggregation inner) {
        return new AsWeightedAggregation(inner);
    }

    private Aggregation inner;

    public AsWeightedAggregation(Aggregation inner) {
        this.inner = inner;
    }

    @Override
    public double apply(WeightedValue... weightedValues) {
        double[] values = new double[weightedValues.length];
        for (int i = 0; i < weightedValues.length; i++)
            values[i] = weightedValues[i].value;
        return inner.apply(values);
    }
}
