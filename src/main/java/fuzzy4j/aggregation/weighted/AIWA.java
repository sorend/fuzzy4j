package fuzzy4j.aggregation.weighted;

/**
 * Andness-directed Implicative-importance Weighted Averaging.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class AIWA implements WeightedAggregation {

    public final double p;
    public final double alpha;

    public AIWA(double p) {
        assert 0 <= p && p <= 1;
        this.p = p;
        this.alpha = (1. - p) / p;
    }

    @Override
    public double apply(WeightedValue... values) {

        if (p <= .5) {
            double s = 0.;
            double d = 0.;
            for (int i = 0; i < values.length; i++) {
                s += Math.pow(values[i].weight * values[i].value, alpha);
                d += Math.pow(values[i].weight, alpha);
            }
            return Math.pow(s / d, 1. / alpha);
        }
        else {
            double s = 0.;
            double d = 0.;
            for (int i = 0; i < values.length; i++) {
                s += Math.pow(values[i].weight * (1. - values[i].value), 1./alpha);
                d += Math.pow(values[i].weight, 1./alpha);
            }
            return 1. - Math.pow(s / d, alpha);
        }
    }
}
