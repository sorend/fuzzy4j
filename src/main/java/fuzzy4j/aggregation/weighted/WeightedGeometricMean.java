package fuzzy4j.aggregation.weighted;

import fuzzy4j.aggregation.Aggregation;

/**
 * Weighted geometric mean aggregation operator.
 *
 * Defined as:
 *      <code>M_WG((w_1, a_1), (w_2, a_2), ... (w_n, a_n)) = exp[ (Sum_i w_i ln a_i) / (Sum_i w_i) ]</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class WeightedGeometricMean implements WeightedAggregation {
    @Override
    public double apply(WeightedValue... values) {
        double n = 0.0, d = 0.0;
        for (int i = 0; i < values.length; i++) {
            n += values[i].weight * Math.log(values[i].value);
            d += values[i].weight;
        }
        return Math.exp(n / d);
    }
}
