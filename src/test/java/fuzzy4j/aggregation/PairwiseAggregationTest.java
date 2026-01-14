package fuzzy4j.aggregation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class PairwiseAggregationTest {

    PairwiseAggregation impl;

    @Test
    void testWrap_factory() {

        ParametricFactory<Aggregation> factory = PairwiseAggregation.wrap_factory(PowerMean.FACTORY);

        Aggregation aggr = factory.create(1.0);

        assertTrue(aggr instanceof PairwiseAggregation);

        // powermean is not associative, so don't use with more than two in this case.
        assertEquals(0.5, aggr.apply(1.0, 0.0));
    }

    @Test
    void testApply() {

        PairwiseAggregation aggr = new PairwiseAggregation(AlgebraicProduct.INSTANCE);

        assertEquals(0.1, aggr.apply(0.2, 0.5, 1.0));

        assertEquals(0.1, aggr.apply(1.0, 0.2, 0.5));
    }
}
