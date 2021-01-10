package fuzzy4j.sets;

/**
 * Simple linear apply function, with cut-off values within [0, 1].
 *
 * For example:
 *
 * <pre>
 * {@code y
 *    ^
 *  1-|                    ,----(y cut at 1)-
 *    |                   /
 *    |                  /
 *    |                 /
 * -0-+--(y cut at 0)--'-------------------> x
 *    |
 * }
 * </pre>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class LinearFunction implements FuzzyFunction {

    private Line f;

    public LinearFunction(double m, double b) {
        this.f = new Line(m, b);
    }

    public LinearFunction(Line f) {
        this.f = f;
    }

    @Override
    public double apply(double x) {
        return Math.min(1.0, Math.max(0.0, f.apply(x)));
    }

}
