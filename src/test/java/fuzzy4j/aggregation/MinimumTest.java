package fuzzy4j.aggregation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class MinimumTest {

    Minimum impl = Minimum.INSTANCE;

    @Test
    void testApply() {

        assertEquals(1.0, impl.compute(1.0, 1.0, 1.0));

        assertEquals(0.5, impl.compute(1.0, 0.5, 0.8, 0.9));

        assertEquals(0.0, impl.compute(0.2, 0.0, 0.4));
    }

    // @Test
    // void testType() {
    //     assertEquals(impl.type(), Norm.Type.T_NORM);
    // }

    @Test
    void testToString() {
        assertNotNull(impl.toString());
    }

    // @Test
    // void testDuality() {
    //     assertTrue(impl.duality() instanceof Maximum);
    // }
}
