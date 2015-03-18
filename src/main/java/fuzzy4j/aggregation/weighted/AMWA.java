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
 * Andness-directed Multiplicativ-importance Weighted Averaging.
 *
 * @author Soren A. Davidsen <soren@tanesha.net>
 */
public class AMWA implements WeightedAggregation {

    public final double p;
    public final double alpha;

    public AMWA(double p) {
        this.p = p;
        this.alpha = (1. - p) / p;
    }

    @Override
    public double apply(WeightedValue... values) {

        double s_w = 0.0;
        for (int i = 0; i < values.length; i++)
            s_w += values[i].weight;

        if (p <= .5) {
            double s = 0.0;
            for (int i = 0; i < values.length; i++) {
                double y_i = values[i].weight / s_w;
                s += y_i * Math.pow(values[i].value, alpha);
            }
            return Math.pow(s, 1. / alpha);
        }
        else {
            double s = 0.0;
            double a2 = p / (1. - p);
            for (int i = 0; i < values.length; i++) {
                double y_i = values[i].weight / s_w;
                s += y_i * Math.pow(1. - values[i].value, a2);
            }
            return 1. - Math.pow(s, 1. / a2);
        }
    }
}
