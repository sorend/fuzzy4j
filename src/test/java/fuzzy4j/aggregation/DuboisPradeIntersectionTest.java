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
import static junit.framework.Assert.fail;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class DuboisPradeIntersectionTest {

    DuboisPradeIntersection impl;

    @Test
    public void testFactory() {

        impl = DuboisPradeIntersection.FACTORY.create(0);

        try {
            impl = DuboisPradeIntersection.FACTORY.create();
            fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            impl = DuboisPradeIntersection.FACTORY.create(null);
            fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            impl = DuboisPradeIntersection.FACTORY.create(1.0, 0.0);
            fail();
        }
        catch (IllegalArgumentException e) {}
    }

    @Test
    public void testCreate() {
        impl = new DuboisPradeIntersection(0);
        impl = new DuboisPradeIntersection(1);

        try {
            impl = new DuboisPradeIntersection(-0.00001);
            fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            impl = new DuboisPradeIntersection(1.000001);
            fail();
        }
        catch (IllegalArgumentException e) {}

    }

    @Test
    public void testApply() throws Exception {

        impl = new DuboisPradeIntersection(0);

        // (0.5 * 0.8) / max(0.5, 0.8, 0.0) = 0.4 / 0.8 = 0.5
        assertEquals(0.5, impl.apply(0.5, 0.8));

        impl = new DuboisPradeIntersection(1.0);

        // 0.5 * 0.8 / max(0.5, 0.8, 1.0) = 0.4 / 1 = 0.4
        assertEquals(0.4, impl.apply(0.5, 0.8));

        try {
            impl.apply(0.1, 0.2, 0.3);
            fail();
        }
        catch (IllegalArgumentException e) {}

    }

    @Test
    public void testType() throws Exception {
        impl = new DuboisPradeIntersection(0);
        assertEquals(Norm.Type.T_NORM, impl.type());
    }

    @Test
    public void testDuality() throws Exception {
        impl = new DuboisPradeIntersection(0);
        assertEquals(Norm.Type.T_NORM, impl.type());
        Norm dual = impl.duality();
        assertEquals(Norm.Type.T_CONORM, dual.type());

        // (0.5 * 0.8) / max(0.5, 0.8, 0.0) = 0.4 / 0.8 = 0.5
        assertEquals(0.5, impl.apply(0.5, 0.8));

        // 1 - ((1 - 0.5 * 1 - 0.8) / max(1 - 0.5, 1 - 0.8, 0.0)) =
        // 1 - (0.5 * 0.2) / max(0.5, 0.2, 0.0) = 1 - (0.1 / 0.5) = 1 - 0.2 = 0.8
        assertEquals(0.8, dual.apply(0.5, 0.8));
    }

    @Test
    public void testToString() throws Exception {
        impl = new DuboisPradeIntersection(0);
        assertNotNull(impl.toString());
    }
}
