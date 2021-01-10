package fuzzy4j.aggregation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class ArithmeticMeanTest {

    ArithmeticMean impl = ArithmeticMean.INSTANCE;

    @Test
    public void testApply() throws Exception {

        assertEquals(Double.NaN, impl.apply(null));
        assertEquals(Double.NaN, impl.apply());

        assertEquals(0.0, impl.apply(0));
        assertEquals(1.0, impl.apply(1));
        assertEquals(0.5, impl.apply(0.5));

        assertEquals(0.5, impl.apply(0, 1));
        assertEquals(0.4, impl.apply(0.8, 0.2, 0.4, 0.6, 0.0));

    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(impl.toString());
    }
}
