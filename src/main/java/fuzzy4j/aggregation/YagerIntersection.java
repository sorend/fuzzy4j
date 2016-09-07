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

/**
 * Yager class aggregation operator.
 *
 * Defined as:
 *      <code>T_Y(p, a_1, a_2, ... a_n) = 1 - min(1, (Sum^n_k=1 (1 - a_k)^p)^(1/p))</code>
 *
 * To get the T-CoNorm operator use {@link fuzzy4j.aggregation.YagerIntersection#duality()}.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class YagerIntersection implements Norm {

    public static final ParametricFactory<Norm> FACTORY = new ParametricFactory<Norm>() {
        @Override
        public Norm create(double... params) {
            double p = params[0];
            if (p < 0.0)
                throw new IllegalArgumentException("p < 0");
            else if (p == 0.0)
                return DrasticIntersection.INSTANCE;
            else if (Double.isInfinite(p))
                return Minimum.INSTANCE;
            else
                return new YagerIntersection(p);
        }
    };

    private double p;

    private YagerIntersection(double p) {
        this.p = p;
    }

    @Override
    public double apply(double... values) {
        double s = 0.0;
        for (int i = 0; i < values.length; i++) {
            s += Math.pow(1.0 - values[i], p);
        }
        return 1.0 - Math.min(1, Math.pow(s, 1.0 / p));
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
