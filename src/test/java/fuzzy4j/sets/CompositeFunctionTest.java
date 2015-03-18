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

package fuzzy4j.sets;

import fuzzy4j.aggregation.AlgebraicProduct;
import fuzzy4j.aggregation.Minimum;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class CompositeFunctionTest {

    CompositeFunction impl;

    @Test
    public void testMembership_01() throws Exception {

        ConstantFunction f1 = new ConstantFunction(0.5);
        TriangularFunction f2 = new TriangularFunction(0.0, 1.0, 2.0);

        impl = new CompositeFunction(AlgebraicProduct.INSTANCE, f1, f2);

        assertEquals(0.5, impl.apply(1.0));
        assertEquals(0.25, impl.apply(0.5));

        assertEquals(0.0, impl.apply(-10));
    }

    @Test
    public void testMembership_02() throws Exception {

        ConstantFunction f1 = new ConstantFunction(0.5);
        TriangularFunction f2 = new TriangularFunction(0.0, 1.0, 2.0);

        impl = new CompositeFunction(Minimum.INSTANCE, f1, f2);

        assertEquals(0.5, impl.apply(1.0));
        assertEquals(0.0, impl.apply(0.0));

        assertEquals(0.0, impl.apply(-10));
    }

}
