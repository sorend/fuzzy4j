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

import fuzzy4j.aggregation.Aggregation;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Wraps a weighted aggregation in the unweighted case, by providing the weights explicitly.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class WithWeightedAggregation implements Aggregation {

    private Logger LOG = Logger.getLogger(WithWeightedAggregation.class.getName());

    private WeightedAggregation inner;
    private double[] weights;

    public WithWeightedAggregation(WeightedAggregation inner, double... weights) {
        this.inner = inner;
        this.weights = weights;
    }

    @Override
    public double apply(double... values) {
        if (values.length != weights.length)
            LOG.warning("values.length != weights.length");
        int min = Math.min(values.length, weights.length);
        WeightedValue[] weightedValues = new WeightedValue[min];
        for (int i = 0; i < min; i++)
            weightedValues[i] = WeightedValue.w(weights[i], values[i]);
        return inner.apply(weightedValues);
    }

    @Override
    public String toString() {
        return "weighted(" + Arrays.asList(weights) + ", " + inner + ")";
    }
}
