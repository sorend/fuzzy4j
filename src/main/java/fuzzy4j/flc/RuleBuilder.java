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
