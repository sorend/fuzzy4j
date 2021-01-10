package fuzzy4j.sets;

import fuzzy4j.util.SimpleInterval;

import static fuzzy4j.util.SimpleInterval.i;

/**
 * A triangular apply function.
 *
 * Defined by three points, which gives us two linear pieces:
 *      f(a) = 0
 *      f(b) = 1
 *      f(c) = 0
 * <pre>
 * {@code y
 *    A
 *  1-|        .
 *    |       /:\
 *    |      / : \
 *    |     /  :  \
 * -0-+----'---:---'-----> x
 *    |   a    b    c
 * }
 * </pre>
 *
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class TriangularFunction implements FuzzyFunction, SupportAware, CenterAware {

    private SimpleInterval support;

    public final double a;
    public final double b;
    public final double c;

    public TriangularFunction(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.support = i(a, c);
    }

    @Override
    public double apply(double x) {
        if (a < x && x < b) {
            if (Double.isInfinite(a))
                return 1.0;
            else
                return (x-a) / (b - a);
        }
        else if (x == b)
            return 1.0;
        else if (b < x && x < c) {
            if (Double.isInfinite(c))
                return 1.0;
            else
                return (c - x) / (c - b);
        }
        else
            return 0.0;


        // return Math.max(0.0, Math.min((x-a) / (b - a), (c - x) / (c - b)));
    }

    @Override
    public SimpleInterval support() {
        return support;
    }

    @Override
    public double center() {
        return b;
    }

    public String toString() {
        return String.format("\u25b3(%f, %f, %f)", a, b, c);
    }
}
