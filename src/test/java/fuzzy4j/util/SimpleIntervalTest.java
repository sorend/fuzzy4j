package fuzzy4j.util;

import fuzzy4j.aggregation.ArithmeticMean;
import org.junit.Test;

import static fuzzy4j.util.SimpleInterval.i;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class SimpleIntervalTest {

    SimpleInterval impl;

    @Test
    public void testParse() throws Exception {

        impl = SimpleInterval.parse("[0, 1]");
        assertTrue(impl.within(0.0));
        assertTrue(impl.within(1.0));
        assertTrue(impl.within(0.5));
        assertFalse(impl.within(1.1));
        assertFalse(impl.within(-0.1));

        impl = SimpleInterval.parse("(0, 1]");
        assertTrue(impl.within(1.0));
        assertFalse(impl.within(0.0));

        impl = SimpleInterval.parse("[0, 1)");
        assertTrue(impl.within(0.0));
        assertFalse(impl.within(1.0));

        impl = SimpleInterval.parse("[0, 1.1]");
        assertTrue(impl.within(0.0));
        assertTrue(impl.within(1.1));

        try {
            impl = SimpleInterval.parse("0 1");
            fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            impl = SimpleInterval.parse(null);
            fail();
        }
        catch (IllegalArgumentException e) {}

    }

    @Test
    public void testLeftExclusive() throws Exception {

        impl = new SimpleInterval(true, 0.0, 1.0, true);

        assertTrue(impl.within(0.0));

        SimpleInterval impl2 = impl.leftExclusive();

        assertFalse(impl == impl2);

        assertFalse(impl2.within(0.0));
        assertTrue(impl.within(0.0));
    }

    @Test
    public void testRightExclusive() throws Exception {
        impl = new SimpleInterval(true, 0.0, 1.0, true);

        assertTrue(impl.within(1.0));

        SimpleInterval impl2 = impl.rightExclusive();

        assertFalse(impl == impl2);

        assertFalse(impl2.within(1.0));
        assertTrue(impl.within(1.0));
    }

    @Test
    public void testWithin_Inf() throws Exception {

        impl = new SimpleInterval(true, Double.NEGATIVE_INFINITY, 0.0, true);

        assertTrue(impl.within(Double.NEGATIVE_INFINITY));

        impl = impl.leftExclusive();

        assertFalse(impl.within(Double.NEGATIVE_INFINITY));

        impl = new SimpleInterval(true, 0.0, Double.POSITIVE_INFINITY, true);

        assertTrue(impl.within(Double.POSITIVE_INFINITY));
        assertFalse(impl.within(Double.NEGATIVE_INFINITY));

        impl = impl.rightExclusive();

        assertFalse(impl.within(Double.POSITIVE_INFINITY));

        assertFalse(impl.within(Double.NaN));
    }

    @Test
    public void testAggregate() {

        impl = i(0, 10);
        SimpleInterval si2 = i(1, 5);

        SimpleInterval si3 = impl.aggregate(ArithmeticMean.INSTANCE, si2);

        assertEquals(0.5, si3.min());
        assertEquals(7.5, si3.max());
    }

    @Test
    public void testToString() {
        assertEquals("[0,1)", SimpleInterval.parse("[0,1)").toString());
    }

    @Test
    public void testEquals() {

        assertEquals(i(0, 1), i(0, 1));
        assertFalse(i(0, 1).equals(i(0, 1.001)));
        assertFalse(i(0.0001, 1).equals(i(0, 1)));
        assertEquals(i(0, 1).hashCode(), i(0, 1).hashCode());

        assertFalse(i(0, 1).leftExclusive().equals(i(0, 1)));
        assertEquals(i(0, 1).leftExclusive(), i(0, 1).leftExclusive());

        assertFalse(i(0, 1).rightExclusive().equals(i(0, 1)));
        assertEquals(i(0, 1).rightExclusive(), i(0, 1).rightExclusive());
    }
}
