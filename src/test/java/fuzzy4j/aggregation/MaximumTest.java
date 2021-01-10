package fuzzy4j.aggregation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class MaximumTest {

    Maximum impl = Maximum.INSTANCE;

    @Test
    public void testCreation() throws Exception {
        Maximum newImpl = new Maximum();

        assertNotNull(newImpl);
    }

    @Test
    public void testApply() throws Exception {

        assertEquals(0.0, impl.apply(0, 0, 0, 0));

        assertEquals(1.0, impl.apply(0, 0, 1, 0));

        assertEquals(1.0, impl.apply(1.0, 0.0));
        assertEquals(1.0, impl.apply(0.0, 1.0));

        assertEquals(0.5, impl.apply(0.2, 0.0, 0.1, 0.5, 0.1, 0.4, 0.49999999));
    }

    @Test
    public void testType() throws Exception {
        assertEquals(impl.type(), Norm.Type.T_CONORM);
        assertEquals(new Maximum().type(), Norm.Type.T_CONORM);
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(impl.toString());
    }

    @Test
    public void testDuality() throws Exception {
        assertTrue(impl.duality() instanceof Minimum);
    }
}
