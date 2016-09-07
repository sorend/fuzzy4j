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
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class TrapezoidalFunctionTest {

    TrapezoidalFunction impl;

    @Test
    public void testMembership() throws Exception {

        impl = new TrapezoidalFunction(10, 20, 30, 40);

        assertEquals(0.0, impl.apply(0));
        assertEquals(0.0, impl.apply(10));
        assertEquals(1.0, impl.apply(20));
        assertEquals(1.0, impl.apply(30));
        assertEquals(0.0, impl.apply(40));
        assertEquals(0.0, impl.apply(50));

        assertEquals(0.5, impl.apply(15));
        assertEquals(1.0, impl.apply(25));
        assertEquals(0.5, impl.apply(35));

    }


    @Test
    public void testStartSameAsFirstTop() {

        impl = new TrapezoidalFunction(0, 0, 1, 3);

        assertEquals(1.0, impl.apply(0));
        assertEquals(0.0, impl.apply(-0.00000001));

        impl = new TrapezoidalFunction(0, 1, 2, 2);

        assertEquals(1.0, impl.apply(2));
        assertEquals(0.0, impl.apply(2.0000000001));
    }
}
