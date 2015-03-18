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
import fuzzy4j.sets.*;
import org.junit.Test;

import static fuzzy4j.sets.Point.$;
import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class CentroidTest {

    Centroid impl = new Centroid();
    Variable var = Variable.output("hello");

    @Test
    public void testExample_01_Triangular() {

        TriangularFunction set = new TriangularFunction(0, 5, 10);
        var.start(0).end(10);

        double res = impl.apply(var, set);

        assertEquals(5, res, 0.001);
    }

    @Test
    public void testExample_02_Trapezoidal() {

        TrapezoidalFunction set = new TrapezoidalFunction(0, 5, 10, 15);
        var.start(0).end(15);

        double res = impl.apply(var, set);

        assertEquals(7.5, res, 0.001);
    }

    @Test
    public void testExample_03_Composite() {

        // example taken from:
        // http://www.intelligent-systems.info/classes/ee509/9.PDF

        PointsLinearFunction f1 = new PointsLinearFunction(0.0, $(0, 0), $(1, 0.3), $(4, 0.3), $(5, 0));
        PointsLinearFunction f2 = new PointsLinearFunction(0.0, $(3, 0), $(4, 0.5), $(6, 0.5), $(7, 0));
        PointsLinearFunction f3 = new PointsLinearFunction(0.0, $(5, 0), $(6, 1), $(7, 1), $(8, 0));

        CompositeFunction f = new CompositeFunction(fuzzy4j.aggregation.Maximum.INSTANCE, f1, f2, f3);

        System.out.println("f = " + f);

        var.start(0).end(8);

        double res = impl.apply(var, f);

        System.out.println("res = " + res);

        assertEquals(4.9, res, 0.1);

    }

}
