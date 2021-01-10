package fuzzy4j.sets;

/**
 * Implementation of a Log (natural logarithm) function which operates within [0, 1] for two defined points.
 *
 * Defined as:
 *  {@code M_E(x, a) = e^(-x a)}
 *
 * The parameter a controls the rate of decay.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class ExponentialDecayFunction implements FuzzyFunction {

    private double x1;
    private double alpha;

    /**
     *
     * @param x1 the location where f(x) = 1, x1 = 0 => f(0) = 1
     * @param alpha the decay parameter. larger = faster decay. alpha=1 means f(~9.0) = 0
     */
    public ExponentialDecayFunction(double x1, double alpha) {
        this.x1 = x1;
        this.alpha = alpha;
    }

    @Override
    public double apply(double x) {
        return Math.min(1.0, Math.exp(-(x - x1) * alpha));
    }
}
