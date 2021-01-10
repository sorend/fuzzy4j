package fuzzy4j.aggregation;

import fuzzy4j.util.ParametersUtil;

/**
 * M.J. Frank (1970s) intersection operator.
 *
 * Defined as:
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class FrankIntersection implements Norm {

    /**
     * Factory, handles that the FrankIntersection has simpler forms for different values of "p"
     */
    public static final ParametricFactory<Norm> FACTORY = new ParametricFactory<Norm>() {
        @Override
        public Norm create(double... params) {
            double p = params[0];
            if (p < 0.0)
                throw new IllegalArgumentException("p must be > 0");
            else if (p == 0.0)
                return Minimum.INSTANCE;
            else if (p == 1.0)
                return AlgebraicProduct.INSTANCE;
            else if (Double.isInfinite(p))
                return LukasiewiczIntersection.INSTANCE;
            else
                return new FrankIntersection(p);
        }
    };

    private double p;

    private FrankIntersection(double p) {
        this.p = p;
    }

    @Override
    public double apply(double... values) {

        ParametersUtil.assertTwoParameters(this.getClass(), "apply", values);

        double a = values[0];
        double b = values[1];

        return Math.log(1.0 + (Math.pow(p, a) - 1.0)*(Math.pow(p, b) - 1.0) / (p - 1.0)) / Math.log(p);
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
