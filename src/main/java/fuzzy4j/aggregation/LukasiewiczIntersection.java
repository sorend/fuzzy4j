package fuzzy4j.aggregation;

import fuzzy4j.util.ParametersUtil;

/**
 * Łukasiewicz intersection operator.
 *
 * Defined as:
 *    <code>I_L(a, b) = max(0, a + b - 1)</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class LukasiewiczIntersection implements Norm {

    public static final LukasiewiczIntersection INSTANCE = new LukasiewiczIntersection();

    @Override
    public double apply(double... values) {

        ParametersUtil.assertTwoParameters(this.getClass(), "apply", values);

        return Math.max(0, values[0] + values[1] - 1);
    }

    @Override
    public Type type() {
        return Type.T_NORM;
    }

    public String toString() {
        return type() + "_lukasiewicz";
    }

    /**
     * The duality operator is based on the DeMorganDuality.
     *
     * @return
     */
    @Override
    public Norm duality() {
        return LukasiewiczUnion.INSTANCE;
    }
}
