package fuzzy4j.aggregation.weighted;

/**
 * Andness-directed Implicative-importance Weighted Averaging.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class RollingAIWA implements WeightedRollingAggregation<RollingAIWA> {

    public final double p;
    public final double alpha;

    private double s = 0, d = 0;

    public RollingAIWA(double p) {
        assert 0 <= p && p <= 1;
        this.p = p;
        this.alpha = (1. - p) / p;
    }

    @Override
    public RollingAIWA clear() {
        s = d = 0;
        return this;
    }

    @Override
    public RollingAIWA add(WeightedValue... values) {
        for (int i = 0; i < values.length; i++) {
            if (p <= 0.5) {
                s += Math.pow(values[i].weight * values[i].value, alpha);
                d += Math.pow(values[i].weight, alpha);
            }
            else {
                s += Math.pow(values[i].weight * (1. - values[i].value), 1./alpha);
                d += Math.pow(values[i].weight, 1./alpha);
            }
        }
        return this;
    }

    @Override
    public double value() {
        if (p <= .5)
            return Math.pow(s / d, 1. / alpha);
        else
            return 1. - Math.pow(s / d, alpha);
    }
}
