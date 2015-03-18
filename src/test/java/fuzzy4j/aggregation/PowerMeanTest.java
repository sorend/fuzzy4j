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

import java.util.Random;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class PowerMeanTest {

    PowerMean impl;

    @Test
    public void testParametricFactory() throws Exception {

        impl = PowerMean.FACTORY.create(1.0);

        assertEquals(1.0, impl.p);

        try {
            PowerMean.FACTORY.create();
            fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            PowerMean.FACTORY.create(1.0, 2.0, 3.0);
            fail();
        }
        catch (IllegalArgumentException e) {}
    }

    public double[] random(int n) {
        Random rand = new Random();
        double[] res = new double[n];
        for (int i = 0; i < n; i++)
            res[i] = rand.nextDouble();
        return res;
    }

    @Test
    public void testApply_basic() throws Exception {

        impl = PowerMean.FACTORY.create(0.5);

        assertEquals(Double.NaN, impl.apply());
        assertEquals(Double.NaN, impl.apply(null));

        assertEquals(0.0, impl.apply(0));
        assertEquals(1.0, impl.apply(1));
        assertEquals(0.22, impl.apply(0.22), 0.00001);
    }


    @Test
    public void testApply() throws Exception {

        impl = new PowerMean(1.0);

        assertEquals(1.0, impl.apply(1.0));

        // for p = 1, we have the arithmetic mean.
        ArithmeticMean mean = ArithmeticMean.INSTANCE;
        double[] rand = random(10);
        assertEquals(mean.apply(rand), impl.apply(rand));

        impl = new PowerMean(1.1);
        assertTrue(mean.apply(rand) < impl.apply(rand));

        impl = new PowerMean(0.9);
        assertTrue(mean.apply(rand) > impl.apply(rand));
    }

    @Test
    public void testToString() {

    }
}
