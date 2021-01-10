package fuzzy4j.negation;

import fuzzy4j.sets.FuzzyFunction;

/**
 * Sugeno negation function.
 *
 * Defined as:
 *  <code>N_S(p, x) = (1 - x) / (1 + p * x)</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class SugenoNegation extends NegationFunction {

    private double p;

    public SugenoNegation(double p, FuzzyFunction inner) {
        super(inner);
        this.p = p;
    }

    @Override
    protected double applyNegation(double x) {
        return (1. - x) / (1. + p * x);
    }

    public String toString() {
        return "(1 - x) / (1 + p * x)";
    }
}
