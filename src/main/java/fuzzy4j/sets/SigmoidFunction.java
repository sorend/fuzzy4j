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

package fuzzy4j.sets;

import fuzzy4j.util.SimpleInterval;

/**
 * Sigmoidal apply function.
 *
 * Defined as:
 *      <code>mu(x, alpha, x0) = (1 + e^(-alpha*(x-x0)))^-1</code>
 *
 * Where:
 *      x0 is the location of the x-axis position of the center of the curve.
 *      alpha defines the steepness of the curve. alpha is large means steep, alpha is small (<0) means less steep.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class SigmoidFunction implements FuzzyFunction, SupportAware {

    private double alpha;
    private double x0;

    public SigmoidFunction(double alpha, double x0) {
        this.alpha = alpha;
        this.x0 = x0;
    }

    @Override
    public double apply(double x) {
        return 1.0 / (1.0 + Math.exp(-alpha * (x - x0)));
    }

    @Override
    public SimpleInterval support() {
        double min = x0 - 9 / Math.abs(alpha);
        double max = x0 + 9 / Math.abs(alpha);
        return new SimpleInterval(true, min, max, true);
    }
}
