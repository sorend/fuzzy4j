package fuzzy4j.aggregation;

import fuzzy4j.util.ParametersUtil;

/**
 * Einstein union operator.
 *
 * Defined as:
 *    <code>U_E(a, b) = (a + b) / (1 + (ab))</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class EinsteinUnion implements Norm {

    public static final EinsteinUnion INSTANCE = new EinsteinUnion();

    @Override
    public double apply(double... values) {

        ParametersUtil.assertTwoParameters(this.getClass(), "apply", values);

        double a = values[0];
        double b = values[1];

        return (a + b) / (1 + (a * b));
    }

    @Override
    public Type type() {
        return Type.T_CONORM;
    }

    public String toString() {
        return type() + "_einstein";
    }

    /**
     * The duality operator is based on the DeMorganDuality.
     *
     * @return
     */
    @Override
    public Norm duality() {
        return EinsteinIntersection.INSTANCE;
    }
}
