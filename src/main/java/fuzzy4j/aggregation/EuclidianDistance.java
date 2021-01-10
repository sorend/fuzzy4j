package fuzzy4j.aggregation;

/**
 * Implementation of an aggregation operator using euclidian distance.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class EuclidianDistance implements Aggregation {

    public static final EuclidianDistance INSTANCE = new EuclidianDistance();

    @Override
    public double apply(double... values) {
        double s = 0.0;
        // sum( (1-a_i)^2 )
        for (int i = 0; i < values.length; i++)
            s += Math.pow(1 - values[i], 2);
        // 1/n * sum
        s /= values.length;
        // 1 - sum^(1/2)
        return 1 - Math.pow(s, 0.5);
    }
}
