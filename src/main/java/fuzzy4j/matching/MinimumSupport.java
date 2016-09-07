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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Calculates the minimum support of two functions.
 * @author Soren <sorend@gmail.com>
 */
public class MinimumSupport {

    public SimpleInterval overlap(SimpleInterval A_sup, SimpleInterval B_sup) {

        double min = Math.max(A_sup.min(), B_sup.min());
        double max = Math.min(A_sup.max(), B_sup.max());

        if (min > max)
            return null;
        else
            return new SimpleInterval(true, min, max, true);
    }

    private static void update(Point p, SimpleInterval overlap, List<Double> nec, List<Double> pos) {
        // no intersection
        if (p == null)
            return;

        if (!(0 < p.y && p.y < 1)) // outside the unit interval, meaning outside of fuzzy function def.
            return;

        if (!overlap.within(p.x)) // intersection is outside the overlap, we don't care about it.
            return;

        // update necessity
        nec.add(p.y);
        pos.add(p.y);
    }

    public void updateNecPos(List<Double> pos, List<Double> nec, Point p, FuzzyFunction B) {
        pos.add(Math.min(p.y, B.apply(p.x)));
        nec.add(Math.max(p.y, 1.0 - B.apply(p.x)));
    }

    public double[] calculate(PointsLinearFunction A, SimpleInterval B) {

        SimpleInterval A_sup = A.support(); // find spport
        SimpleInterval B_sup = B.support();

        SimpleInterval overlap = overlap(A_sup, B_sup); // calculate overlapping support

        if (overlap == null)
            return new double[]{0, 1};

        List<Double> nec = new ArrayList<Double>();
        List<Double> pos = new ArrayList<Double>();

        // border cases
        updateNecPos(nec, pos, new Point(overlap.min(), A.apply(overlap.min())), B);
        updateNecPos(nec, pos, new Point(overlap.max(), A.apply(overlap.max())), B);

        for (Point p : A.points)
            updateNecPos(pos, nec, p, B);

        return new double[]{ Collections.min(nec), Collections.max(pos) };
    }

    public double[] calculate(TriangularFunction A, SimpleInterval B) {

        SimpleInterval A_sup = A.support(); // find spport
        SimpleInterval B_sup = B.support();

        SimpleInterval overlap = overlap(A_sup, B_sup); // calculate overlapping support

        if (overlap == null)
            return new double[]{0, 1};

        List<Double> values = new ArrayList<Double>();
        values.add(A.apply(overlap.min()));
        values.add(A.apply(overlap.max()));

        if (overlap.within(A.b))
            values.add(1.0);

        return new double[]{ Collections.min(values), Collections.max(values) };
    }

    public double[] calculate(TriangularFunction A, TriangularFunction B) {

        SimpleInterval A_sup = A.support(); // find spport
        SimpleInterval B_sup = B.support();

        SimpleInterval overlap = overlap(A_sup, B_sup);

        if (overlap == null)
            return new double[]{0, 1};

        // calculate points for intersection of each of the segments.
        Line A_left = Line.fromPoints(new Point(A.a, 0.0), new Point(A.b, 1.0));
        Line A_right = Line.fromPoints(new Point(A.b, 1.0), new Point(A.c, 0.0));

        Line B_left = Line.fromPoints(new Point(B.a, 0.0), new Point(B.b, 1.0));
        Line B_right = Line.fromPoints(new Point(B.b, 1.0), new Point(B.c, 0.0));

        List<Double> nec = new ArrayList<Double>();
        List<Double> pos = new ArrayList<Double>();

        nec.add(Math.max(A.apply(overlap.min()), 1.0 - B.apply(overlap.min())));
        nec.add(Math.max(A.apply(overlap.max()), 1.0 - B.apply(overlap.max())));

        pos.add(Math.min(A.apply(overlap.min()), B.apply(overlap.min())));
        pos.add(Math.min(A.apply(overlap.max()), B.apply(overlap.max())));

        Point A_1_B_1 = A_left.intersection(B_left);
        update(A_1_B_1, overlap, nec, pos);

        Point A_1_B_2 = A_left.intersection(B_right);
        update(A_1_B_2, overlap, nec, pos);

        Point A_2_B_1 = A_right.intersection(B_left);
        update(A_2_B_1, overlap, nec, pos);

        Point A_2_B_2 = A_right.intersection(B_right);
        update(A_2_B_1, overlap, nec, pos);

        System.out.println("A_left=" + A_left + ", A_right=" + A_right + ", B_left=" + B_left + ", B_right=" + B_right);
        System.out.println("intersecting: left/left=" + A_1_B_1 + ", left/right=" + A_1_B_2 + ", " +
                "right/left=" + A_2_B_1 + ", right/right=" + A_2_B_2);

        double a_pos = Collections.max(pos);
        double a_nec = Collections.min(nec);

        return new double[]{ a_nec, a_pos };
    }

}
