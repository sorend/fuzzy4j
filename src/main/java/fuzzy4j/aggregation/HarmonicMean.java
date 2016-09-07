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
 * Harmonic mean aggregation operator [1]
 *
 * Defined as:
 *      M_H(a_1, a_2, ... a_n) = 1/n Sum^n_k=1 1/a_k
 *
 * [1] Wolfram Alpha, "HarmonicMean", <a href="http://mathworld.wolfram.com/HarmonicMean.html">http://mathworld.wolfram.com/HarmonicMean.html</a>, 2011
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class HarmonicMean implements Aggregation {

    public static HarmonicMean INSTANCE = new HarmonicMean();

    @Override
    public double apply(double... values) {
        if (values == null || values.length == 0)
            return Double.NaN;
        double s = 0.0;
        for (int i = 0; i < values.length; i++)
            s += (1 / values[i]);
        return values.length / s;
    }

    public String toString() {
        return "h_{harmonic}";
    }
}
