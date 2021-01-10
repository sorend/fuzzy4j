package fuzzy4j.sets;

/**
 * Pi apply function. Called Pi function because it's shape looks like Pi (uppercase).
 *
 * <pre>
 * {@code y
 *    A
 *  1-|          .-------.
 *    |         /    :    \
 *    |       _/     :     \_
 *    |     _/       :       \_
 * -0-+----'---------:---------'------> x
 *    |   a     p    r    q     b
 * }
 * </pre>
 *
 * [1] Saroj K. Meher, "A new Fuzzy Supervised Classification Method based on Aggregation Operator",
 *  in 3rd Int. IEEE Conf. on Signal-Image Tech. and Internet-Based Systems, 2008.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class PiFunction implements FuzzyFunction, CenterAware {

    public final double a, r, b, m, p, q;
    // private double a, r, b, m, p, q;

    public PiFunction(double a, double r, double b, double m) {
        this.a = a;
        this.r = r;
        this.b = b;
        this.m = m;
        this.p = (r + a) / 2.0;
        this.q = (b + r) / 2.0;
    }

    @Override
    public double apply(double x) {
        if (x <= a)
            return 0.0;
        else if (a < x && x <= p)
            return Math.pow(2, m - 1) * Math.pow((x-a) / (r-a), m);
        else if (p < x && x <= r)
            return 1.0 - (Math.pow(2, m - 1) * Math.pow((r - x) / (r - a), m));
        else if (r < x && x <= q)
            return 1.0 - (Math.pow(2, m - 1) * Math.pow((x - r)/(b - r), m));
        else if (q < x && x <= b)
            return (Math.pow(2, m - 1) * Math.pow((b - x) / (b - r), m));
        else // (b < x)
            return 0.0;
    }

    @Override
    public String toString() {
        return String.format("Pi(%.2f, %.2f, %.2f)", a, r, b);
    }

    @Override
    public double center() {
        return r;
    }
}
