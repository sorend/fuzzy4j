package fuzzy4j.aggregation;

/**
 * Minimum (min) aggregation operator.
 *
 * Defined as:
 *    <code>M_M(a_1, a_2, ... a_n) = min^n_k=1 a_k</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class Minimum implements Aggregation {

    public static final Minimum INSTANCE = new Minimum();

    @Override
    public double apply(double... values) {
        return Maximum.minmax(values)[0];
    }

    public String toString() {
        return "T_NORM_min";
    }
}
