package fuzzy4j.negation;

import fuzzy4j.sets.FuzzyFunction;

/**
 * Inverse function, inverses another function.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class ZadehNegation extends NegationFunction {

    public ZadehNegation(FuzzyFunction inner) {
        super(inner);
    }

    @Override
    protected double applyNegation(double x) {
        return 1 - x;
    }

    public String toString() {
        return "1 - x";
    }
}
