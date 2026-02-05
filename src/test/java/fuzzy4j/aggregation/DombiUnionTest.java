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

        // Mean max = new PairwiseMean(Norms.asMean(Norms.fromTNorm(Norms.dombiByDrasticality(0.0))));

        // Mean drasticUnion = new PairwiseMean(Norms.asMean(Norms.fromTNorm(Norms.dombiByDrasticality(1.0))));

        // assertEquals(0.9, max.compute(0.9, 0.2, 0.1), 0.001);

        // assertEquals(1.0, drasticUnion.compute(0.9, 0.2, 0.1), 0.001);

    }
}
