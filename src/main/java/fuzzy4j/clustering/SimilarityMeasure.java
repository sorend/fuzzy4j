package fuzzy4j.clustering;

/**
 * Design interface for similarity between objects.
 *
 * @author Soren <sorend@gmail.com>
 */
public interface SimilarityMeasure<V> {
    /**
     * Calculate similarity of two objects  sim(a, b) -> [0, 1]
     * @param x1 first object
     * @param x2 second object
     * @return 0 means same, anything else some degree of not similar
     */
    public double distance(V x1, V x2);
}
