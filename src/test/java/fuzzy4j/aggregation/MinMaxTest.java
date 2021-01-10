package fuzzy4j.aggregation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class MinMaxTest {

    MinMax impl;

    public void testFACTORY() {

        impl = MinMax.FACTORY.create(0.0);
        impl = MinMax.FACTORY.create(1.0);
        impl = MinMax.FACTORY.create(0.5);

        try {
            impl = MinMax.FACTORY.create(0.0, 0.2, 0.3);
            fail();
        }
        catch (Exception e) {
        }

        try {
            impl = MinMax.FACTORY.create(-1.0);
            fail();
        }
        catch (Exception e) {
        }

        try {
            impl = MinMax.FACTORY.create(1.0000001);
            fail();
        }
        catch (Exception e) {
        }
    }

    @Test
    public void testBasics() {

        impl = MinMax.MIDRANGE;

        assertEquals(Double.NaN, impl.apply());
        assertEquals(Double.NaN, impl.apply(null));

        try {
            new MinMax(-0.001);
            fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            new MinMax(1.001);
            fail();
        }
        catch (IllegalArgumentException e) {}
    }

    @Test
    public void testApply() throws Exception {

        impl = MinMax.FACTORY.create(1.0);

        assertEquals(0.43, impl.apply(0.43));

        assertEquals(0.45, impl.apply(0.2, 0.1, 0.4, 0.45));

        impl = MinMax.FACTORY.create(0.0);

        assertEquals(0.22, impl.apply(0.22));

        assertEquals(0.66, impl.apply(0.9, 0.7, 0.66, 0.99));

        impl = MinMax.FACTORY.create(0.5);

        assertEquals(0.55, impl.apply(0.55));

        assertEquals(0.4, impl.apply(0.2, 0.6, 0.4));

        assertEquals(0.2, impl.apply(0.2, 0.3, 0.1));
    }
}
