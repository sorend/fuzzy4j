/*
 * Copyright (c) 2013, Søren Atmakuri Davidsen
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

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class HarmonicMeanTest {

    HarmonicMean impl = HarmonicMean.INSTANCE;

    @Test
    public void testApply_basics() throws Exception {

        assertEquals(Double.NaN, impl.apply());
        assertEquals(Double.NaN, impl.apply(null));
        assertEquals(0.0, impl.apply(0));
        assertEquals(1.0, impl.apply(1));
        assertEquals(0.4, impl.apply(0.4));
    }

    @Test
    public void testApply_01() throws Exception {

        // 1, 1, 1 = 3 / 1+1+1 = 1
        assertEquals(1.0, impl.apply(1, 1, 1));

        // 0, 0.5, 1 = 3 / (1/0 + 1/0.5 + 1/1) = 3 / (inf + 2 + 1) = 0
        assertEquals(0.0, impl.apply(0, 0.5, 1));

        // 0.4, 0.8, 0.9 = 3 / (1/0.4 + 1/0.8 + 1/0.9) = 3 / 2.5 + 1.25 + 1.111.. = 3 / 4.8611 = 0.61714
        assertEquals(0.61714, impl.apply(0.4, 0.8, 0.9), 0.00001);
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull(impl.toString());
    }
}
