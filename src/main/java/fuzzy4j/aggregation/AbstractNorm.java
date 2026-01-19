package fuzzy4j.aggregation;

/**
 * Abstract implementation of norm without parameters.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public abstract class AbstractNorm implements Aggregation {

    protected AbstractNorm() {
    }

    @Override
    public abstract double apply(double... values);
}
