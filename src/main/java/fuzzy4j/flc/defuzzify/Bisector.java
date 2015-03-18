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

/**
 * Support Bisector-type defuzzification.
 *
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class Bisector extends SamplingDefuzzify {

    @Override
    public double apply(Variable variable, FuzzyFunction set) {

        double min = variable.min();
        double max = variable.max();
        int samples = getSamples();
        double dx = (max - min) / samples;

        int left = 0, right = 0;
        double leftA = 0, rightA = 0;
        double xleft = min, xright = max;
        while (samples-- > 0) {
            if (leftA <= rightA) {
                xleft = min + (left + 0.5) * dx;
                leftA += set.apply(xleft);
                left++;
            }
            else {
                xright = max - (right + 0.5) * dx;
                rightA += set.apply(xright);
                right++;
            }
        }

        // apply bisector
        return (leftA * xright + rightA * xleft) / (leftA + rightA);
    }
}
