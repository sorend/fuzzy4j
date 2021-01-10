package fuzzy4j.aggregation;

import fuzzy4j.util.ParametersUtil;

/**
 * HamacherProduct intersection operator.
 *
 * Defined as:
 *    <code>I_L(a, b) = min(a, b) if a+b > 1, 0 otherwise</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class HamacherIntersection implements Norm {

    public static final ParametricFactory<Norm> FACTORY = new ParametricFactory<Norm>() {
        @Override
        public Norm create(double... params) {
            double p = params[0];
            if (p < 0.0)
                throw new IllegalArgumentException("p < 0");
            else if (Double.isInfinite(p))
                return DrasticIntersection.INSTANCE;
            else
                return new HamacherIntersection(p);
        }
    };

    public static final Norm HAMACHER_PRODUCT = FACTORY.create(0.0);

    private double p;

    private HamacherIntersection(double p) {
        this.p = p;
    }

    @Override
    public double apply(double... values) {

        ParametersUtil.assertTwoParameters(this.getClass(), "apply", values);

        double a = values[0];
        double b = values[1];

        if (a == 0.0 && b == 0.0 && p == 0.0)
            return 0.0;
        else
            return (a * b) / (p + (1 - p) * (a + b + (a * b)));
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
