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

package fuzzy4j.matching;

import fuzzy4j.sets.*;
import fuzzy4j.util.SimpleInterval;
import org.junit.Test;

import static fuzzy4j.util.SimpleInterval.i;
import static junit.framework.Assert.assertEquals;

/**
 * @author Soren <sorend@gmail.com>
 */
public class MinimumSupportTest {

    @Test
    public void calculate_Triangular_Triangular() throws Exception {

        MinimumSupport support = new MinimumSupport();

        TriangularFunction A = new TriangularFunction(0, 1, 2);
        TriangularFunction B = new TriangularFunction(1, 2, 3);
        TriangularFunction C = new TriangularFunction(0.5, 1, 1.5);
        TriangularFunction D = new TriangularFunction(3, 4, 5);

        double[] nec_pos = support.calculate(A, B);
        System.out.printf("[Nec, Pos] = [%f, %f]\n", nec_pos[0], nec_pos[1]);

        nec_pos = support.calculate(A, C);
        System.out.printf("[Nec, Pos] = [%f, %f]\n", nec_pos[0], nec_pos[1]);

        nec_pos = support.calculate(A, D);
        System.out.printf("[Nec, Pos] = [%f, %f]\n", nec_pos[0], nec_pos[1]);
    }



    @Test
    public void calculate_PointsLinear_Interval() throws Exception {


        MinimumSupport support = new MinimumSupport();

        PointsLinearFunction A = new PointsLinearFunction(0.0,
                new Point(1.6, 0.0),
                new Point(2.0, 1.0),
                new Point(Double.POSITIVE_INFINITY, 1.0)
        );

        SimpleInterval B = i(1.7, 1.8);

        double[] necPos = support.calculate(A, B);

        assertEquals(0.25, necPos[0], 0.001);
        assertEquals(0.5,  necPos[1], 0.001);

        // on top
        SimpleInterval B_2 = i(10, 11);
        double[] necPos_2  = support.calculate(A, B_2);
        assertEquals(1.0,  necPos_2[0], 0.001);
        assertEquals(1.0,  necPos_2[1], 0.001);

        // below
        SimpleInterval B_3 = i(0, 1);
        double[] necPos_3  = support.calculate(A, B_3);
        assertEquals(0.0,  necPos_3[0], 0.001);
        assertEquals(1.0,  necPos_3[1], 0.001);

        // from below overlap
        SimpleInterval B_4 = i(0, 1.7);
        double[] necPos_4  = support.calculate(A, B_4);

        assertEquals(0.0,  necPos_4[0], 0.001);
        assertEquals(0.25,  necPos_4[1], 0.001);

        // from top overlap
        SimpleInterval B_5 = i(1.8, 10);
        double[] necPos_5  = support.calculate(A, B_5);

        assertEquals(0.5,  necPos_5[0], 0.001);
        assertEquals(1.0,  necPos_5[1], 0.001);

    }

    @Test
    public void calculate_Interval_Triangular() throws Exception {

        MinimumSupport support = new MinimumSupport();

        TriangularFunction A = new TriangularFunction(0, 1, 2);
        SimpleInterval B_low = new SimpleInterval(true, -2, -1, true);
        SimpleInterval B_hig = new SimpleInterval(true, 3, 4, true);

        SimpleInterval B_i_l = new SimpleInterval(true, -0.5, 0.5, true);

        SimpleInterval B_one = new SimpleInterval(true, 0.5, 0.5, true);

        SimpleInterval B_inner = new SimpleInterval(true, 0.5, 1.0, true);

        SimpleInterval B_outer = new SimpleInterval(true, -0.5, 2.5, true);

        double[] i_1 = support.calculate(A, B_low);
        assertEquals(0.0, i_1[0], 0.0001);
        assertEquals(1.0, i_1[1], 0.0001);

        double[] i_2 = support.calculate(A, B_hig);
        assertEquals(0.0, i_2[0], 0.0001);
        assertEquals(1.0, i_2[1], 0.0001);

        double[] i_3 = support.calculate(A, B_i_l);
        assertEquals(0.0, i_3[0], 0.0001);
        assertEquals(0.5, i_3[1], 0.0001);

        double[] i_4 = support.calculate(A, B_one);
        assertEquals(0.5, i_4[0], 0.0001);
        assertEquals(0.5, i_4[1], 0.0001);

        double[] i_5 = support.calculate(A, B_inner);
        assertEquals(0.5, i_5[0], 0.0001);
        assertEquals(1.0, i_5[1], 0.0001);

        double[] i_6 = support.calculate(A, B_outer);
        assertEquals(0.0, i_6[0], 0.0001);
        assertEquals(1.0, i_6[1], 0.0001);
    }


}
