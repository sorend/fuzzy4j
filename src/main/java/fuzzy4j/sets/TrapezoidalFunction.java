package fuzzy4j.sets;

import fuzzy4j.util.SimpleInterval;

import static fuzzy4j.util.SimpleInterval.i;

/**
 * A trapeze shaped apply function.
 *
 * Soren A. Davidsen (sorend@gmail.com)
 */
public class TrapezoidalFunction implements FuzzyFunction {

    private double a, b, d, c;
    private SimpleInterval support;

    /**
     * Creates a trapeze function that goes between [0, 1]
     *
     * @param a the left point of the trapeze where f(x) = 0
     * @param b the left point of the trapeze where f(x) = 1
     * @param c the right point of the trapeze where f(x) = 1
     * @param d the right point of the trapeze where f(x) = 0
     */
    public TrapezoidalFunction(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.support = i(a, d);
    }

    @Override
    public double apply(double x) {
        if (x < a)
            return 0.0;
        else if (a <= x && x < b)
            return (x - a) / (b - a);
        else if (b <= x && x <= c)
            return 1.0;
        else if (c < x && x <= d)
            return (d - x) / (d - c);
        else
            return 0.0;
    }

    public String toString() {
        return "i/¯\\i(" + a + ", " + b + ", " + c + ", " + d + ")";
    }
}
