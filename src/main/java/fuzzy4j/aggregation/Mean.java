package fuzzy4j.aggregation;

import java.io.Serializable;

/**
 * @author Soren <sorend@gmail.com>
 */
@FunctionalInterface
public interface Mean extends Serializable {
    public double compute(double... values);
}
