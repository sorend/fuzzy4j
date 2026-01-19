package fuzzy4j.aggregation;

import fuzzy4j.algebra.Norms;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class DombiUnionTest {

    @Test
    void checkDrasticality() {

        Aggregation max = new PairwiseAggregation(Norms.asNorm(Norms.dombiByDrasticality(0.0)).duality());

        Aggregation drasticUnion = new PairwiseAggregation(Norms.asNorm(Norms.dombiByDrasticality(1.0)).duality());

        assertEquals(0.9, max.apply(0.9, 0.2, 0.1));

        assertEquals(1.0, drasticUnion.apply(0.9, 0.2, 0.1));

    }
}
