package fuzzy4j.aggregation.weighted;

/**
 * Andness-directed Weighted Averaging operator [1].
 *
 * The AWA operator is controlled by two parameters:
 *  "p" controls the andness.
 *  "l" controls the function between multiplicative (l = 0) and implicative (l = 1) semantics.
 *
 * [1] H. L. Larsen, "Multiplicative and implicative importance weighted averaging aggregation operators with accurate andness direction",
 *      In Proc. of IFSA-EUSFLAT 2009, 2009.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class AWA implements WeightedAggregation {

    public final double p;
    public final double l;
    // public final double y_exp;
    // public final double alpha;

    public AWA(double p, double l) {
        this.p = p;
        this.l = l;
        // this.alpha = (1. - p) / p;
        // this.y_exp = Math.pow(alpha, l);
    }

    @Override
    public double apply(WeightedValue... values) {

        if (p <= .5) {
            double alpha = (1. - p) / p;
            double y_exp = Math.pow(alpha, l);
            return doCalc(y_exp, alpha, values);
        }
        else {
            WeightedValue[] invV = new WeightedValue[values.length];
            for (int i = 0; i < values.length; i++)
                invV[i] = WeightedValue.w(values[i].weight, 1. - values[i].value);

            double alpha = (1. - (1. - p)) / (1. - p);
            double y_exp = Math.pow(alpha, l);

            return 1. - doCalc(y_exp, alpha, invV);
        }
    }

    private static double doCalc(double y_exp, double alpha, WeightedValue... values) {
        double y_s = 0.0;
        for (int i = 0; i < values.length; i++)
            y_s += Math.pow(values[i].weight, y_exp);

        double s = 0.;
        for (int i = 0; i < values.length; i++) {
            s += (Math.pow(values[i].weight, y_exp) / y_s) * Math.pow(values[i].value, alpha);
        }
        return Math.pow(s, 1. / alpha);
    }

    @Override
    public String toString() {
        return String.format("AWA(p: %f, l: %f)", p, l);
    }
}