package fuzzy4j.aggregation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class DombiIntersectionTest {

    Norm impl;

    @Test
    public void testCalc() throws Exception {

        impl = DombiIntersection.BY_EXPONENT.create(0.9);

        double r = impl.apply(1.0, 1.0);

        assertEquals(1.0, r, 0.001);

        r = impl.apply(1.0, 0.0);

        assertEquals(0.0, r, 0.001);

        r = impl.apply(1.0, 0.1);

        System.out.println("r = " + r);
    }
}
