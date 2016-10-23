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
package fuzzy4j.aggregation.weighted;

/**
 * Andness-directed Weighted Averaging operator [1].
 *
 * The AWA operator is controlled by two parameters:
 *  "p" controls the andness.
 *  "l" controls the function between multiplicative (l = 0) and implicative (l = 1) semantics.
 *
 * [1] H. L. Larsen, "Multiplicative and implicative importance weighted averaging aggregation operators with accurate andness direction",
 *      In Proc. of IFSA-EUSFLAT 2009, 2009.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class AWA implements WeightedAggregation {

    public final double p;
    public final double l;
    // public final double y_exp;
    // public final double alpha;

    public AWA(double p, double l) {
        this.p = p;
        this.l = l;
        // this.alpha = (1. - p) / p;
        // this.y_exp = Math.pow(alpha, l);
    }

    @Override
    public double apply(WeightedValue... values) {

        if (p <= .5) {
            double alpha = (1. - p) / p;
            double y_exp = Math.pow(alpha, l);
            return doCalc(y_exp, alpha, values);
        }
        else {
            WeightedValue[] invV = new WeightedValue[values.length];
            for (int i = 0; i < values.length; i++)
                invV[i] = WeightedValue.w(values[i].weight, 1. - values[i].value);

            double alpha = (1. - (1. - p)) / (1. - p);
            double y_exp = Math.pow(alpha, l);

            return 1. - doCalc(y_exp, alpha, invV);
        }
    }

    private static double doCalc(double y_exp, double alpha, WeightedValue... values) {
        double y_s = 0.0;
        for (int i = 0; i < values.length; i++)
            y_s += Math.pow(values[i].weight, y_exp);

        double s = 0.;
        for (int i = 0; i < values.length; i++) {
            s += (Math.pow(values[i].weight, y_exp) / y_s) * Math.pow(values[i].value, alpha);
        }
        return Math.pow(s, 1. / alpha);
    }

    @Override
    public String toString() {
        return String.format("AWA(p: %f, l: %f)", p, l);
    }
}