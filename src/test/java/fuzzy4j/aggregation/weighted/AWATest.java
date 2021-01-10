package fuzzy4j.aggregation.weighted;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class AWATest {

    AWA impl;

    @Test
    public void compareAIWA_lt_0_5() {

        AIWA aiwa = new AIWA(1./3.);
        impl = new AWA(1./3., 1.);

        double A_awa  = impl.apply(WeightedValue.pairs(0.4, 0.1, 1.0, 0.7));
        double A_aiwa = aiwa.apply(WeightedValue.pairs(0.4, 0.1, 1.0, 0.7));

        assertEquals(A_awa, A_aiwa, 0.0001);

        double B_awa  = impl.apply(WeightedValue.pairs(0.4, 0.9, 1.0, 0.4));
        double B_aiwa = aiwa.apply(WeightedValue.pairs(0.4, 0.9, 1.0, 0.4));

        assertEquals(B_awa, B_aiwa, 0.0001);

    }

    @Test
    public void compareAIWA_gt_0_5() {

        AIWA aiwa = new AIWA(2./3.);
        impl = new AWA(2./3., 1.);

        double A_awa  = impl.apply(WeightedValue.pairs(0.4, 0.1, 1.0, 0.7));
        double A_aiwa = aiwa.apply(WeightedValue.pairs(0.4, 0.1, 1.0, 0.7));

        assertEquals(A_awa, A_aiwa, 0.0001);

        double B_awa  = impl.apply(WeightedValue.pairs(0.4, 0.9, 1.0, 0.4));
        double B_aiwa = aiwa.apply(WeightedValue.pairs(0.4, 0.9, 1.0, 0.4));

        assertEquals(B_awa, B_aiwa, 0.0001);

    }


    @Test
    public void compareAMWA_lt_0_5() {

        AMWA amwa = new AMWA(1./3.);
        impl = new AWA(1./3., 0.);

        double A_awa  = impl.apply(WeightedValue.pairs(0.4, 0.1, 1.0, 0.7));
        double A_amwa = amwa.apply(WeightedValue.pairs(0.4, 0.1, 1.0, 0.7));

        System.out.println("AWA = " + A_awa + ", AMWA = " + A_amwa);

        assertEquals(A_awa, A_amwa, 0.0001);

        double B_awa  = impl.apply(WeightedValue.pairs(0.4, 0.9, 1.0, 0.4));
        double B_amwa = amwa.apply(WeightedValue.pairs(0.4, 0.9, 1.0, 0.4));

        assertEquals(B_awa, B_amwa, 0.0001);

    }

    @Test
    public void compareAMWA_gt_0_5() {

        AMWA amwa = new AMWA(2./3.);
        impl = new AWA(2./3., 0.);

        double A_awa  = impl.apply(WeightedValue.pairs(0.4, 0.1, 1.0, 0.7));
        double A_amwa = amwa.apply(WeightedValue.pairs(0.4, 0.1, 1.0, 0.7));

        assertEquals(A_awa, A_amwa, 0.0001);

        double B_awa  = impl.apply(WeightedValue.pairs(0.4, 0.9, 1.0, 0.4));
        double B_amwa = amwa.apply(WeightedValue.pairs(0.4, 0.9, 1.0, 0.4));

        assertEquals(B_awa, B_amwa, 0.0001);

    }

    @Test
    public void debugPow() {

        double exp = Math.pow(0.5, 0);

        System.out.println("2^0 = " + Math.pow(2.0, exp));

    }

}
