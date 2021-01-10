package fuzzy4j.aggregation.weighted;

/**
 * Weighted aggregation is a special case of aggregation where we work on pairs of weight and apply.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public interface WeightedAggregation {

    /**
     * Aggregate the weighted pairs (sequences).
     *
     * See {@link WeightedValue#pairs(double...)}
     *
     * @param values Each (v, a) pair, where "v" is the weight, and "a" the apply value.
     * @return the aggregated value.
     */
    public double apply(WeightedValue... values);
}
