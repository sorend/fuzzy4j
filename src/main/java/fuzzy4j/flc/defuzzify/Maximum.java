/*
 * Copyright (c) 2013, Søren Atmakuri Davidsen
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

package fuzzy4j.flc.defuzzify;

import fuzzy4j.flc.Variable;
import fuzzy4j.sets.FuzzyFunction;
import fuzzy4j.util.FuzzyUtil;

/**
 * Support Maximum based defuzzification. Considers maximum of the function, and use this
 * maximum for defuzzified value. The maximum is an interval, the first x where f(x) = max of the
 * maximum, and the last x where f(x) = max.
 *
 * Three types of maximum are implemented. Considering a trapezoidal function T(a, b, c, d)
 * as an example, see below:
 *
 * <ul>
 *   <li>{@link fuzzy4j.flc.defuzzify.Maximum#minOfMax()} Considers the first maximum, for T this is b</li>
 *   <li>{@link fuzzy4j.flc.defuzzify.Maximum#maxOfMax()} Considers the last maximum, for T this is c</li>
 *   <li>{@link fuzzy4j.flc.defuzzify.Maximum#meanOfMax()} Considers the mean of first and last maximum, for T this is (c - b) / 2</li>
 * </ul>
 *
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class Maximum extends SamplingDefuzzify {

    /**
     * Takes Mean of Maximum
     */
    public static Maximum meanOfMax() {
        return new Maximum(MeanOfMax);
    }

    /**
     * Takes Max of Maximum
     */
    public static Maximum maxOfMax() {
        return new Maximum(MaxOfMax);
    }

    /**
     * Takes Min of Maximum
     */
    public static Maximum minOfMax() {
        return new Maximum(MinOfMax);
    }


    private final MaxFinalizer maxFinalizer;
    public double tolerance = 0.00001;

    public Maximum(MaxFinalizer maxFinalizer) {
        this.maxFinalizer = maxFinalizer;
    }

    private class MaximumCallback implements FuzzyUtil.PointCallback {
        double max_x_start, max_x_end;
        double maxy = 0;
        boolean top = false;
        @Override
        public void has(int i, double x, double y) {
            if (y - maxy > tolerance) {
                top = true; // found a new top
                maxy = y;
                max_x_start = x;
                max_x_end = x;
            }
            else if (top && Math.abs(y - maxy) < tolerance) {
                max_x_end = x; // still on top
            }
            else {
                top = false; // not on top anymore
            }
        }
    }


    @Override
    public double apply(Variable variable, FuzzyFunction set) {

        MaximumCallback callback = new MaximumCallback();

        FuzzyUtil.probe(set, variable.min(), variable.max(), getSamples(), callback);

        return maxFinalizer.result(callback.max_x_start, callback.max_x_end);
    }


    private static interface MaxFinalizer {
        double result(double max_start, double max_end);
    }

    public static MaxFinalizer MinOfMax = new MaxFinalizer() {
        @Override
        public double result(double max_start, double max_end) {
            return max_start;
        }
    };

    public static MaxFinalizer MaxOfMax = new MaxFinalizer() {
        @Override
        public double result(double max_start, double max_end) {
            return max_end;
        }
    };

    public static MaxFinalizer MeanOfMax = new MaxFinalizer() {
        @Override
        public double result(double max_start, double max_end) {
            return max_start + ((max_end - max_start) / 2.0);
        }
    };

}
