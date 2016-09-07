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
 * @author Soren <sorend@gmail.com>
 */
public class LineTest {
    @Test
    public void testFromPoints_increasing() throws Exception {

        Line l = Line.fromPoints(new Point(0, 0), new Point(1, 1));

        assertEquals(l.m, 1.0, 0.00000001);
        assertEquals(l.b, 0.0, 0.00000001);

        // order of points irrelevant
        Line l2 = Line.fromPoints(new Point(1, 1), new Point(0, 0));

        assertEquals(l2.m, 1.0, 0.00000001);
        assertEquals(l2.b, 0.0, 0.00000001);
    }

    @Test
    public void testFromPoints_decreasing_not_b_0() throws Exception {

        Line l = Line.fromPoints(new Point(2, 0), new Point(1, 1));

        assertEquals(-1.0, l.m, 0.00000001);
        assertEquals(2.0, l.b, 0.00000001);
    }

    @Test
    public void testIntersection() throws Exception {

        Line l1 = Line.fromPoints(new Point(0, 0), new Point(1, 1));
        Line l2 = Line.fromPoints(new Point(2, 0), new Point(1, 1));

        // both lines are defined by the point (1, 1) so they must intersect there.
        Point i = l1.intersection(l2);
        assertEquals(1.0, i.x);
        assertEquals(1.0, i.y);
    }

    @Test
    public void testEval() throws Exception {

    }

    @Test
    public void testIsIncreasing() throws Exception {

    }

    @Test
    public void testIsDecreasing() throws Exception {

    }
}
