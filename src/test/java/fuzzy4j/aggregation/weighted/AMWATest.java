package fuzzy4j.aggregation.weighted;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class AMWATest {

    AMWA impl;

    @Test
    public void testCalc_p_gt_0_5() {

        impl = new AMWA(2./3.);

        double A = impl.apply(WeightedValue.pairs(0.4, 0.1, 1.0, 0.7));

        double B = impl.apply(WeightedValue.pairs(0.4, 0.9, 1.0, 0.4));

        System.out.println("A = " + A + ", B = " + B);

        //
        // need to assert, didn't calculate anything here.
        //

    }

    @Test
    public void testCalc_p_lt_0_5() {

        impl = new AMWA(1./3.);

        double A = impl.apply(WeightedValue.pairs(0.4, 0.1, 1.0, 0.7));

        double B = impl.apply(WeightedValue.pairs(0.4, 0.9, 1.0, 0.4));

        System.out.println("A = " + A + ", B = " + B);

        assertEquals(0.594, A, 0.001);

    }
}
