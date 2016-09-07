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

/**
 * Pi apply function. Called Pi function because it's shape looks like Pi (uppercase).
 *
 * <pre>
 * {@code y
 *    A
 *  1-|          .-------.
 *    |         /    :    \
 *    |       _/     :     \_
 *    |     _/       :       \_
 * -0-+----'---------:---------'------> x
 *    |   a     p    r    q     b
 * }
 * </pre>
 *
 * [1] Saroj K. Meher, "A new Fuzzy Supervised Classification Method based on Aggregation Operator",
 *  in 3rd Int. IEEE Conf. on Signal-Image Tech. and Internet-Based Systems, 2008.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class PiFunction implements FuzzyFunction, CenterAware {

    public final double a, r, b, m, p, q;
    // private double a, r, b, m, p, q;

    public PiFunction(double a, double r, double b, double m) {
        this.a = a;
        this.r = r;
        this.b = b;
        this.m = m;
        this.p = (r + a) / 2.0;
        this.q = (b + r) / 2.0;
    }

    @Override
    public double apply(double x) {
        if (x <= a)
            return 0.0;
        else if (a < x && x <= p)
            return Math.pow(2, m - 1) * Math.pow((x-a) / (r-a), m);
        else if (p < x && x <= r)
            return 1.0 - (Math.pow(2, m - 1) * Math.pow((r - x) / (r - a), m));
        else if (r < x && x <= q)
            return 1.0 - (Math.pow(2, m - 1) * Math.pow((x - r)/(b - r), m));
        else if (q < x && x <= b)
            return (Math.pow(2, m - 1) * Math.pow((b - x) / (b - r), m));
        else // (b < x)
            return 0.0;
    }

    @Override
    public String toString() {
        return String.format("Pi(%.2f, %.2f, %.2f)", a, r, b);
    }

    @Override
    public double center() {
        return r;
    }
}
