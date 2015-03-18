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
