package fuzzy4j.aggregation.weighted;

/**
 * Andness-directed Multiplicativ-importance Weighted Averaging.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class AMWA implements WeightedAggregation {

    public final double p;
    public final double alpha;

    public AMWA(double p) {
        this.p = p;
        this.alpha = (1. - p) / p;
    }

    @Override
    public double apply(WeightedValue... values) {

        double s_w = 0.0;
        for (int i = 0; i < values.length; i++)
            s_w += values[i].weight;

        if (p <= .5) {
            double s = 0.0;
            for (int i = 0; i < values.length; i++) {
                double y_i = values[i].weight / s_w;
                s += y_i * Math.pow(values[i].value, alpha);
            }
            return Math.pow(s, 1. / alpha);
        }
        else {
            double s = 0.0;
            double a2 = p / (1. - p);
            for (int i = 0; i < values.length; i++) {
                double y_i = values[i].weight / s_w;
                s += y_i * Math.pow(1. - values[i].value, a2);
            }
            return 1. - Math.pow(s, 1. / a2);
        }
    }
}
