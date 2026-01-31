package fuzzy4j.aggregation.weighted;

import fuzzy4j.aggregation.Mean;

/**
 * Wrap an unweighted aggregation as a weighted one (disregarding all weights)
 *
 * Soren A. Davidsen (sorend@gmail.com)
 */
public class AsWeightedMean implements WeightedMean {

    public static AsWeightedMean asWeighted(Mean inner) {
        return new AsWeightedMean(inner);
    }

    private Mean inner;

    public AsWeightedMean(Mean inner) {
        this.inner = inner;
    }

    @Override
    public double compute(WeightedValue... weightedValues) {
        double[] values = new double[weightedValues.length];
        for (int i = 0; i < weightedValues.length; i++)
            values[i] = weightedValues[i].value();
        return inner.compute(values);
    }
}
