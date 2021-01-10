package fuzzy4j.sets;

import fuzzy4j.util.SimpleInterval;

/**
 * Membershipfunction which always returns the same value for any member in the set.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class ConstantFunction implements FuzzyFunction {

    private double value;

    public ConstantFunction(double value) {
        this.value = value;
    }

    @Override
    public double apply(double x) {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
