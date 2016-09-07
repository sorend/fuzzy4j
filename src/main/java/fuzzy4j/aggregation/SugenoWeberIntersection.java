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
package fuzzy4j.aggregation;

import fuzzy4j.util.ParametersUtil;

/**
 * Sugeno-Weber class intersection operator.
 *
 * To get a union operator, use {@link fuzzy4j.aggregation.SugenoWeberIntersection#duality()}.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class SugenoWeberIntersection implements Norm {

    public static final ParametricFactory<Norm> T_NORM = new ParametricFactory<Norm>() {
        @Override
        public Norm create(double... params) {
            double p = params[0];
            if (p < -1.)
                throw new IllegalArgumentException("requires p >= -1");
            else if (p == 1.)
                return DrasticIntersection.INSTANCE;
            else if (Double.isInfinite(p))
                return AlgebraicProduct.INSTANCE;
            else
                return new SugenoWeberIntersection(p);
        }
    };

    public static final ParametricFactory INTERSECTION = T_NORM;

    private double p;

    public SugenoWeberIntersection(double p) {
        this.p = p;
    }

    @Override
    public double apply(double... values) {

        ParametersUtil.assertTwoParameters(this.getClass(), "apply", values);

        double a = values[0];
        double b = values[1];

        return Math.max(0., (a + b - 1. + (p * a * b)) / (1. + p));
    }

    @Override
    public Type type() {
        return Type.T_NORM;
    }

    @Override
    public Norm duality() {
        return new DeMorganDuality(this);
    }
}
