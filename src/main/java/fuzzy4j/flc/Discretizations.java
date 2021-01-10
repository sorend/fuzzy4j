package fuzzy4j.flc;

import static fuzzy4j.flc.Term.term;

/**
 * Implementation of simple arithmetic discretizations.
 *
 * @author Soren <sorend@gmail.com>
 */
public class Discretizations {

    /**
     * Creates a discretization based on a set of triangular apply functions distributed
     * uniformly over the {@code [start, end]} interval, for the number of labels fuzzy sets.
     *
     * The first first fuzzy set has {@code mu(start) = 1} and the last fuzzy set has {@code mu(end) = 1}.
     * Each set overlaps such that:
     *    {@code if mu_{n}(v) = 1 then mu_{n-1}(v) = 0 and mu_{n+1}(v) = 0}
     *
     * @param start start of range
     * @param end end of range
     * @param labels the labels for the discretized sets.
     * @return terms
     */
    public static Term[] uniformTriangular(double start, double end, String... labels) {

        assert labels != null && labels.length > 1;
        assert start < end;

        double delta = (end - start) / (labels.length - 1);

        Term[] terms = new Term[labels.length];
        for (int i = 0; i < terms.length; i++) {
            double center = start + (i * delta);
            terms[i] = term(labels[i], center - delta, center, center + delta);
        }

        return terms;
    }

}
