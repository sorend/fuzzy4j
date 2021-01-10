package fuzzy4j.aggregation;

import fuzzy4j.util.ParametersUtil;

/**
 * Aczel-Alsina class intersection operator.
 *
 * To get a union operator, use {@link fuzzy4j.aggregation.AczelAlsinaIntersection#duality()}.
 *
 * Aczel-Alsina takes a single parameter "p" which must be in [0, inf]
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class AczelAlsinaIntersection implements Norm {

    public static final ParametricFactory<Norm> T_NORM = new ParametricFactory<Norm>() {
        @Override
        public Norm create(double... params) {
            if (params == null || params.length != 1)
                throw new IllegalArgumentException("One parameter expected (p)");
            double p = params[0];
            if (p == 0.)
                return DrasticIntersection.INSTANCE;
            else if (Double.isInfinite(p))
                return Minimum.INSTANCE;
            else
                return new AczelAlsinaIntersection(p);
        }
    };

    public static final ParametricFactory FACTORY = T_NORM;

    private double p;

    public AczelAlsinaIntersection(double p) {
        if (p < 0.0)
            throw new IllegalArgumentException("expected p >= 0");
        this.p = p;
    }

    @Override
    public double apply(double... values) {

        ParametersUtil.assertTwoParameters(this.getClass(), "apply", values);

        double a = values[0];
        double b = values[1];

        double lap = Math.pow(-Math.log(a), p);
        double lbp = Math.pow(-Math.log(b), p);
        // double lap = Math.pow(Math.abs(Math.log(a)), p);
        // double lbp = Math.pow(Math.abs(Math.log(b)), p);

        // System.out.println("lap " + lap + " lbp " + lbp);

        return Math.exp(-Math.pow(lap + lbp, 1.0/p));
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
