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

import java.util.Arrays;

/**
 * Implementation of a Weighted Ordered Weighted Averaging (W-OWA) aggregation operator [1].
 *
 * [1] Henrik L. Larsen, "Multiplicative and implicative importance weighted averaging operators with accurate andness direction",
 *      In Proc. of IFSA-EUSFLAT, 2009.
 *
 * @author Soren A. Davidsen <soren@tanesha.net>
 */
public class WeightedOWA implements WeightedAggregation {

    private double[] weights;
    private double p;
    private double l;

    public WeightedOWA(double p, double l, double... weights) {
        this.weights = weights;
        this.p = p;
        this.l = l;

        double sum = 0.0;
        for (double w : weights)
            sum += w;
        double diff = Math.abs(1.0 - sum);
        if (diff > 0.0001)
            throw new IllegalArgumentException(String.format("sum(weights) must be 1.0 (was: %f)", sum));
    }

    @Override
    public double apply(WeightedValue... values) {
        // we need same lenghts
        int min = Math.min(values.length, weights.length);
        // sort
        Arrays.sort(values);
        WeightedOWA.reverse(values);
        //
        if (p >= .5) {
            double s = 0.0;
            double R_exp = l * ((2 * p) - 1.);
            for (int i = 0; i < min; i++) {
                double I_R = 1. - Math.pow(values[i].weight, R_exp) * (1. - values[i].value);
                s += weights[i] * I_R;
            }
            return s;
        }
        else {
            double s = 0.0;
            double R_exp = l * ((2 * (1. - p)) - 1.);
            for (int i = 0; i < min; i++) {
                double I_R = 1. - Math.pow(values[i].weight, R_exp) * values[i].value;
                s += weights[i] * I_R;
            }
            return s;
        }
    }

    /**
     * Calculate the orness of this OWA operator.
     *
     * To get the andness use it's definition: <code>double andness = 1.0 - orness();</code>
     *
     * @return the orness [0, 1]
     */
    public double orness() {
        double n = weights.length;
        double s = 0.0;
        for (int i = 0; i < weights.length; i++)
            s += (n - (i+1)) * weights[i];
        return s / (n - 1.0);
    }

    /**
     * Calculate the relative dispersion (entropy) of the OWA operator.
     *
     * @return the dispersion [0, ln n]
     */
    public double dispersion() {
        double n = weights.length;
        double s = 0.0;
        for (double w : weights)
            if (w > 0.0)
                s += w * Math.log(1.0 / w);
        return s / Math.log(n);
    }

    /**
     * Reverse an array.
     *
     * @param array the array
     */
    public static void reverse(WeightedValue[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        WeightedValue tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

}
