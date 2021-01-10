package fuzzy4j.aggregation;

import fuzzy4j.aggregation.weighted.WeightedValue;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class AIWATest {

    AIWA impl;

    @Test
    public void testParametricFactory() throws Exception {

        impl = AIWA.FACTORY.create(0.5);

        assertEquals(impl.p, 0.5);

        try {
            impl = AIWA.FACTORY.create();
            fail();
        }
        catch (IllegalArgumentException e) {
        }

        try {
            impl = AIWA.FACTORY.create(0.4, 0.5, 0.1);
            fail("more than one parameter not supported");
        }
        catch (IllegalArgumentException e) {
        }

        try {
            impl = AIWA.FACTORY.create(1.000001);
            fail();
        }
        catch (IllegalArgumentException e) {
        }

        try {
            impl = AIWA.FACTORY.create(-0.0000001);
            fail();
        }
        catch (IllegalArgumentException e) {
        }

        impl = AIWA.FACTORY.create(1);
        impl = AIWA.FACTORY.create(0);

    }

    /**
     * Test from notes.
     * @throws Exception
     */
    @Test
    public void testCalc_same_as_weighted() throws Exception {

        impl = new AIWA(2./3.);

        fuzzy4j.aggregation.weighted.AIWA weighted = new fuzzy4j.aggregation.weighted.AIWA(2./3.);

        double A = impl.apply(0.1, 0.7);
        double B = impl.apply(0.9, 0.4);

        double wA = weighted.apply(WeightedValue.pairs(1.0, 0.1, 1.0, 0.7));
        double wB = weighted.apply(WeightedValue.pairs(1.0, 0.9, 1.0, 0.4));

        assertEquals(A, wA);
        assertEquals(B, wB);
    }

    @Test
    public void testCalc_p_morethan_0_5() throws Exception {

        impl = new AIWA(2./3.);

        double A = impl.apply(0.1, 0.7);
        double B = impl.apply(0.9, 0.4);

        assertEquals(0.33, A, 0.01);
        assertEquals(0.57, B, 0.01);
    }

    @Test
    public void testCalc_p_lessthan_0_5() throws Exception {

        impl = new AIWA(1./3.);

        double A = impl.apply(0.1, 0.7);
        double B = impl.apply(0.9, 0.4);

        assertEquals(0.5, A, 0.01);
        assertEquals(0.7, B, 0.01);
    }

}
