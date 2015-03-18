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

package fuzzy4j.negation;

import fuzzy4j.sets.FuzzyFunction;
import fuzzy4j.sets.TriangularFunction;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class SugenoNegationTest {

    SugenoNegation impl;

    FuzzyFunction ff = new TriangularFunction(0, 1, 2);

    @Test
    public void testSimple() {

        // (1 - x) / (1 + 1 * x)
        impl = new SugenoNegation(1.0, ff);

        // x = 0, (1 - 0) / (1 + 1 * 0) = 1 / 1 = 1
        assertEquals(1.0, impl.apply(0));

        // x = 1, (1 - 1) / ... = 0 / ... = 0
        assertEquals(0.0, impl.apply(1));
        assertEquals(1.0, impl.apply(2));

        // x = 0.5, (1 - 0.5) / (1 + 1 * 0.5) = 0.5 / 1.5 = 0.333..
        assertEquals(0.333, impl.apply(0.5), 0.001);

        // x = 0.1, (1 - 0.1) / (1 + 1 * 0.1) = 0.9 / 1.1 = 0.8181..
        assertEquals(0.818, impl.applyNegation(0.1), 0.001);

        // x = 0.9, (1 - 0.9) / (1 + 1 * 0.9) = 0.1 / 1.9 = 0.052..
        assertEquals(0.052, impl.applyNegation(0.9), 0.001);
    }

    @Test
    public void testToString() {
        impl = new SugenoNegation(1.0, ff);
        assertNotNull(impl.toString());
    }

}
