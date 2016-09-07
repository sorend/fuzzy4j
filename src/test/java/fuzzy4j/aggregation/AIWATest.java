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
