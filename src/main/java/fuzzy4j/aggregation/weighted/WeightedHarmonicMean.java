package fuzzy4j.aggregation.weighted;

/**
 * Weighted harmonic mean aggregation operator [1]
 *
 * Defined as:
 *      h_WH((w_1, a_1), (w_2, a_2), ... (w_n, a_n)) = (Sum_i w_i) / (Sum_i w_i/a_i))
 *
 * [1] http://en.wikipedia.org/wiki/Weighted_harmonic_mean#Weighted_harmonic_mean
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class WeightedHarmonicMean implements WeightedAggregation {

    @Override
    public double apply(WeightedValue... values) {
        // check if we have any 0 values (result is always 0 then)
        for (int i = 0; i < values.length; i++)
            if (values[i].value == 0.0)
                return 0.0;

        // calculate
        double n = 0.0;
        double d = 0.0;
        for (int i = 0; i < values.length; i++) {
            n += values[i].weight;
            d += values[i].weight / values[i].value;
        }
        return n / d;
    }
}
