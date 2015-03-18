package fuzzy4j.aggregation;

/**
 * This supports wrapping an aggregation that takes only two parameters into a series
 * of aggregations for any number of aggregated values. This assumes that hte order of
 * aggregation does not matter, so use with care only on aggregation operators that
 * satisfy this requirement (i.e. associative operators, for example t-norms).
 *
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class PairwiseAggregation implements Aggregation {

    /**
     * Wrap a parametric factory with pairwise aggregation.
     * @param inner The parametric factory to wrap
     * @param <T>
     * @return A wrapped factory
     */
    public static final <T extends Aggregation> ParametricFactory<Aggregation> wrap_factory(final ParametricFactory<T> inner) {
        return new ParametricFactory<Aggregation>() {
            @Override
            public Aggregation create(double... params) {
                return new PairwiseAggregation(inner.create(params));
            }
        };
    }

    public final Aggregation inner;

    public PairwiseAggregation(Aggregation inner) {
        this.inner = inner;
    }

    @Override
    public double apply(double... values) {
        double r = values[0];
        for (int i = 1; i < values.length; i++)
            r = inner.apply(r, values[i]);
        return r;
    }
}
