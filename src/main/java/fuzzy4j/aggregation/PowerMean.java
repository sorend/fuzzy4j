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
 * Aggregation operator using power-mean [1]. The power mean is also commonly called generalized mean.
 *
 * Defined as:
 *   <code>M_p(a_1, a_2, .. a_n) = ( 1/n Sum^n_k=1 a_n^p )^(1/p)</code>
 *
 * Where "p" controls order of power.
 *
 *
 * [1] Wolfram Alpha, Power Mean, http://mathworld.wolfram.com/PowerMean.html, 2011
 *
 * @author Soren A. Davidsen <soren@tanesha.net>
 */
public class PowerMean implements Aggregation {

    public static ParametricFactory<PowerMean> FACTORY = new ParametricFactory<PowerMean>() {
        @Override
        public PowerMean create(double... params) {
            if (params == null || params.length != 1)
                throw new IllegalArgumentException("one parameter expected (p)");
            return new PowerMean(params[0]);
        }
    };

    public final double p;

    public PowerMean(double p) {
        this.p = p;
    }

    @Override
    public double apply(double... values) {
        if (values == null || values.length == 0)
            return Double.NaN;
        double s = 0.0;
        for (int i = 0; i < values.length; i++) {
            s += Math.pow(values[i], p);
        }
        return Math.pow(s / values.length, 1.0 / p);
    }
}
