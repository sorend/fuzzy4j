package fuzzy4j.negation;

import fuzzy4j.sets.FuzzyFunction;

/**
 * Yager negation function.
 *
 * Defined as:
 *  <code>N_Y(p, x) = (1 - x^p)^(1/p)</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class YagerNegation extends NegationFunction {

    private double p;

    public YagerNegation(double p, FuzzyFunction inner) {
        super(inner);
        this.p = p;
    }

    @Override
    protected double applyNegation(double x) {
        return Math.pow(1. - Math.pow(x, p), 1. / p);
    }

    public String toString() {
        return "(1 - x^p)^(1/p)";
    }
}
