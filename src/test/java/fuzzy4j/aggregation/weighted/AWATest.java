/*
 * Copyright (c) 2012, Søren Atmakuri Davidsen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package fuzzy4j.aggregation.weighted;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <soren@tanesha.net>
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
