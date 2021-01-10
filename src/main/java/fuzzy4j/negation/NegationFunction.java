package fuzzy4j.negation;

import fuzzy4j.sets.FuzzyFunction;

/**
 * Abstract base class for negation of fuzzy sets.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public abstract class NegationFunction implements FuzzyFunction {

    private FuzzyFunction inner;

    public NegationFunction(FuzzyFunction inner) {
        this.inner = inner;
    }

    protected abstract double applyNegation(double x);

    @Override
    public double apply(double x) {
        double y = inner.apply(x);
        assert 0.0 <= y && y <= 1;
        return applyNegation(inner.apply(x));
    }
}
