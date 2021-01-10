package fuzzy4j.sets;

import fuzzy4j.Valued;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public interface SetFunction<T extends Valued> {
    public double membership(T obj);
}
