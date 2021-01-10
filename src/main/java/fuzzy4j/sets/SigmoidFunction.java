package fuzzy4j.sets;

import fuzzy4j.util.SimpleInterval;

/**
 * Sigmoidal apply function.
 *
 * Defined as:
 *      <code>mu(x, alpha, x0) = (1 + e^(-alpha*(x-x0)))^-1</code>
 *
 * Where:
 *      x0 is the location of the x-axis position of the center of the curve.
 *      alpha defines the steepness of the curve. alpha is large means steep, alpha is small (<0) means less steep.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class SigmoidFunction implements FuzzyFunction, SupportAware {

    private double alpha;
    private double x0;

    public SigmoidFunction(double alpha, double x0) {
        this.alpha = alpha;
        this.x0 = x0;
    }

    @Override
    public double apply(double x) {
        return 1.0 / (1.0 + Math.exp(-alpha * (x - x0)));
    }

    @Override
    public SimpleInterval support() {
        double min = x0 - 9 / Math.abs(alpha);
        double max = x0 + 9 / Math.abs(alpha);
        return new SimpleInterval(true, min, max, true);
    }
}
