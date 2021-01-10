package fuzzy4j.aggregation;

import fuzzy4j.util.ParametersUtil;

/**
 * Drastic intersection operator.
 *
 * Defined as:
 *    <code>I_D(a, b) = b if a = 1, a if b = 1, 0 otherwise</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class DrasticIntersection implements Norm {

    public static final DrasticIntersection INSTANCE = new DrasticIntersection();

    private DrasticIntersection() {
    }

    @Override
    public double apply(double... values) {

        ParametersUtil.assertTwoParameters(this.getClass(), "apply", values);

        double a = values[0];
        double b = values[1];

        return a == 1.0 ? b : b == 1.0 ? a : 0.0;
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
