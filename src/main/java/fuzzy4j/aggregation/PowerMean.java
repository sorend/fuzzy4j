package fuzzy4j.aggregation;

/**
 * Aggregation operator using power-mean [1]. The power mean is also commonly called generalized mean.
 *
 * Defined as:
 *   <code>M_p(a_1, a_2, .. a_n) = ( 1/n Sum^n_k=1 a_n^p )^(1/p)</code>
 *
 * Where "p" controls order of power.
 *
 *
 * [1] Wolfram Alpha, Power Mean, http://mathworld.wolfram.com/PowerMean.html, 2011
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class PowerMean implements Aggregation {

    public static ParametricFactory<PowerMean> FACTORY = new ParametricFactory<PowerMean>() {
        @Override
        public PowerMean create(double... params) {
            if (params == null || params.length != 1)
                throw new IllegalArgumentException("one parameter expected (p)");
            return new PowerMean(params[0]);
        }
    };

    public final double p;

    public PowerMean(double p) {
        this.p = p;
    }

    @Override
    public double apply(double... values) {
        if (values == null || values.length == 0)
            return Double.NaN;
        double s = 0.0;
        for (int i = 0; i < values.length; i++) {
            s += Math.pow(values[i], p);
        }
        return Math.pow(s / values.length, 1.0 / p);
    }
}
