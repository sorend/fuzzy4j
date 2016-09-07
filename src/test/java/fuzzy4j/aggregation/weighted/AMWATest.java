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
