/*
 * Copyright (c) 2013, Søren Atmakuri Davidsen
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

/**
 * Build rules in an easy manner.
 *
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class RuleBuilder {

    private enum PendingType {
        AND,
        OR;
    }

    private ControllerBuilder controllerBuilder;

    public RuleBuilder(ControllerBuilder controllerBuilder) {
        this.controllerBuilder = controllerBuilder;
    }

    public TermSelector var(Variable var) {
        return new TermSelector(null, var);
    }

    public class AfterRuleBuilder {

        private AfterRuleBuilder previous;
        private Expression expression;
        private PendingType type = null;

        public AfterRuleBuilder(AfterRuleBuilder previous, Expression expression) {
            this.previous = previous;
            this.expression = expression;
        }

        public VarSelector or() {
            this.type = PendingType.OR;
            return new VarSelector(this);
        }

        public VarSelector and() {
            this.type = PendingType.AND;
            return new VarSelector(this);
        }

        public ThenVarSelector then() {

            AfterRuleBuilder prev = previous;
            Expression current = expression;

            while (prev != null) {
                current = (prev.type == PendingType.AND) ?
                        ExpressionFactory.and(current, prev.expression) :
                        ExpressionFactory.or(current, prev.expression);

                prev = prev.previous;
            }

            return new ThenVarSelector(current);
        }
    }

    public class VarSelector {

        private AfterRuleBuilder previous;

        public VarSelector(AfterRuleBuilder previous) {
            this.previous = previous;
        }

        public TermSelector var(Variable var) {
            return new TermSelector(previous, var);
        }
    }

    public class TermSelector {

        private Variable var;
        private AfterRuleBuilder previous;

        public TermSelector(AfterRuleBuilder previous, Variable var) {
            this.previous = previous;
            this.var = var;
        }

        public AfterRuleBuilder isNot(Term term) {
            return new AfterRuleBuilder(previous, ExpressionFactory.is(var, Hedge.NOT, term));
        }

        public AfterRuleBuilder is(Term term) {
            return new AfterRuleBuilder(previous, ExpressionFactory.is(var, term));
        }

        public AfterRuleBuilder is(HedgeBuilder hedgeBuilder) {
            return new AfterRuleBuilder(previous, ExpressionFactory.is(var, hedgeBuilder.hedge, hedgeBuilder.term));
        }

        public AfterRuleBuilder is(Hedge hedge, Term term) {
            return new AfterRuleBuilder(previous, ExpressionFactory.is(var, hedge, term));
        }
    }

    public class ThenVarSelector {

        private Expression expression;

        public ThenVarSelector(Expression expression) {
            this.expression = expression;
        }

        public FinalTermSelector var(Variable var) {
            return new FinalTermSelector(expression, var);
        }
    }

    public class FinalTermSelector {

        private Expression expression;
        private Variable var;

        public FinalTermSelector(Expression expression, Variable var) {
            this.expression = expression;
            this.var = var;
        }

        public ControllerBuilder is(Term term) {
            // build the rule
            controllerBuilder.addRule(new IfThenRule(expression, ExpressionFactory.is(var, term)));
            return controllerBuilder;
        }
    }

}
