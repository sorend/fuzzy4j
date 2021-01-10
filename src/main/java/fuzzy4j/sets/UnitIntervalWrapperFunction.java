package fuzzy4j.sets;

/**
 * Wraps a function inside the unit interval [0, 1]
 *
 * Defined for the function f(x)
 *      <code>U_W(x) = min(1, max(0, f(x)))</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
class UnitIntervalWrapperFunction implements FuzzyFunction {
    private FuzzyFunction inner;

    UnitIntervalWrapperFunction(FuzzyFunction inner) {
        this.inner = inner;
    }

    @Override
    public double apply(double x) {
        double value = inner.apply(x);
        if (value < 0.0)
            return 0.0;
        else if (value > 1.0)
            return 1.0;
        else
            return value;
    }

    public String toString() {
        return "min(1, max(0, " + inner.toString() + "))";
    }
}
