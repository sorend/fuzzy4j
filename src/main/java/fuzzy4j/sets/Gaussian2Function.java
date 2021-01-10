package fuzzy4j.sets;

/**
 * Gaussian 2 implementation, allows a function which joins at center but with different decay in each direction.
 *
 * Where
 *     c is the center (top) of the distribution
 *     s1 decides the support left of c.
 *     s2 decides the support right of c.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class Gaussian2Function implements FuzzyFunction {

    FuzzyFunction inner;

    /**
     * @see fuzzy4j.sets.Gaussian2Function
     *
     * @param c
     * @param s1
     * @param s2
     */
    public Gaussian2Function(double c, double s1, double s2) {
        this.inner = new SplitFunction(c, new GaussianFunction(c, s1), new GaussianFunction(c, s2));
    }

    @Override
    public double apply(double x) {
        return inner.apply(x);
    }
}
