package fuzzy4j.aggregation.weighted;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class AIWATest {

    AIWA impl;

    /**
     * Test from notes.
     * @throws Exception
     */
    @Test
    public void testCalc_Notes() throws Exception {

        impl = new AIWA(2./3.);

        double A = impl.apply(WeightedValue.pairs(0.4, 0.1, 1.0, 0.7));

        double B = impl.apply(WeightedValue.pairs(0.4, 0.9, 1.0, 0.4));

        assertEquals(0.565, A, 0.001);
        assertEquals(0.442, B, 0.001);
    }


    @Test
    public void extremes_One() throws Exception {

        impl = new AIWA(1.0);

        double A = impl.apply(WeightedValue.pairs(1.0, 0.4, 1.0, 0.6));

        System.out.println("A.one = " + A);

        assertEquals(0.0, A);
    }

    @Test
    public void extremes_Zero() throws Exception {

        impl = new AIWA(0.0);

        double A = impl.apply(WeightedValue.pairs(1.0, 0.4, 1.0, 0.6));

        System.out.println("A.zero = " + A);

        assertEquals(1.0, A);
    }

}
