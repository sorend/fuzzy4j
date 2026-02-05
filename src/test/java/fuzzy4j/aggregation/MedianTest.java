package fuzzy4j.aggregation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class MedianTest {

    Median impl = Median.INSTANCE;

    @Test
    void testApply_01() {

        assertEquals(Double.NaN, impl.compute());
        assertEquals(Double.NaN, impl.compute(null));

        assertEquals(1.0, impl.compute(1.0));

        assertEquals(0.0, impl.compute(0.0));

        assertEquals(0.4, impl.compute(0.0, 0.4, 1.0));

        assertEquals(0.5, impl.compute(0.0, 0.4, 0.6, 1.0));

    }

    @Test
    void testApply_02() {

        assertEquals(0.5, impl.compute(0.0, 1.0));

        assertEquals(0.4, impl.compute(1.0, 0.0, 0.4));

        assertEquals(0.5, impl.compute(0.4, 0.6, 0.2, 0.0, 1.0, 0.8));

    }

    @Test
    void testToString() {
        assertNotNull(impl.toString());
    }
}
