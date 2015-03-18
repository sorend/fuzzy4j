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

package fuzzy4j;

import fuzzy4j.sets.FuzzyFunction;

import java.util.*;

/**
 * @author Soren <soren@tanesha.net>
 */
public class FuzzyMeasure {

    Map<Integer, Double> mu_U = new HashMap<Integer, Double>();

    public FuzzyMeasure(FuzzyFunction possibilityDistribution, int start, int end) {
        for (int i = start; i <= end; i++)
            mu_U.put(i, possibilityDistribution.apply(i));
    }

    public double[] calculate(int value) {
        return calculate(value, value);
    }

    public double[] calculate(int start, int end) {

        // possibility
        List<Double> pos = new ArrayList<Double>();
        List<Double> nec = new ArrayList<Double>();

        for (int i = start; i <= end; i++) {
            double mu = mu_U.containsKey(i) ? mu_U.get(i) : 0.0;

            pos.add(Math.min(1.0, mu));
        }

        // support of the
        for (int i : mu_U.keySet()) {
            double neg_pos = (i >= start && i <= end) ? 0 : 1;
            nec.add(Math.max(neg_pos, mu_U.get(i)));
        }

        double posRes = Collections.max(pos);
        double necRes = Collections.min(nec);

        return new double[]{ posRes, necRes };
    }
}
