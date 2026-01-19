package fuzzy4j.aggregation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class MaximumTest {

    Maximum impl = Maximum.INSTANCE;

    @Test
    void testCreation() {
        Maximum newImpl = new Maximum();

        assertNotNull(newImpl);
    }

    @Test
    void testApply() {

        assertEquals(0.0, impl.apply(0, 0, 0, 0));

        assertEquals(1.0, impl.apply(0, 0, 1, 0));

        assertEquals(1.0, impl.apply(1.0, 0.0));
        assertEquals(1.0, impl.apply(0.0, 1.0));

        assertEquals(0.5, impl.apply(0.2, 0.0, 0.1, 0.5, 0.1, 0.4, 0.49999999));
    }

    // @Test
    // void testType() {
    //     assertEquals(impl.type(), Norm.Type.T_CONORM);
    //     assertEquals(new Maximum().type(), Norm.Type.T_CONORM);
    // }

    @Test
    void testToString() {
        assertNotNull(impl.toString());
    }

    // @Test
    // void testDuality() {
    //     assertTrue(impl.duality() instanceof Minimum);
    // }
}
