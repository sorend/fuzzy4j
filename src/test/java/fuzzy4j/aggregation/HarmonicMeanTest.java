package fuzzy4j.aggregation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class HarmonicMeanTest {

    HarmonicMean impl = HarmonicMean.INSTANCE;

    @Test
    public void testApply_basics() throws Exception {

        assertEquals(Double.NaN, impl.apply());
        assertEquals(Double.NaN, impl.apply(null));
        assertEquals(0.0, impl.apply(0));
        assertEquals(1.0, impl.apply(1));
        assertEquals(0.4, impl.apply(0.4));
    }

    @Test
    public void testApply_01() throws Exception {

        // 1, 1, 1 = 3 / 1+1+1 = 1
        assertEquals(1.0, impl.apply(1, 1, 1));

        // 0, 0.5, 1 = 3 / (1/0 + 1/0.5 + 1/1) = 3 / (inf + 2 + 1) = 0
        assertEquals(0.0, impl.apply(0, 0.5, 1));

        // 0.4, 0.8, 0.9 = 3 / (1/0.4 + 1/0.8 + 1/0.9) = 3 / 2.5 + 1.25 + 1.111.. = 3 / 4.8611 = 0.61714
        assertEquals(0.61714, impl.apply(0.4, 0.8, 0.9), 0.00001);
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(impl.toString());
    }
}
