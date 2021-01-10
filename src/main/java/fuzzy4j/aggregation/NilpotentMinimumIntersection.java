package fuzzy4j.aggregation;

import fuzzy4j.util.ParametersUtil;

/**
 * Nilpotent minimum intersection operator.
 *
 * Defined as:
 *    <code>I_L(a, b) = min(a, b) if a+b > 1, 0 otherwise</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class NilpotentMinimumIntersection implements Norm {

    public static final NilpotentMinimumIntersection INSTANCE = new NilpotentMinimumIntersection();

    @Override
    public double apply(double... values) {

        ParametersUtil.assertTwoParameters(this.getClass(), "apply", values);

        return values[0] + values[1] > 1.0 ? Math.min(values[0], values[1]) : 0.0;
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
