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

package fuzzy4j.flc.defuzzify;

import fuzzy4j.flc.Variable;
import fuzzy4j.sets.FuzzyFunction;
import fuzzy4j.sets.PiFunction;
import fuzzy4j.sets.TrapezoidalFunction;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class MaximumTest {

    Maximum impl;

    Variable var;
    FuzzyFunction set;

    @Before
    public void setup() {
        var = Variable.input("hello").start(0).end(15);
        set = new TrapezoidalFunction(0, 5, 10, 15);
    }

    @Test
    public void maxOfMax() throws Exception {
        impl = Maximum.maxOfMax();
        double res = impl.apply(var, set);
        assertEquals(10, res, 0.05);
    }

    @Test
    public void minOfMax() throws Exception {
        impl = Maximum.minOfMax();
        double res = impl.apply(var, set);
        assertEquals(5, res, 0.05);
    }

    @Test
    public void meanOfMax() throws Exception {
        impl = Maximum.meanOfMax();
        double res = impl.apply(var, set);
        assertEquals(7.5, res, 0.05);
    }

    public void pifunctionExample() {

        // this type of function should have only a single max value.
        set = new PiFunction(5, 10, 15, 1);

        double res1 = Maximum.meanOfMax().apply(var, set);

        double res2 = Maximum.minOfMax().apply(var, set);

        double res3 = Maximum.maxOfMax().apply(var, set);

        assertEquals(10, res1, 0.05);
        assertEquals(res1, res2);
        assertEquals(res1, res3);

    }

}
