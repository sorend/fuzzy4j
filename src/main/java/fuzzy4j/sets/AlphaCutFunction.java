package fuzzy4j.sets;

/**
 * Alphacut function, implements an alphacut of another function.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class AlphaCutFunction implements FuzzyFunction {

    private FuzzyFunction inner;
    private double alpha;

    /**
     *
     * @param inner The function to alphacut
     * @param alpha the alphacut value [0, 1]
     */
    public AlphaCutFunction(FuzzyFunction inner, double alpha) {
        this.inner = inner;
        this.alpha = alpha;
    }

    @Override
    public double apply(double x) {
        double y = inner.apply(x);
        return y >= alpha ? y : 0.0;
    }

    @Override
    public String toString() {
        return "\u03b1-cut(" + alpha + ", " + inner + ")";
    }
}
