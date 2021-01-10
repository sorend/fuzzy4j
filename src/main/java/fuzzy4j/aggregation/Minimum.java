package fuzzy4j.aggregation;

/**
 * Minimum (min) aggregation operator.
 *
 * Defined as:
 *    <code>M_M(a_1, a_2, ... a_n) = min^n_k=1 a_k</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class Minimum implements Norm, Aggregation {

    public static final Minimum INSTANCE = new Minimum();

    @Override
    public double apply(double... values) {
        return Maximum.minmax(values)[0];
    }

    @Override
    public Type type() {
        return Type.T_NORM;
    }

    public String toString() {
        return type() + "_min";
    }

    @Override
    public Norm duality() {
        return Maximum.INSTANCE;
    }
}
