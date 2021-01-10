package fuzzy4j.aggregation;

import fuzzy4j.util.ParametersUtil;

/**
 * Einstein intersection operator.
 *
 * Defined as:
 *    <code>I_E(a, b) = (ab) / (2 - (a + b - ab)</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class EinsteinIntersection implements Norm {

    public static final EinsteinIntersection INSTANCE = new EinsteinIntersection();

    @Override
    public double apply(double... values) {

        ParametersUtil.assertTwoParameters(this.getClass(), "apply", values);

        double a = values[0];
        double b = values[1];

        return (a * b) / (2 - (a + b - (a * b)));
    }

    @Override
    public Type type() {
        return Type.T_NORM;
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
        return EinsteinUnion.INSTANCE;
    }
}
