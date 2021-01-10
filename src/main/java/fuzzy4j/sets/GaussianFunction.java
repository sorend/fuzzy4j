package fuzzy4j.sets;

/**
 * Gaussian function implementation
 *
 * The normalized gaussian function is defined as:
 *     <code>mu(x, b, c) = e^-((x-c)*(x-c))/(2*s*s)</code>
 *
 * Where
 *     c is the center (top) of the distribution
 *     s decides the support
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class GaussianFunction implements FuzzyFunction {

    public final double c;
    public final double s;
    private final double div;

    /**
     * @see GaussianFunction
     *
     * @param c
     * @param s
     */
    public GaussianFunction(double c, double s) {
        this.c = c;
        this.s = s;
        this.div = 2 * s * s;
    }

    @Override
    public double apply(double x) {
        return Math.exp(-(x - c) * (x - c) / div);
    }
}
