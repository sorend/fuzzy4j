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
import static junit.framework.Assert.assertTrue;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class AczelAlsinaIntersectionTest {

    AczelAlsinaIntersection impl;

    @Test
    public void testApply_p_0() throws Exception {

        impl = new AczelAlsinaIntersection(0);
    }

    /*

    @Test
    public void testApply_p_inf() throws Exception {

        impl = new AczelAlsinaIntersection(Double.POSITIVE_INFINITY);

        assertEquals(0.2, impl.apply(0.5, 0.2));
    }

    @Test
    public void testApply_p_1() throws Exception {

        impl = new AczelAlsinaIntersection(1);
    }

    @Test
    public void testType() throws Exception {

        impl = new AczelAlsinaIntersection(1.0);

        assertTrue(impl instanceof Norm);

        assertTrue(impl.type() == Norm.Type.T_NORM);
    }

    @Test
    public void testDuality() throws Exception {

        impl = new AczelAlsinaIntersection(1.0);

        assertTrue(impl.type() == Norm.Type.T_NORM);

        assertEquals(0.3, impl.apply(0.6, 0.3));

        Norm dual = impl.duality();

        assertTrue(dual.type() == Norm.Type.T_CONORM);

        assertEquals(0.7, dual.apply(0.2, 0.7));
    }
    */
}
