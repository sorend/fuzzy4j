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
 * Aczel-Alsina class intersection operator.
 *
 * To get a union operator, use {@link fuzzy4j.aggregation.AczelAlsinaIntersection#duality()}.
 *
 * Aczel-Alsina takes a single parameter "p" which must be in [0, inf]
 *
 * @author Soren A. Davidsen <soren@tanesha.net>
 */
public class AczelAlsinaIntersection implements Norm {

    public static final ParametricFactory<Norm> T_NORM = new ParametricFactory<Norm>() {
        @Override
        public Norm create(double... params) {
            if (params == null || params.length != 1)
                throw new IllegalArgumentException("One parameter expected (p)");
            double p = params[0];
            if (p == 0.)
                return DrasticIntersection.INSTANCE;
            else if (Double.isInfinite(p))
                return Minimum.INSTANCE;
            else
                return new AczelAlsinaIntersection(p);
        }
    };

    public static final ParametricFactory FACTORY = T_NORM;

    private double p;

    public AczelAlsinaIntersection(double p) {
        if (p < 0.0)
            throw new IllegalArgumentException("expected p >= 0");
        this.p = p;
    }

    @Override
    public double apply(double... values) {

        ParametersUtil.assertTwoParameters(this.getClass(), "apply", values);

        double a = values[0];
        double b = values[1];

        double lap = Math.pow(-Math.log(a), p);
        double lbp = Math.pow(-Math.log(b), p);
        // double lap = Math.pow(Math.abs(Math.log(a)), p);
        // double lbp = Math.pow(Math.abs(Math.log(b)), p);

        System.out.println("lap " + lap + " lbp " + lbp);

        return Math.exp(-Math.pow(lap + lbp, 1.0/p));
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
