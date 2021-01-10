package fuzzy4j.aggregation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class MinimumTest {

    Minimum impl = Minimum.INSTANCE;

    @Test
    public void testApply() throws Exception {

        assertEquals(1.0, impl.apply(1.0, 1.0, 1.0));

        assertEquals(0.5, impl.apply(1.0, 0.5, 0.8, 0.9));

        assertEquals(0.0, impl.apply(0.2, 0.0, 0.4));
    }

    @Test
    public void testType() throws Exception {
        assertEquals(impl.type(), Norm.Type.T_NORM);
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(impl.toString());
    }

    @Test
    public void testDuality() throws Exception {
        assertTrue(impl.duality() instanceof Maximum);
    }
}
