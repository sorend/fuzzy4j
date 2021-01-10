package fuzzy4j;

/**
 * Simple wrapper to make any object valued.
 *
 * The valuedmember maintains the identity equals and hashCode of the wrapped object.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class ValuedMember<T> implements Valued {

    private T obj;
    private double value;

    public ValuedMember(T obj, double value) {
        this.obj = obj;
        this.value = value;
    }

    public T obj() {
        return obj;
    }

    public double value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof ValuedMember))
            return false;
        return obj.equals(((ValuedMember)o).obj);
    }

    @Override
    public int hashCode() {
        return obj.hashCode();
    }
}
