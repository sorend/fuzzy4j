package fuzzy4j.aggregation.weighted;

import fuzzy4j.aggregation.Mean;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Sets default weight, to allow unweighted aggregations to use a weighted aggregation.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class WithDefaultWeightedMean implements Mean {

    private Logger LOG = Logger.getLogger(WithDefaultWeightedMean.class.getName());

    private WeightedMean inner;
    private double defaultWeight;

    public WithDefaultWeightedMean(WeightedMean inner, double defaultWeight) {
        this.inner = inner;
        this.defaultWeight = defaultWeight;
    }

    @Override
    public double compute(double... values) {
        double[] weights = new double[values.length];
        Arrays.fill(weights, defaultWeight);

        WeightedValue[] weightedValues = new WeightedValue[values.length];
        for (int i = 0; i < values.length; i++)
            weightedValues[i] = WeightedValue.w(weights[i], values[i]);
        return inner.compute(weightedValues);
    }

    @Override
    public String toString() {
        return "weighted(def=" + defaultWeight + ", " + inner + ")";
    }
}
