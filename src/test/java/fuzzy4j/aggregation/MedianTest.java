package fuzzy4j.aggregation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class MedianTest {

    Median impl = Median.INSTANCE;

    @Test
    public void testApply_01() {

        assertEquals(Double.NaN, impl.apply());
        assertEquals(Double.NaN, impl.apply(null));

        assertEquals(1.0, impl.apply(1.0));

        assertEquals(0.0, impl.apply(0.0));

        assertEquals(0.4, impl.apply(0.0, 0.4, 1.0));

        assertEquals(0.5, impl.apply(0.0, 0.4, 0.6, 1.0));

    }

    @Test
    public void testApply_02() {

        assertEquals(0.5, impl.apply(0.0, 1.0));

        assertEquals(0.4, impl.apply(1.0, 0.0, 0.4));

        assertEquals(0.5, impl.apply(0.4, 0.6, 0.2, 0.0, 1.0, 0.8));

    }

    @Test
    public void testToString() {
        assertNotNull(impl.toString());
    }
}
