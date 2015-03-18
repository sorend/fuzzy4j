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

/**
 * Implementation of a Log (natural logarithm) function which operates within [0, 1] for two defined points.
 *
 * Defined as:
 *  {@code M_E(x, a) = e^(-x a)}
 *
 * The parameter a controls the rate of decay.
 *
 * @author Soren A. Davidsen <soren@tanesha.net>
 */
public class ExponentialDecayFunction implements FuzzyFunction {

    private double x1;
    private double alpha;

    /**
     *
     * @param x1 the location where f(x) = 1, x1 = 0 => f(0) = 1
     * @param alpha the decay parameter. larger = faster decay. alpha=1 means f(~9.0) = 0
     */
    public ExponentialDecayFunction(double x1, double alpha) {
        this.x1 = x1;
        this.alpha = alpha;
    }

    @Override
    public double apply(double x) {
        return Math.min(1.0, Math.exp(-(x - x1) * alpha));
    }
}
