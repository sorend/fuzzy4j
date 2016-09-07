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
 * Implementation of cosine apply function. Note that this is not a cycling
 * cosine function, but only supports a single "top": _.-~-._
 *
 * Alpha defines the support of the function, Beta defines the top.
 *
 * <pre>
 * {@code .
 *       .-^-.
 *    .-'     `-.
 *   /           \
 * -'-------------`-----> x
 *        /|\<---->
 *         |    alpha
 *        beta
 * }
 * </pre>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class CosineFunction implements FuzzyFunction, CenterAware {

    private double alpha;
    private double beta;

    /**
     * Constructor
     *
     * @param alpha Half the support
     * @param beta apply(beta) = 1.0
     */
    public CosineFunction(double alpha, double beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    @Override
    public double apply(double x) {
        double d = x - beta;
        double t = alpha;
        double tPi = Math.PI / alpha;
        if ((-t <= d) && (d <= t))
            return (Math.cos(d * tPi) + 1.0) / 2.0;
        else
            return 0.0;
    }

    @Override
    public String toString() {
        return "cos";
    }

    @Override
    public double center() {
        return beta;
    }
}
