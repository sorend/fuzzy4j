package fuzzy4j.aggregation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class QuadraticMeanTest {

    QuadraticMean impl = QuadraticMean.INSTANCE;

    @Test
    void testApply() {

        assertEquals(Double.NaN, impl.apply());
        assertEquals(Double.NaN, impl.apply(null));

        assertEquals(0.0, impl.apply(0));
        assertEquals(1.0, impl.apply(1));
        assertEquals(0.8, impl.apply(0.8));
    }

    @Test
    void testApply_02() {

        // 0, 0.2, 0.9 = 1/3 * (0*0 + 0.2*0.2 + 0.9*0.9) = 1/3 * (0 + 0.04 + 0.81) = 0.85 / 3 = 0.28333
        // sqrt = 0.53229
        assertEquals(0.53229, impl.apply(0, 0.2, 0.9), 0.00001);

    }

    @Test
    void testToString() {
        assertNotNull(impl.toString());
        assertTrue(impl.toString().contains("h_"));
    }
}
