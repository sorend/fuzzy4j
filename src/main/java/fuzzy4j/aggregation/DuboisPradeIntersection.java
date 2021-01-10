package fuzzy4j.aggregation;

import fuzzy4j.util.ParametersUtil;

/**
 * Implements the Dubois-Prade aggregation operator.
 *
 * Defined as: <code>t_dp(a, b) = (a * b) / max(a, b, p)</code>
 *
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class DuboisPradeIntersection implements Norm {

    /**
     * Construct dubois prade using the parametricfactory interface.
     */
    public static final ParametricFactory<DuboisPradeIntersection> FACTORY = new ParametricFactory<DuboisPradeIntersection>() {
        @Override
        public DuboisPradeIntersection create(double... params) {
            if (params == null || params.length != 1)
                throw new IllegalArgumentException("one parameter expected (p in [0, 1])");
            return new DuboisPradeIntersection(params[0]);
        }
    };

    public final double p;

    public DuboisPradeIntersection(double p) {
        if (p < 0 || p > 1)
            throw new IllegalArgumentException("requires p in [0, 1]");
        this.p = p;
    }

    @Override
    public double apply(double... values) {
        ParametersUtil.assertTwoParameters(DuboisPradeIntersection.class, "apply", values);
        return (values[0] * values[1]) / Math.max(Math.max(values[0], values[1]), p);
    }

    @Override
    public Type type() {
        return Type.T_NORM;
    }

    @Override
    public Norm duality() {
        return new DeMorganDuality(this);
    }

    public String toString() {
        return type().toString() + "_dp";
    }
}
