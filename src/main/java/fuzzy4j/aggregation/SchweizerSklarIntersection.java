package fuzzy4j.aggregation;

import fuzzy4j.util.ParametersUtil;

/**
 * Schweizer-Sklar intersection operator.
 *
 * Defined as:
 *    <code>I_L(p, a, b) = max(a^p + b^p - 1, 0)^(1/p)</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class SchweizerSklarIntersection implements Norm {

    public static final ParametricFactory<Norm> FACTORY = new ParametricFactory<Norm>() {
        @Override
        public Norm create(double... params) {
            double p = params[0];
            if (Double.isInfinite(p)) {
                if (p == Double.NEGATIVE_INFINITY)
                    return Minimum.INSTANCE;
                else
                    return DrasticIntersection.INSTANCE;
            }
            else if (p == 0.0)
                return AlgebraicProduct.INSTANCE;
            else
                return new SchweizerSklarIntersection(p);
        }
    };

    private double p;

    public SchweizerSklarIntersection(double p) {
        this.p = p;
    }

    @Override
    public double apply(double... values) {

        ParametersUtil.assertTwoParameters(this.getClass(), "apply", values);

        double a = values[0];
        double b = values[1];

        return Math.pow(Math.max(0.0, Math.pow(a, p) + Math.pow(b, p) - 1.0), 1.0/p);
    }

    @Override
    public Type type() {
        return Type.T_NORM;
    }

    /**
     * The duality operator is based on the DeMorganDuality.
     *
     * @return
     */
    @Override
    public Norm duality() {
        return new DeMorganDuality(this);
    }
}
