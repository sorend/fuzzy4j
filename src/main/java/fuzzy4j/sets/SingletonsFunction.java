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

import fuzzy4j.aggregation.Minimum;
import fuzzy4j.util.FuzzyUtil;
import fuzzy4j.util.SimpleInterval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Soren A. Davidsen <soren@tanesha.net>
 */
public class SingletonsFunction implements FuzzyFunction, SupportAware {

    private double defaultValue = 0.0;
    private Point[] elements;

    public SingletonsFunction(double defaultValue, Point... elements) {
        this.defaultValue = defaultValue;
        this.elements = elements;
    }

    public SingletonsFunction(double... points) {
        elements = new Point[points.length];
        for (int i = 0; i < points.length; i++)
            elements[i] = new Point(points[i], 1.0);
    }

    public SingletonsFunction(Point... elements) {
        this.elements = elements;
    }

    @Override
    public double apply(double x) {
        for (int i = 0; i < elements.length; i++)
            if (elements[i].x == x)
                return elements[i].y;
        return defaultValue;
    }

    @Override
    public SimpleInterval support() {
        double min = Double.POSITIVE_INFINITY, max = Double.NEGATIVE_INFINITY;
        for (Point p : elements) {
            min = Math.min(p.x, min);
            max = Math.max(p.x, max);
        }
        return new SimpleInterval(true, min, max, true);
    }

    public Iterator<Point> iterator() {
        return Arrays.asList(elements).iterator();
    }
}
