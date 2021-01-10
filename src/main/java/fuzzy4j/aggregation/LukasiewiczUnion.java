package fuzzy4j.aggregation;

import fuzzy4j.util.ParametersUtil;

/**
 * Łukasiewicz intersection operator.
 *
 * Defined as:
 *    <code>U_L(a, b) = min(a + b, 1)</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class LukasiewiczUnion implements Norm {

    public static final LukasiewiczUnion INSTANCE = new LukasiewiczUnion();

    @Override
    public double apply(double... values) {

        ParametersUtil.assertTwoParameters(this.getClass(), "apply", values);

        return Math.min(values[0] + values[1], 1.0);
    }

    @Override
    public Type type() {
        return Type.T_CONORM;
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
        return LukasiewiczIntersection.INSTANCE;
    }
}
