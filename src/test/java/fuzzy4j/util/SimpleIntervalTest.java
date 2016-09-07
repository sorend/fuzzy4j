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

package fuzzy4j.util;

import fuzzy4j.aggregation.ArithmeticMean;
import org.junit.Test;

import static fuzzy4j.util.SimpleInterval._;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class SimpleIntervalTest {

    SimpleInterval impl;

    @Test
    public void testParse() throws Exception {

        impl = SimpleInterval.parse("[0, 1]");
        assertTrue(impl.within(0.0));
        assertTrue(impl.within(1.0));
        assertTrue(impl.within(0.5));
        assertFalse(impl.within(1.1));
        assertFalse(impl.within(-0.1));

        impl = SimpleInterval.parse("(0, 1]");
        assertTrue(impl.within(1.0));
        assertFalse(impl.within(0.0));

        impl = SimpleInterval.parse("[0, 1)");
        assertTrue(impl.within(0.0));
        assertFalse(impl.within(1.0));

        impl = SimpleInterval.parse("[0, 1.1]");
        assertTrue(impl.within(0.0));
        assertTrue(impl.within(1.1));

        try {
            impl = SimpleInterval.parse("0 1");
            fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            impl = SimpleInterval.parse(null);
            fail();
        }
        catch (IllegalArgumentException e) {}

    }

    @Test
    public void testLeftExclusive() throws Exception {

        impl = new SimpleInterval(true, 0.0, 1.0, true);

        assertTrue(impl.within(0.0));

        SimpleInterval impl2 = impl.leftExclusive();

        assertFalse(impl == impl2);

        assertFalse(impl2.within(0.0));
        assertTrue(impl.within(0.0));
    }

    @Test
    public void testRightExclusive() throws Exception {
        impl = new SimpleInterval(true, 0.0, 1.0, true);

        assertTrue(impl.within(1.0));

        SimpleInterval impl2 = impl.rightExclusive();

        assertFalse(impl == impl2);

        assertFalse(impl2.within(1.0));
        assertTrue(impl.within(1.0));
    }

    @Test
    public void testWithin_Inf() throws Exception {

        impl = new SimpleInterval(true, Double.NEGATIVE_INFINITY, 0.0, true);

        assertTrue(impl.within(Double.NEGATIVE_INFINITY));

        impl = impl.leftExclusive();

        assertFalse(impl.within(Double.NEGATIVE_INFINITY));

        impl = new SimpleInterval(true, 0.0, Double.POSITIVE_INFINITY, true);

        assertTrue(impl.within(Double.POSITIVE_INFINITY));
        assertFalse(impl.within(Double.NEGATIVE_INFINITY));

        impl = impl.rightExclusive();

        assertFalse(impl.within(Double.POSITIVE_INFINITY));

        assertFalse(impl.within(Double.NaN));
    }

    @Test
    public void testAggregate() {

        impl = _(0, 10);
        SimpleInterval si2 = _(1, 5);

        SimpleInterval si3 = impl.aggregate(ArithmeticMean.INSTANCE, si2);

        assertEquals(0.5, si3.min());
        assertEquals(7.5, si3.max());
    }

    @Test
    public void testToString() {
        assertEquals("[0,1)", SimpleInterval.parse("[0,1)").toString());
    }

    @Test
    public void testEquals() {

        assertEquals(_(0, 1), _(0, 1));
        assertFalse(_(0, 1).equals(_(0, 1.001)));
        assertFalse(_(0.0001, 1).equals(_(0, 1)));
        assertEquals(_(0, 1).hashCode(), _(0, 1).hashCode());

        assertFalse(_(0, 1).leftExclusive().equals(_(0, 1)));
        assertEquals(_(0, 1).leftExclusive(), _(0, 1).leftExclusive());

        assertFalse(_(0, 1).rightExclusive().equals(_(0, 1)));
        assertEquals(_(0, 1).rightExclusive(), _(0, 1).rightExclusive());
    }
}
