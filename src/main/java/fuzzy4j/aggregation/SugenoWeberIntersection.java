package fuzzy4j.aggregation;

import fuzzy4j.util.ParametersUtil;

/**
 * Sugeno-Weber class intersection operator.
 *
 * To get a union operator, use {@link fuzzy4j.aggregation.SugenoWeberIntersection#duality()}.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class SugenoWeberIntersection implements Norm {

    public static final ParametricFactory<Norm> T_NORM = new ParametricFactory<Norm>() {
        @Override
        public Norm create(double... params) {
            double p = params[0];
            if (p < -1.)
                throw new IllegalArgumentException("requires p >= -1");
            else if (p == 1.)
                return DrasticIntersection.INSTANCE;
            else if (Double.isInfinite(p))
                return AlgebraicProduct.INSTANCE;
            else
                return new SugenoWeberIntersection(p);
        }
    };

    public static final ParametricFactory INTERSECTION = T_NORM;

    private double p;

    public SugenoWeberIntersection(double p) {
        this.p = p;
    }

    @Override
    public double apply(double... values) {

        ParametersUtil.assertTwoParameters(this.getClass(), "apply", values);

        double a = values[0];
        double b = values[1];

        return Math.max(0., (a + b - 1. + (p * a * b)) / (1. + p));
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
