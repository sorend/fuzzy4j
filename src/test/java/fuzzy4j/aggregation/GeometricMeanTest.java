package fuzzy4j.aggregation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class GeometricMeanTest {

    GeometricMean impl = GeometricMean.INSTANCE;

    public void testCalc_none() {
        assertEquals(Double.NaN, impl.apply(null));
        assertEquals(Double.NaN, impl.apply());
        assertEquals(0.5, impl.apply(0.5));
    }

    @Test
    public void testCalc_lower() throws Exception {

        double val = impl.apply(0.0, 0.0);
        assertEquals(0.0, val);

        double val2 = impl.apply(0.0, 0.0, 0, 0, 0, 0);
        assertEquals(0.0, val2);
    }

    @Test
    public void testCalc_upper() throws Exception {

        double val = impl.apply(1.0, 1.0);
        assertEquals(1.0, val);
    }

    @Test
    public void testCalc_01() throws Exception {

        double val = impl.apply(1.0, 0.0);
        assertTrue(val < 0.5);

        double val2 = impl.apply(1.0, 0.1, 0.2);
        double val3 = ArithmeticMean.INSTANCE.apply(1.0, 0.1, 0.2);

        assertTrue(val2 < val3);
    }

    @Test
    public void testType() throws Exception {
        assertEquals(Norm.Type.UNKNOWN, impl.type());
        assertEquals(Norm.Type.UNKNOWN, impl.duality().type());
    }

    @Test
    public void testDuality() throws Exception {

        Norm dual = impl.duality();

        double val = impl.apply(1.0, 0.2, 0.4);
        double val2 = dual.apply(1.0, 0.2, 0.4);

        assertEquals(1.0, val2);
        assertTrue(val < val2);
    }

    @Test
    public void testToString() {
        assertNotNull(impl.toString());
    }
}
