package fuzzy4j.aggregation;

import fuzzy4j.algebra.Norms;
import fuzzy4j.algebra.SNorm;

/**
 * Implementation of Maximum Mean.
 */
public class Maximum implements Mean {

    public static final Maximum INSTANCE = new Maximum();

    @Override
    public double compute(double... values) {
        if (values == null || values.length == 0) return 0.0;
        SNorm norm = Norms.maximum();
        double max = values[0];
        for (int i = 1; i < values.length; i++) {
            max = norm.s(max, values[i]);
        }
        return max;
    }

    public static double[] minmax(double... values) {
        if (values == null || values.length == 0) return new double[]{Double.NaN, Double.NaN};
        double min = values[0];
        double max = values[0];
        for (int i = 1; i < values.length; i++) {
            min = Math.min(min, values[i]);
            max = Math.max(max, values[i]);
        }
        return new double[]{min, max};
    }
}
