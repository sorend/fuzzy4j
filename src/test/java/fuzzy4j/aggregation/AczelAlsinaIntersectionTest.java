package fuzzy4j.aggregation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class AczelAlsinaIntersectionTest {

    AczelAlsinaIntersection impl;

    @Test
    public void testApply_p_0() throws Exception {

        impl = new AczelAlsinaIntersection(0);
    }

    /*

    @Test
    public void testApply_p_inf() throws Exception {

        impl = new AczelAlsinaIntersection(Double.POSITIVE_INFINITY);

        assertEquals(0.2, impl.apply(0.5, 0.2));
    }

    @Test
    public void testApply_p_1() throws Exception {

        impl = new AczelAlsinaIntersection(1);
    }

    @Test
    public void testType() throws Exception {

        impl = new AczelAlsinaIntersection(1.0);

        assertTrue(impl instanceof Norm);

        assertTrue(impl.type() == Norm.Type.T_NORM);
    }

    @Test
    public void testDuality() throws Exception {

        impl = new AczelAlsinaIntersection(1.0);

        assertTrue(impl.type() == Norm.Type.T_NORM);

        assertEquals(0.3, impl.apply(0.6, 0.3));

        Norm dual = impl.duality();

        assertTrue(dual.type() == Norm.Type.T_CONORM);

        assertEquals(0.7, dual.apply(0.2, 0.7));
    }
    */
}
