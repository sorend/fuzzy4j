package fuzzy4j.aggregation;

/**
 * Maximum (max) aggregation operator
 *
 * Defined as:
 *    <code>M_M(a_1, a_2, ... a_n) = max^n_k=1 a_k</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class Maximum implements Norm, Aggregation {

    public static final Maximum INSTANCE = new Maximum();

    @Override
    public double apply(double... values) {
        return minmax(values)[1];
    }

    @Override
    public Type type() {
        return Type.T_CONORM;
    }

    public String toString() {
        return type() + "_max";
    }

    @Override
    public Norm duality() {
        return Minimum.INSTANCE;
    }

    public static double[] minmax(double[] values) {
        if (values == null || values.length == 0)
            return new double[] { Double.NaN, Double.NaN };
        double min = values[0], max = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < min)
                min = values[i];
            if (values[i] > max)
                max = values[i];
        }
        return new double[]{ min, max };
    }
}
