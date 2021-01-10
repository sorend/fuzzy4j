package fuzzy4j.aggregation;

/**
 * Harmonic mean aggregation operator [1]
 *
 * Defined as:
 *      M_H(a_1, a_2, ... a_n) = 1/n Sum^n_k=1 1/a_k
 *
 * [1] Wolfram Alpha, "HarmonicMean", <a href="http://mathworld.wolfram.com/HarmonicMean.html">http://mathworld.wolfram.com/HarmonicMean.html</a>, 2011
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class HarmonicMean implements Aggregation {

    public static HarmonicMean INSTANCE = new HarmonicMean();

    @Override
    public double apply(double... values) {
        if (values == null || values.length == 0)
            return Double.NaN;
        double s = 0.0;
        for (int i = 0; i < values.length; i++)
            s += (1 / values[i]);
        return values.length / s;
    }

    public String toString() {
        return "h_{harmonic}";
    }
}
