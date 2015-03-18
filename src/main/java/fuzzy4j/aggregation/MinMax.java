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
package fuzzy4j.aggregation;

/**
 * MinMax operator. Takes a weighted average of minimum and maximum.
 *
 * Defined as <code>h_minmax(v) = (1-p) * min(v) + p * max(v)</code>.
 *
 * @author Soren A. Davidsen <soren@tanesha.net>
 */
public class MinMax implements Aggregation {

    public static MinMax MIDRANGE = new MinMax(0.5);

    public static ParametricFactory<MinMax> FACTORY = new ParametricFactory<MinMax>() {
        @Override
        public MinMax create(double... params) {
            if (params == null || params.length != 1)
                throw new IllegalArgumentException("minmax expects one parameter (p)");
            return new MinMax(params[0]);
        }
    };

    private double p;

    public MinMax(double p) {
        if (p < 0 || p > 1)
            throw new IllegalArgumentException("p must be in [0, 1]");
        this.p = p;
    }

    @Override
    public double apply(double... values) {
        double[] minmax = Maximum.minmax(values);
        return (1.-p) * minmax[0] + p * minmax[1];
    }
}
