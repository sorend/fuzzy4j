package fuzzy4j.aggregation.weighted;

import fuzzy4j.aggregation.Aggregation;

/**
 * Aggregation operator using arithmetic mean.
 *
 * Defined as:
 *    <code>h_WAM((w_1, a_1), (w_2, a_2), ... (w_n, a_n)) = (Sum^n_i=1 w_i * a_i) / (Sum^n_i=1 w_i)</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class WeightedArithmeticMean implements WeightedAggregation {
    @Override
    public double apply(WeightedValue... values) {

        double s = 0.0, ws = 0.0;
        for (int i = 0; i < values.length; i++) {
            s += values[i].value * values[i].weight;
            ws += values[i].weight;
        }

        return s / ws;
    }
}
