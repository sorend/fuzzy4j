package fuzzy4j.aggregation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class ArithmeticMeanTest {

    ArithmeticMean impl = ArithmeticMean.INSTANCE;

    @Test
    void testApply() {

        assertEquals(Double.NaN, impl.compute(null));
        assertEquals(Double.NaN, impl.compute());

        assertEquals(0.0, impl.compute(0));
        assertEquals(1.0, impl.compute(1));
        assertEquals(0.5, impl.compute(0.5));

        assertEquals(0.5, impl.compute(0, 1));
        assertEquals(0.4, impl.compute(0.8, 0.2, 0.4, 0.6, 0.0));

    }

    @Test
    void testToString() {
        assertNotNull(impl.toString());
    }
}
