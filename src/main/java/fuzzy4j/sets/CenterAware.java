package fuzzy4j.sets;

/**
 * Interface which specifies a function that is aware about its center point.
 * (for example a triangular function has a center where f(x) = 1)
 *
 * @author Soren <sorend@gmail.com>
 */
public interface CenterAware {
    /**
     * Returns the center of the function
     * @return
     */
    public double center();
}
