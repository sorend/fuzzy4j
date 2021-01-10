package fuzzy4j.aggregation;

/**
 * MinMax operator. Takes a weighted average of minimum and maximum.
 *
 * Defined as <code>h_minmax(v) = (1-p) * min(v) + p * max(v)</code>.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class MinMax implements Aggregation {

    public static MinMax MIDRANGE = new MinMax(0.5);

    public static ParametricFactory<MinMax> FACTORY = new ParametricFactory<MinMax>() {
        @Override
        public MinMax create(double... params) {
            if (params == null || params.length != 1)
                throw new IllegalArgumentException("minmax expects one parameter (p)");
            return new MinMax(params[0]);
        }
    };

    private double p;

    public MinMax(double p) {
        if (p < 0 || p > 1)
            throw new IllegalArgumentException("p must be in [0, 1]");
        this.p = p;
    }

    @Override
    public double apply(double... values) {
        double[] minmax = Maximum.minmax(values);
        return (1.-p) * minmax[0] + p * minmax[1];
    }
}
