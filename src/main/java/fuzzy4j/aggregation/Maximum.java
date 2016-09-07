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
 * Maximum (max) aggregation operator
 *
 * Defined as:
 *    <code>M_M(a_1, a_2, ... a_n) = max^n_k=1 a_k</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class Maximum implements Norm, Aggregation {

    public static final Maximum INSTANCE = new Maximum();

    @Override
    public double apply(double... values) {
        return minmax(values)[1];
    }

    @Override
    public Type type() {
        return Type.T_CONORM;
    }

    public String toString() {
        return type() + "_max";
    }

    @Override
    public Norm duality() {
        return Minimum.INSTANCE;
    }

    public static double[] minmax(double[] values) {
        if (values == null || values.length == 0)
            return new double[] { Double.NaN, Double.NaN };
        double min = values[0], max = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < min)
                min = values[i];
            if (values[i] > max)
                max = values[i];
        }
        return new double[]{ min, max };
    }
}
