package fuzzy4j.aggregation.weighted;

/**
 * A rolling aggregation allows to add values one by one (or n at once), and
 * call value to get the value.
 *
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public interface WeightedRollingAggregation<T extends WeightedRollingAggregation> {

    public T add(WeightedValue... value);

    public double value();

    public T clear();
}
