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

import fuzzy4j.aggregation.Aggregation;

import java.util.Arrays;

/**
 * Membership function which aggregates other fuzzy sets by an aggregation operator.
 *
 * For example, create product of two fuzzy sets:
 * <pre>
 * {@code
 * // create two triangular functions
 * TriangularFunction f1 = new TriangularFunction(1.0, 2.0, 3.0);
 * TriangularFunction f2 = new TriangularFunction(0.5, 1.0, 1.5);
 * // create a composite, which is the product of the two.
 * CompositeFunction prod = new CompositeFunction(AlgebraicProduct.INSTANCE, f1, f2);
 * }
 * </pre>
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class CompositeFunction implements FuzzyFunction {

    public final FuzzyFunction[] inners;
    public final Aggregation aggregation;

    public CompositeFunction(Aggregation aggregation, FuzzyFunction... inners) {
        this.aggregation = aggregation;
        this.inners = inners;
    }

    @Override
    public double apply(double x) {
        // find values
        double[] vals = new double[inners.length];
        for (int i = 0; i < vals.length; i++)
            vals[i] = inners[i].apply(x);
        // aggregate
        return aggregation.apply(vals);
    }

    @Override
    public String toString() {
        return aggregation + "(" + Arrays.asList(inners) + ")";
    }
}
