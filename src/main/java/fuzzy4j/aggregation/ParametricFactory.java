package fuzzy4j.aggregation;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public interface ParametricFactory<T> {
    public T create(double... params);
}
