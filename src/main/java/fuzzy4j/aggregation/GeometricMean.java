package fuzzy4j.aggregation;

/**
 * Geometric Mean aggregation operator.
 *
 * Defined as:
 *      <code>M_G(a_1, a_2, ... a_n) = (Prod^n_k=1 a_k)^(1/n)</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class GeometricMean implements Aggregation, Norm {

    public static final GeometricMean INSTANCE = new GeometricMean();

    private final DeMorganDuality DUAL = new DeMorganDuality(this);

    @Override
    public double apply(double... values) {
        if (values == null || values.length == 0)
            return Double.NaN;
        double p = values[0];
        for (int i = 1; i < values.length; i++)
            p *= values[i];
        return Math.pow(p, 1.0 / values.length);
    }

    @Override
    public Type type() {
        return Type.UNKNOWN;
    }

    @Override
    public Norm duality() {
        return DUAL;
    }

    @Override
    public String toString() {
        return "h_{geometric}";
    }
}
