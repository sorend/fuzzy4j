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
import static junit.framework.Assert.fail;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class MinMaxTest {

    MinMax impl;

    public void testFACTORY() {

        impl = MinMax.FACTORY.create(0.0);
        impl = MinMax.FACTORY.create(1.0);
        impl = MinMax.FACTORY.create(0.5);

        try {
            impl = MinMax.FACTORY.create(0.0, 0.2, 0.3);
            fail();
        }
        catch (Exception e) {
        }

        try {
            impl = MinMax.FACTORY.create(-1.0);
            fail();
        }
        catch (Exception e) {
        }

        try {
            impl = MinMax.FACTORY.create(1.0000001);
            fail();
        }
        catch (Exception e) {
        }
    }

    @Test
    public void testBasics() {

        impl = MinMax.MIDRANGE;

        assertEquals(Double.NaN, impl.apply());
        assertEquals(Double.NaN, impl.apply(null));

        try {
            new MinMax(-0.001);
            fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            new MinMax(1.001);
            fail();
        }
        catch (IllegalArgumentException e) {}
    }

    @Test
    public void testApply() throws Exception {

        impl = MinMax.FACTORY.create(1.0);

        assertEquals(0.43, impl.apply(0.43));

        assertEquals(0.45, impl.apply(0.2, 0.1, 0.4, 0.45));

        impl = MinMax.FACTORY.create(0.0);

        assertEquals(0.22, impl.apply(0.22));

        assertEquals(0.66, impl.apply(0.9, 0.7, 0.66, 0.99));

        impl = MinMax.FACTORY.create(0.5);

        assertEquals(0.55, impl.apply(0.55));

        assertEquals(0.4, impl.apply(0.2, 0.6, 0.4));

        assertEquals(0.2, impl.apply(0.2, 0.3, 0.1));
    }
}
