package fuzzy4j.sets;

import java.io.Serializable;

/**
 * Representation of a apply function. A apply function should define apply
 * for all x in the universal set X.
 *
 * @see SupportAware
 */
public interface FuzzyFunction extends Serializable {
    /**
     * Measure apply of value x in the set.
     * @param x
     * @return
     */
	public double apply(double x);
}
