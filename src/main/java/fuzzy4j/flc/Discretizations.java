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

package fuzzy4j.flc;

import static fuzzy4j.flc.Term.term;

/**
 * Implementation of simple arithmetic discretizations.
 *
 * @author Soren <soren@tanesha.net>
 */
public class Discretizations {

    /**
     * Creates a discretization based on a set of triangular apply functions distributed
     * uniformly over the {@code [start, end]} interval, for the number of labels fuzzy sets.
     *
     * The first first fuzzy set has {@code mu(start) = 1} and the last fuzzy set has {@code mu(end) = 1}.
     * Each set overlaps such that:
     *    {@code if mu_{n}(v) = 1 then mu_{n-1}(v) = 0 and mu_{n+1}(v) = 0}
     *
     * @param start start of range
     * @param end end of range
     * @param labels the labels for the discretized sets.
     * @return terms
     */
    public static Term[] uniformTriangular(double start, double end, String... labels) {

        assert labels != null && labels.length > 1;
        assert start < end;

        double delta = (end - start) / (labels.length - 1);

        Term[] terms = new Term[labels.length];
        for (int i = 0; i < terms.length; i++) {
            double center = start + (i * delta);
            terms[i] = term(labels[i], center - delta, center, center + delta);
        }

        return terms;
    }

}
