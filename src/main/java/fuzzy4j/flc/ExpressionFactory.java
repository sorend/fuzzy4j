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

import fuzzy4j.aggregation.Aggregation;

import java.util.Arrays;

/**
 * @author Soren <sorend@gmail.com>
 */
public class ExpressionFactory {

    public static Expression and(Expression... expressions) {
        return new AndExpression(expressions);
    }

    public static Expression or(Expression... expressions) {
        return new OrExpression(expressions);
    }

    public static Expression other(Aggregation aggregation, Expression... expressions) {
        return new OtherExpression(aggregation, expressions);
    }

    public static FuzzyExpression is(Variable variable, Term value) {
        return new FuzzyExpression(variable, value);
    }

    public static Expression is(Variable variable, Hedge hedge, Term value) {
        return new HedgedExpression(is(variable, value), hedge);
    }

    private static class HedgedExpression implements Expression {

        public final FuzzyExpression inner;
        public final Hedge hedge;

        private HedgedExpression(FuzzyExpression inner, Hedge hedge) {
            this.inner = inner;
            this.hedge = hedge;
        }

        @Override
        public double apply(InputInstanceAccessor accessor) {
            return hedge.apply(inner.apply(accessor));
        }

        @Override
        public String toString() {
            return hedge + "(" + inner + ")";
        }
    }

    public static class FuzzyExpression implements Expression{
        public final Variable variable;
        public final Term value;

        public FuzzyExpression(Variable variable, Term value) {
            this.variable = variable;
            this.value = value;
        }

        @Override
        public double apply(InputInstanceAccessor accessor) {
            return accessor.valueOf(variable, value);
        }

        public String toString() {
            return variable + " is " + value;
        }
    }

    private static class OtherExpression implements Expression {
        private Aggregation aggregation;
        private Expression[] inners;

        private OtherExpression(Aggregation aggregation, Expression[] inners) {
            this.aggregation = aggregation;
            this.inners = inners;
        }

        @Override
        public double apply(InputInstanceAccessor accessor) {
            double[] res = new double[inners.length];
            for (int i = 0; i < inners.length; i++)
                res[i] = inners[i].apply(accessor);
            return aggregation.apply(res);
        }
    }

    private static class AndExpression implements Expression {
        private Expression[] inners;

        private AndExpression(Expression[] inners) {
            this.inners = inners;
        }

        @Override
        public double apply(InputInstanceAccessor accessor) {
            double[] res = new double[inners.length];
            for (int i = 0; i < inners.length; i++)
                res[i] = inners[i].apply(accessor);
            return accessor.andFunction().apply(res);
        }

        public String toString() {
            return "and(" + Arrays.asList(inners) + ")";
        }
    }

    private static class OrExpression implements Expression {
        private Expression[] inners;

        private OrExpression(Expression[] inners) {
            this.inners = inners;
        }

        @Override
        public double apply(InputInstanceAccessor accessor) {
            double[] res = new double[inners.length];
            for (int i = 0; i < inners.length; i++)
                res[i] = inners[i].apply(accessor);
            return accessor.orFunction().apply(res);
        }

        public String toString() {
            return "or(" + Arrays.asList(inners) + ")";
        }
    }

}
