package fuzzy4j.aggregation;

/**
 * Mean operator using arithmetic mean.
 *
 * Defined as:
 *    <code>M_A(a_1, a_2, ... a_n) = 1/n Sum^n_k=1 a_k</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class ArithmeticMean implements Mean {

    public static ArithmeticMean INSTANCE = new ArithmeticMean();

    @Override
    public double compute(double... values) {
        if (values == null || values.length == 0)
            return Double.NaN;
        double s = 0.0;
        for (int i = 0; i < values.length; i++)
            s += values[i];
        return s / values.length;
    }

    @Override
    public String toString() {
        return "h_{arithmetic}";
    }
}
