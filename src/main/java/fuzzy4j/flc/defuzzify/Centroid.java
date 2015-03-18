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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Support Centroid-type defuzzification.
 *
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class Centroid extends SamplingDefuzzify {

    private final Logger LOG = Logger.getLogger(Centroid.class.getName());

    private class CentroidCallback implements FuzzyUtil.PointCallback {
        double centroid_x = 0;
        double centroid_y = 0;
        double area = 0;
        @Override
        public void has(int i, double x, double y) {
            centroid_x += x * y;
            centroid_y += y * y;
            area += y;
        }
    }

    @Override
    public double apply(Variable variable, FuzzyFunction set) {

        CentroidCallback callback = new CentroidCallback();

        FuzzyUtil.probe(set, variable.min(), variable.max(), getSamples(), callback);

        return callback.centroid_x / callback.area;
    }
}
