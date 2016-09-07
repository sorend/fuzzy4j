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

/**
 * Implementation of an S-shape membershipi function.
 *
 * Negate the function to create Z-shape.
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class SFunction implements FuzzyFunction, SupportAware, CenterAware {

    private double a;
    private double b;
    private double ab_middle;

    /**
     * Creates an S function that goes between [0, 1].
     *
     * @param a The point where x starts to to grow. f(x) = 0 forall x < a
     * @param b The point where x is at 1, f(x) = 1 forall x > b
     */
    public SFunction(double a, double b) {
        this.a = Math.min(a, b);
        this.b = Math.max(a, b);
        this.ab_middle = this.a + ((this.b - this.a) / 2);
    }

    @Override
    public double apply(double x) {
        if (x <= a) // up to a
            return 0;
        else if (x <= ab_middle) // a up to ab_middle
            return 2.0 * Math.pow(((x - a) / (b - a)), 2);
        else if (x <= b) // ab_middle up to b
            return 1.0 - (2.0 * Math.pow(((b - x) / (b - a)), 2));
        else // more than b
            return 1;
    }

    @Override
    public SimpleInterval support() {
        return new SimpleInterval(false, a, Double.POSITIVE_INFINITY, true);
    }

    @Override
    public double center() {
        return b;
    }

    public String toString() {
        return "S(x, " + a + ", " + ab_middle + ", " + b + ")";
    }
}
