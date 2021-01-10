package fuzzy4j.aggregation;

import java.util.Arrays;

/**
 * Aggregation operator using interquartile mean. The interquartile mean, is the arithmetic
 * mean after the 1st and 4th quartile values have been removed.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class TruncatedMean implements Aggregation {

    public static ParametricFactory<TruncatedMean> BY_PERCENTILE = new ParametricFactory<TruncatedMean>() {
        @Override
        public TruncatedMean create(double... params) {
            if (params == null || params.length != 1)
                throw new IllegalArgumentException("requires one parameter (p)");
            return new TruncatedMean(params[0], 1.0 - params[0]);
        }
    };

    public static ParametricFactory<TruncatedMean> BY_RANGE = new ParametricFactory<TruncatedMean>() {
        @Override
        public TruncatedMean create(double... params) {
            if (params == null || params.length != 2)
                throw new IllegalArgumentException("requires two parameters (a, b)");
            return new TruncatedMean(params[0], params[1]);
        }
    };

    public final double truncateLower, truncateUpper;

    public TruncatedMean(double truncateLower, double truncateUpper) {
        if (truncateLower < 0.0 || truncateLower >= 0.5)
            throw new IllegalArgumentException("requires truncateLower in [0, 0.5)");
        if (truncateUpper > 1.0 || truncateUpper <= 0.5)
            throw new IllegalArgumentException("requires truncateUpper in (0.5, 1.0]");
        this.truncateLower = truncateLower;
        this.truncateUpper = truncateUpper;
    }

    @Override
    public double apply(double... values) {
        if (values == null || values.length == 0)
            return Double.NaN;

        Arrays.sort(values);

        int first  = (int) Math.floor(values.length * truncateLower);
        int fourth = (int) Math.ceil(values.length * truncateUpper);

        // delegate to arithmetic mean
        return ArithmeticMean.INSTANCE.apply(Arrays.copyOfRange(values, first, fourth));
    }

    @Override
    public String toString() {
        return "h_{interquartile}";
    }
}
