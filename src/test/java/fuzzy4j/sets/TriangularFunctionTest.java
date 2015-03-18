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

package fuzzy4j.sets;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <soren@tanesha.net>
 */
public class TriangularFunctionTest {

    @Test
    public void center() {

        TriangularFunction f = new TriangularFunction(0, 1, 2);

        assertEquals(1.0, f.center());

        TriangularFunction f2 = new TriangularFunction(0, 10, 11);
        assertEquals(10.0, f2.center());
    }

    @Test
    public void simple() {

        TriangularFunction f = new TriangularFunction(0.0, 1.0, 2.0);

        assertEquals(0.0, f.apply(-100));
        assertEquals(0.0, f.apply(0.0));
        assertEquals(0.5, f.apply(0.5));

        assertEquals(1.0, f.apply(1.0));
        assertEquals(0.5, f.apply(1.5));

        assertEquals(0.0, f.apply(2.0));

        assertEquals(0.0, f.apply(100.0));
    }

    @Test
    public void leftOpen() {

        TriangularFunction f = new TriangularFunction(Double.NEGATIVE_INFINITY, 1.0, 2.0);

        assertEquals(1.0, f.apply(-100));
        assertEquals(1.0, f.apply(0.0));
        assertEquals(1.0, f.apply(0.5));

        assertEquals(1.0, f.apply(1.0));
        assertEquals(0.5, f.apply(1.5));

        assertEquals(0.0, f.apply(2.0));

        assertEquals(0.0, f.apply(100.0));
    }

    @Test
    public void rightOpen() {

        TriangularFunction f = new TriangularFunction(0.0, 1.0, Double.POSITIVE_INFINITY);

        assertEquals(0.0, f.apply(-100));
        assertEquals(0.0, f.apply(0.0));
        assertEquals(0.5, f.apply(0.5));

        assertEquals(1.0, f.apply(1.0));
        assertEquals(1.0, f.apply(1.5));

        assertEquals(1.0, f.apply(2.0));

        assertEquals(1.0, f.apply(100.0));
    }

    @Test
    public void testStartMiddleSame() {

        TriangularFunction f = new TriangularFunction(0, 0, 1);

        assertEquals(1.0, f.apply(0));
        assertEquals(0.0, f.apply(-0.000000001));

        f = new TriangularFunction(0, 1, 1);

        assertEquals(1.0, f.apply(1));
        assertEquals(0.0, f.apply(1.00000001));
    }

}
