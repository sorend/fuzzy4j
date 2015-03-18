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
 * Wraps a t-norm operator using the DeMorgan duality.
 *
 * 1) a plus b = not(not(a) times not(b))
 * 2) a times b = not(not(a) plus not(b))
 *
 * not(x) = 1 - x, using standard Zadeh negation.
 *
* @author Soren A. Davidsen <soren@tanesha.net>
*/
class DeMorganDuality implements Norm, Aggregation {

    private Norm op;

    public DeMorganDuality(Norm op) {
        this.op = op;
    }

    @Override
    public double apply(double... values) {
        double[] inv = new double[values.length];
        for (int i = 0; i < values.length; i++)
            inv[i] = 1 - values[i];
        return 1 - op.apply(inv);
    }

    @Override
    public Type type() {
        if (op.type() == Type.UNKNOWN)
            return Type.UNKNOWN;
        else
            return op.type() == Norm.Type.T_NORM ? Norm.Type.T_CONORM : Norm.Type.T_NORM;
    }

    @Override
    public Norm duality() {
        // the duality of the duality is itself :)
        return op;
    }
}
