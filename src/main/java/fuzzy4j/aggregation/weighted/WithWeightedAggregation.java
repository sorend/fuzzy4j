package fuzzy4j.aggregation.weighted;

import fuzzy4j.aggregation.Aggregation;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Wraps a weighted aggregation in the unweighted case, by providing the weights explicitly.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class WithWeightedAggregation implements Aggregation {

    private Logger LOG = Logger.getLogger(WithWeightedAggregation.class.getName());

    private WeightedAggregation inner;
    private double[] weights;

    public WithWeightedAggregation(WeightedAggregation inner, double... weights) {
        this.inner = inner;
        this.weights = weights;
    }

    @Override
    public double apply(double... values) {
        if (values.length != weights.length)
            LOG.warning("values.length != weights.length");
        int min = Math.min(values.length, weights.length);
        WeightedValue[] weightedValues = new WeightedValue[min];
        for (int i = 0; i < min; i++)
            weightedValues[i] = WeightedValue.w(weights[i], values[i]);
        return inner.apply(weightedValues);
    }

    @Override
    public String toString() {
        return "weighted(" + Arrays.asList(weights) + ", " + inner + ")";
    }
}
