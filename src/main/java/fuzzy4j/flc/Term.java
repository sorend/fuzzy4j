package fuzzy4j.flc;

import fuzzy4j.sets.FuzzyFunction;
import fuzzy4j.sets.TrapezoidalFunction;
import fuzzy4j.sets.TriangularFunction;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class Term {

    /**
     * Construct a term with a triangular apply function.
     * @param name
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static Term term(String name, double a, double b, double c) {
        return term(name, new TriangularFunction(a, b, c));
    }

    /**
     * Construct a term with a trapezoidal apply function.
     * @param name
     * @param a
     * @param b
     * @param c
     * @param d
     * @return
     */
    public static Term term(String name, double a, double b, double c, double d) {
        return term(name, new TrapezoidalFunction(a, b, c, d));
    }

    /**
     * Construct a term with a specified apply function.
     * @param name
     * @param set
     * @return
     */
    public static Term term(String name, FuzzyFunction set) {
        if (name == null || set == null)
            throw new IllegalArgumentException("name and set must be non-null");
        return new Term(name, set);
    }

    public final String name;
    public final FuzzyFunction set;

    public Term(String name, FuzzyFunction set) {
        this.name = name;
        this.set = set;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term) o;
        if (!name.equals(term.name)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return name;
    }
}
