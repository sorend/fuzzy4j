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

import static junit.framework.Assert.*;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class TruncatedMeanTest {

    TruncatedMean impl;

    @Test
    public void testCreation() {
        try {
            TruncatedMean.BY_PERCENTILE.create();
            fail();
        }
        catch (IllegalArgumentException e) {
        }
        try {
            TruncatedMean.BY_PERCENTILE.create(0, 0.2, 0.3);
            fail();
        }
        catch (IllegalArgumentException e) {
        }

        try {
            TruncatedMean.BY_PERCENTILE.create(null);
            fail();
        }
        catch (IllegalArgumentException e) {
        }

        try {
            TruncatedMean.BY_PERCENTILE.create(0.5);
            fail();
        }
        catch (IllegalArgumentException e) {
        }

        try {
            TruncatedMean.BY_PERCENTILE.create(-0.001);
            fail();
        }
        catch (IllegalArgumentException e) {
        }

        TruncatedMean.BY_PERCENTILE.create(0);
        TruncatedMean.BY_PERCENTILE.create(0.4999);
    }

    @Test
    public void testApply_basic() throws Exception {

        impl = new TruncatedMean(0.25, 0.75);

        assertEquals(Double.NaN, impl.apply());
        assertEquals(Double.NaN, impl.apply(null));

        assertEquals(0.0, impl.apply(0));
        assertEquals(1.0, impl.apply(1));
        assertEquals(0.8, impl.apply(0.8));
    }

    @Test
    public void testApply() throws Exception {

        impl = new TruncatedMean(0.25, 0.75);

        assertEquals(0.5, impl.apply(0.05, 0.1, 0.9, 0.91));

        // not defined yet, assertEquals(0.5, impl.apply(0.05, 0.5, 0.7));
    }

    @Test
    public void testToString() throws Exception {
        impl = new TruncatedMean(0.25, 0.75);
        assertNotNull(impl.toString());
        assertTrue(impl.toString().contains("h_"));
    }
}
