package fuzzy4j.sets;

/**
 * Generalized bell shape function.
 *
 * Defined as:
 *      {@code mu(x, alpha, beta, gamma) = (1 + abs((x - gamma) / alpha))^(beta * 2)}
 *
 * This is equal to the Beta-function for {@code mu(x, alpha, 1, gamma)}
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class GeneralizedBellFunction implements FuzzyFunction {

    private double alpha;
    private double beta;
    private double gamma;

    public GeneralizedBellFunction(double alpha, double beta, double gamma) {
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
    }

    @Override
    public double apply(double x) {
        double a = Math.abs((x - gamma) / alpha);
        double b = Math.pow(a, 2.0 * beta);
        return 1.0 / (1.0 + b);
    }
}
