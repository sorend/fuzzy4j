package fuzzy4j.sets;

/**
 * Implementation of cosine apply function. Note that this is not a cycling
 * cosine function, but only supports a single "top": i.-~-.i
 *
 * Alpha defines the support of the function, Beta defines the top.
 *
 * <pre>
 * {@code .
 *       .-^-.
 *    .-'     `-.
 *   /           \
 * -'-------------`-----> x
 *        /|\<---->
 *         |    alpha
 *        beta
 * }
 * </pre>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class CosineFunction implements FuzzyFunction, CenterAware {

    private double alpha;
    private double beta;

    /**
     * Constructor
     *
     * @param alpha Half the support
     * @param beta apply(beta) = 1.0
     */
    public CosineFunction(double alpha, double beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    @Override
    public double apply(double x) {
        double d = x - beta;
        double t = alpha;
        double tPi = Math.PI / alpha;
        if ((-t <= d) && (d <= t))
            return (Math.cos(d * tPi) + 1.0) / 2.0;
        else
            return 0.0;
    }

    @Override
    public String toString() {
        return "cos";
    }

    @Override
    public double center() {
        return beta;
    }
}
