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
package fuzzy4j.aggregation;

import java.util.Arrays;

/**
 * Aggregation operator using interquartile mean. The interquartile mean, is the arithmetic
 * mean after the 1st and 4th quartile values have been removed.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class TruncatedMean implements Aggregation {

    public static ParametricFactory<TruncatedMean> BY_PERCENTILE = new ParametricFactory<TruncatedMean>() {
        @Override
        public TruncatedMean create(double... params) {
            if (params == null || params.length != 1)
                throw new IllegalArgumentException("requires one parameter (p)");
            return new TruncatedMean(params[0], 1.0 - params[0]);
        }
    };

    public static ParametricFactory<TruncatedMean> BY_RANGE = new ParametricFactory<TruncatedMean>() {
        @Override
        public TruncatedMean create(double... params) {
            if (params == null || params.length != 2)
                throw new IllegalArgumentException("requires two parameters (a, b)");
            return new TruncatedMean(params[0], params[1]);
        }
    };

    public final double truncateLower, truncateUpper;

    public TruncatedMean(double truncateLower, double truncateUpper) {
        if (truncateLower < 0.0 || truncateLower >= 0.5)
            throw new IllegalArgumentException("requires truncateLower in [0, 0.5)");
        if (truncateUpper > 1.0 || truncateUpper <= 0.5)
            throw new IllegalArgumentException("requires truncateUpper in (0.5, 1.0]");
        this.truncateLower = truncateLower;
        this.truncateUpper = truncateUpper;
    }

    @Override
    public double apply(double... values) {
        if (values == null || values.length == 0)
            return Double.NaN;

        Arrays.sort(values);

        int first  = (int) Math.floor(values.length * truncateLower);
        int fourth = (int) Math.ceil(values.length * truncateUpper);

        // delegate to arithmetic mean
        return ArithmeticMean.INSTANCE.apply(Arrays.copyOfRange(values, first, fourth));
    }

    @Override
    public String toString() {
        return "h_{interquartile}";
    }
}
