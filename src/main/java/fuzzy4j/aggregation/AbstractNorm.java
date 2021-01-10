package fuzzy4j.aggregation;

/**
 * Abstract implementation of norm without parameters.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public abstract class AbstractNorm implements Norm {

    private Type type;
    private Norm dual;

    protected AbstractNorm(Type type, Norm dual) {
        this.type = type;
        this.dual = dual;
    }

    @Override
    public Type type() {
        return type;
    }

    @Override
    public Norm duality() {
        return dual;
    }

    @Override
    public abstract double apply(double... values);
}
