package fuzzy4j.aggregation.weighted;

import fuzzy4j.aggregation.Aggregation;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Sets default weight, to allow unweighted aggregations to use a weighted aggregation.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class WithDefaultWeightedAggregation implements Aggregation {

    private Logger LOG = Logger.getLogger(WithDefaultWeightedAggregation.class.getName());

    private WeightedAggregation inner;
    private double defaultWeight;

    public WithDefaultWeightedAggregation(WeightedAggregation inner, double defaultWeight) {
        this.inner = inner;
        this.defaultWeight = defaultWeight;
    }

    @Override
    public double apply(double... values) {
        double[] weights = new double[values.length];
        Arrays.fill(weights, defaultWeight);

        WeightedValue[] weightedValues = new WeightedValue[values.length];
        for (int i = 0; i < values.length; i++)
            weightedValues[i] = WeightedValue.w(weights[i], values[i]);
        return inner.apply(weightedValues);
    }

    @Override
    public String toString() {
        return "weighted(def=" + defaultWeight + ", " + inner + ")";
    }
}
