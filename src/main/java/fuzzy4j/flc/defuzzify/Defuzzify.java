package fuzzy4j.flc.defuzzify;

import fuzzy4j.flc.Variable;
import fuzzy4j.sets.FuzzyFunction;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public interface Defuzzify {
    public double apply(Variable variable, FuzzyFunction set);
}
