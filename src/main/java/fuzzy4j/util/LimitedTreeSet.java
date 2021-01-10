package fuzzy4j.util;

import java.util.TreeSet;

/**
 * A sorted collection with a limited capacity.
 *
 * @author Soren <sorend@gmail.com>
 */
public class LimitedTreeSet<E> extends TreeSet<E> {

    private final int maxCapacity;

    public LimitedTreeSet(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public boolean add(E e) {
        boolean rc = super.add(e);
        if (!rc)
            return false;
        else {
            if (this.size() > maxCapacity)
                this.remove(this.last());
            return true;
        }
    }
}
