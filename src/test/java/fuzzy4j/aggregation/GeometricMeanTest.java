package fuzzy4j.aggregation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class GeometricMeanTest {

    GeometricMean impl = GeometricMean.INSTANCE;

    @Test
    void testCalc_none() {
        assertEquals(Double.NaN, impl.apply(null));
        assertEquals(Double.NaN, impl.apply());
        assertEquals(0.5, impl.apply(0.5));
    }

    @Test
    void testCalc_lower() {

        double val = impl.apply(0.0, 0.0);
        assertEquals(0.0, val);

        double val2 = impl.apply(0.0, 0.0, 0, 0, 0, 0);
        assertEquals(0.0, val2);
    }

    @Test
    void testCalc_upper() {

        double val = impl.apply(1.0, 1.0);
        assertEquals(1.0, val);
    }

    @Test
    void testCalc_01() {

        double val = impl.apply(1.0, 0.0);
        assertTrue(val < 0.5);

        double val2 = impl.apply(1.0, 0.1, 0.2);
        double val3 = ArithmeticMean.INSTANCE.apply(1.0, 0.1, 0.2);

        assertTrue(val2 < val3);
    }

    @Test
    void testType() {
        assertEquals(Norm.Type.UNKNOWN, impl.type());
        assertEquals(Norm.Type.UNKNOWN, impl.duality().type());
    }

    @Test
    void testDuality() {

        Norm dual = impl.duality();

        double val = impl.apply(1.0, 0.2, 0.4);
        double val2 = dual.apply(1.0, 0.2, 0.4);

        assertEquals(1.0, val2);
        assertTrue(val < val2);
    }

    @Test
    void testToString() {
        assertNotNull(impl.toString());
    }
}
