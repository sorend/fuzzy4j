package fuzzy4j.aggregation.weighted;

/**
 * A rolling aggregation allows to add values one by one (or n at once), and
 * call value to get the value.
 *
 * Soren A. Davidsen (soren@atmakuridavidsen.com)
 */
public interface WeightedRollingMean<T extends WeightedRollingMean> {

    public T add(WeightedValue... value);

    public double value();

    public T clear();
}
