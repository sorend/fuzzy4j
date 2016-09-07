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

import fuzzy4j.util.SimpleInterval;

import static fuzzy4j.util.SimpleInterval._;

/**
 * A triangular apply function.
 *
 * Defined by three points, which gives us two linear pieces:
 *      f(a) = 0
 *      f(b) = 1
 *      f(c) = 0
 * <pre>
 * {@code y
 *    A
 *  1-|        .
 *    |       /:\
 *    |      / : \
 *    |     /  :  \
 * -0-+----'---:---'-----> x
 *    |   a    b    c
 * }
 * </pre>
 *
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class TriangularFunction implements FuzzyFunction, SupportAware, CenterAware {

    private SimpleInterval support;

    public final double a;
    public final double b;
    public final double c;

    public TriangularFunction(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.support = _(a, c);
    }

    @Override
    public double apply(double x) {
        if (a < x && x < b) {
            if (Double.isInfinite(a))
                return 1.0;
            else
                return (x-a) / (b - a);
        }
        else if (x == b)
            return 1.0;
        else if (b < x && x < c) {
            if (Double.isInfinite(c))
                return 1.0;
            else
                return (c - x) / (c - b);
        }
        else
            return 0.0;


        // return Math.max(0.0, Math.min((x-a) / (b - a), (c - x) / (c - b)));
    }

    @Override
    public SimpleInterval support() {
        return support;
    }

    @Override
    public double center() {
        return b;
    }

    public String toString() {
        return String.format("\u25b3(%f, %f, %f)", a, b, c);
    }
}
