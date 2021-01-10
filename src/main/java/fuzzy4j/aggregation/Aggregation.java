package fuzzy4j.aggregation;

import java.io.Serializable;

/**
 * @author Soren <sorend@gmail.com>
 */
public interface Aggregation extends Serializable {
    public double apply(double... values);
}
