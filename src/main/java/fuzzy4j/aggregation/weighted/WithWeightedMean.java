package fuzzy4j.aggregation.weighted;

import fuzzy4j.aggregation.Mean;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Wraps a weighted aggregation in the unweighted case, by providing the weights explicitly.
 *
 * Soren A. Davidsen (sorend@gmail.com)
 */
public class WithWeightedMean implements Mean {

    private Logger LOG = Logger.getLogger(WithWeightedMean.class.getName());

    private WeightedMean inner;
    private double[] weights;

    public WithWeightedMean(WeightedMean inner, double... weights) {
        this.inner = inner;
        this.weights = weights;
    }

    @Override
    public double compute(double... values) {
        if (values.length != weights.length)
            LOG.warning("values.length != weights.length");
        int min = Math.min(values.length, weights.length);
        WeightedValue[] weightedValues = new WeightedValue[min];
        for (int i = 0; i < min; i++)
            weightedValues[i] = WeightedValue.w(weights[i], values[i]);
        return inner.compute(weightedValues);
    }

    @Override
    public String toString() {
        return "weighted(" + Arrays.asList(weights) + ", " + inner + ")";
    }
}
