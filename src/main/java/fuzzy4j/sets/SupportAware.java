package fuzzy4j.sets;

import fuzzy4j.util.SimpleInterval;

/**
 * Interface that functions can implement if they can provide the support
 * of the function as a simple interval.
 *
 * The support is defined as the interval where f(x) > 0.
 *
 * NOTE: While you can always implement support, please do it only when it makes
 * sense (that is, support does not include -inf or inf)
 *
 * @see fuzzy4j.util.SimpleInterval
 * @author Soren <sorend@gmail.com>
 */
public interface SupportAware {
    /**
     * Returns the support of the function
     * @return
     */
    public SimpleInterval support();
}
