package fuzzy4j.aggregation;

import fuzzy4j.algebra.Norms;
import fuzzy4j.algebra.TNorm;

/**
 * Implementation of Minimum Mean.
 */
public class Minimum implements Mean {

    public static final Minimum INSTANCE = new Minimum();

    @Override
    public double compute(double... values) {
        if (values == null || values.length == 0) return 0.0;
        TNorm norm = Norms.minimum();
        double min = values[0];
        for (int i = 1; i < values.length; i++) {
            min = norm.t(min, values[i]);
        }
        return min;
    }

    public static double of(double... values) {
        return INSTANCE.compute(values);
    }
}
