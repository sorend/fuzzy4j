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

import static fuzzy4j.util.SimpleInterval.i;

/**
 * A trapeze shaped apply function.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class TrapezoidalFunction implements FuzzyFunction, SupportAware, CenterAware {

    private double a, b, d, c;
    private SimpleInterval support;

    /**
     * Creates a trapeze function that goes between [0, 1]
     *
     * @param a the left point of the trapeze where f(x) = 0
     * @param b the left point of the trapeze where f(x) = 1
     * @param c the right point of the trapeze where f(x) = 1
     * @param d the right point of the trapeze where f(x) = 0
     */
    public TrapezoidalFunction(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.support = i(a, d);
    }

    @Override
    public SimpleInterval support() {
        return this.support;
    }

    @Override
    public double apply(double x) {
        if (x < a)
            return 0.0;
        else if (a <= x && x < b)
            return (x - a) / (b - a);
        else if (b <= x && x <= c)
            return 1.0;
        else if (c < x && x <= d)
            return (d - x) / (d - c);
        else
            return 0.0;
    }

    @Override
    public double center() {
        return b + ((c - b) / 2.0);
    }

    public String toString() {
        return "i/¯\\i(" + a + ", " + b + ", " + c + ", " + d + ")";
    }
}
