package fuzzy4j.aggregation;

import java.util.Arrays;

/**
 * Median "mean" aggregation operator. Will take the middle of the vector of numbers.
 *
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class Median implements Aggregation {

    public static Median INSTANCE = new Median();

    @Override
    public double apply(double... values) {
        if (values == null || values.length == 0)
            return Double.NaN;
        Arrays.sort(values);
        int middle = values.length / 2;
        if (values.length % 2 == 0)
            return (values[middle] + values[middle - 1]) / 2;
        else
            return values[middle];
    }

    public String toString() {
        return "h_{median}";
    }
}
