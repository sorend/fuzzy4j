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
 * Andness-directed Implicative-importance Weighted Averaging.
 *
 * @author Soren A. Davidsen <soren@tanesha.net>
 */
public class RollingAIWA implements WeightedRollingAggregation<RollingAIWA> {

    public final double p;
    public final double alpha;

    private double s = 0, d = 0;

    public RollingAIWA(double p) {
        assert 0 <= p && p <= 1;
        this.p = p;
        this.alpha = (1. - p) / p;
    }

    @Override
    public RollingAIWA clear() {
        s = d = 0;
        return this;
    }

    @Override
    public RollingAIWA add(WeightedValue... values) {
        for (int i = 0; i < values.length; i++) {
            if (p <= 0.5) {
                s += Math.pow(values[i].weight * values[i].value, alpha);
                d += Math.pow(values[i].weight, alpha);
            }
            else {
                s += Math.pow(values[i].weight * (1. - values[i].value), 1./alpha);
                d += Math.pow(values[i].weight, 1./alpha);
            }
        }
        return this;
    }

    @Override
    public double value() {
        if (p <= .5)
            return Math.pow(s / d, 1. / alpha);
        else
            return 1. - Math.pow(s / d, alpha);
    }
}
