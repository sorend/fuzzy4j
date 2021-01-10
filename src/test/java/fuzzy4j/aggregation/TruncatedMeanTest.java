package fuzzy4j.aggregation;

import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class TruncatedMeanTest {

    TruncatedMean impl;

    @Test
    public void testCreation() {
        try {
            TruncatedMean.BY_PERCENTILE.create();
            fail();
        }
        catch (IllegalArgumentException e) {
        }
        try {
            TruncatedMean.BY_PERCENTILE.create(0, 0.2, 0.3);
            fail();
        }
        catch (IllegalArgumentException e) {
        }

        try {
            TruncatedMean.BY_PERCENTILE.create(null);
            fail();
        }
        catch (IllegalArgumentException e) {
        }

        try {
            TruncatedMean.BY_PERCENTILE.create(0.5);
            fail();
        }
        catch (IllegalArgumentException e) {
        }

        try {
            TruncatedMean.BY_PERCENTILE.create(-0.001);
            fail();
        }
        catch (IllegalArgumentException e) {
        }

        TruncatedMean.BY_PERCENTILE.create(0);
        TruncatedMean.BY_PERCENTILE.create(0.4999);
    }

    @Test
    public void testApply_basic() throws Exception {

        impl = new TruncatedMean(0.25, 0.75);

        assertEquals(Double.NaN, impl.apply());
        assertEquals(Double.NaN, impl.apply(null));

        assertEquals(0.0, impl.apply(0));
        assertEquals(1.0, impl.apply(1));
        assertEquals(0.8, impl.apply(0.8));
    }

    @Test
    public void testApply() throws Exception {

        impl = new TruncatedMean(0.25, 0.75);

        assertEquals(0.5, impl.apply(0.05, 0.1, 0.9, 0.91));

        // not defined yet, assertEquals(0.5, impl.apply(0.05, 0.5, 0.7));
    }

    @Test
    public void testToString() throws Exception {
        impl = new TruncatedMean(0.25, 0.75);
        assertNotNull(impl.toString());
        assertTrue(impl.toString().contains("h_"));
    }
}
