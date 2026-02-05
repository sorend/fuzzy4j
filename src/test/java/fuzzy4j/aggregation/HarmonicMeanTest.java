package fuzzy4j.aggregation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class HarmonicMeanTest {

    HarmonicMean impl = HarmonicMean.INSTANCE;

    @Test
    void testApply_basics() {

        assertEquals(Double.NaN, impl.compute());
        assertEquals(Double.NaN, impl.compute(null));
        assertEquals(0.0, impl.compute(0));
        assertEquals(1.0, impl.compute(1));
        assertEquals(0.4, impl.compute(0.4));
    }

    @Test
    void testApply_01() {

        // 1, 1, 1 = 3 / 1+1+1 = 1
        assertEquals(1.0, impl.compute(1, 1, 1));

        // 0, 0.5, 1 = 3 / (1/0 + 1/0.5 + 1/1) = 3 / (inf + 2 + 1) = 0
        assertEquals(0.0, impl.compute(0, 0.5, 1));

        // 0.4, 0.8, 0.9 = 3 / (1/0.4 + 1/0.8 + 1/0.9) = 3 / 2.5 + 1.25 + 1.111.. = 3 / 4.8611 = 0.61714
        assertEquals(0.61714, impl.compute(0.4, 0.8, 0.9), 0.00001);
    }

    @Test
    void testToString() {
        assertNotNull(impl.toString());
    }
}
