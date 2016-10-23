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
 * Simple implementation of a sequence in Java
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class WeightedValue implements Comparable<WeightedValue> {

    public static WeightedValue[] pairs(double... weightValueParis) {
        if (weightValueParis.length % 2 != 0)
            throw new IllegalArgumentException("not pairs passed, must be even number of arguments");
        WeightedValue[] r = new WeightedValue[weightValueParis.length / 2];
        int j = 0;
        for (int i = 0; i < weightValueParis.length; i += 2) {
            r[j++] = WeightedValue.w(weightValueParis[i], weightValueParis[i + 1]);
        }
        return r;
    }

    public static WeightedValue w(double weight, double value) {
        return new WeightedValue(weight, value);
    }

    public final double value;
    public final double weight;

    public WeightedValue(double weight, double value) {
        this.weight = weight;
        this.value = value;
    }

    @Override
    public int compareTo(WeightedValue o) {
        return Double.compare(this.weight, o.weight);
    }

    public String toString() {
        return String.format("(%.5f, %.5f)", weight, value);
        // return "(" + weight + ", " + value + ")";
    }
}
