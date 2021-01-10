package fuzzy4j.aggregation;

/**
 * Yager class aggregation operator.
 *
 * Defined as:
 *      <code>T_Y(p, a_1, a_2, ... a_n) = 1 - min(1, (Sum^n_k=1 (1 - a_k)^p)^(1/p))</code>
 *
 * To get the T-CoNorm operator use {@link fuzzy4j.aggregation.YagerIntersection#duality()}.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class YagerIntersection implements Norm {

    public static final ParametricFactory<Norm> FACTORY = new ParametricFactory<Norm>() {
        @Override
        public Norm create(double... params) {
            double p = params[0];
            if (p < 0.0)
                throw new IllegalArgumentException("p < 0");
            else if (p == 0.0)
                return DrasticIntersection.INSTANCE;
            else if (Double.isInfinite(p))
                return Minimum.INSTANCE;
            else
                return new YagerIntersection(p);
        }
    };

    private double p;

    private YagerIntersection(double p) {
        this.p = p;
    }

    @Override
    public double apply(double... values) {
        double s = 0.0;
        for (int i = 0; i < values.length; i++) {
            s += Math.pow(1.0 - values[i], p);
        }
        return 1.0 - Math.min(1, Math.pow(s, 1.0 / p));
    }

    @Override
    public Type type() {
        return Type.T_NORM;
    }

    @Override
    public Norm duality() {
        return new DeMorganDuality(this);
    }

}
