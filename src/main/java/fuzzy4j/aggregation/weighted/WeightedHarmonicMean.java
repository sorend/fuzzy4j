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
 * Weighted harmonic mean aggregation operator [1]
 *
 * Defined as:
 *      h_WH((w_1, a_1), (w_2, a_2), ... (w_n, a_n)) = (Sum_i w_i) / (Sum_i w_i/a_i))
 *
 * [1] http://en.wikipedia.org/wiki/Weighted_harmonic_mean#Weighted_harmonic_mean
 *
 * @author Soren A. Davidsen <soren@tanesha.net>
 */
public class WeightedHarmonicMean implements WeightedAggregation {

    @Override
    public double apply(WeightedValue... values) {
        // check if we have any 0 values (result is always 0 then)
        for (int i = 0; i < values.length; i++)
            if (values[i].value == 0.0)
                return 0.0;

        // calculate
        double n = 0.0;
        double d = 0.0;
        for (int i = 0; i < values.length; i++) {
            n += values[i].weight;
            d += values[i].weight / values[i].value;
        }
        return n / d;
    }
}
