package fuzzy4j.aggregation;

/**
 * Aggregation operator quadratic mean (more commonly called Root Mean Square)
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class QuadraticMean implements Aggregation {

    public static QuadraticMean INSTANCE = new QuadraticMean();

    @Override
    public double apply(double... values) {
        if (values == null || values.length == 0)
            return Double.NaN;
        double ss = 0.0;
        for (int i = 0; i < values.length; i++)
            ss += values[i] * values[i];
        return Math.sqrt(ss / values.length);
    }

    @Override
    public String toString() {
        return "h_{rms}";
    }
}
