package fuzzy4j.flc;

/**
 * Representation of the fuzzy if then rule.
 *
 * @author Soren <sorend@gmail.com>
 */
public class IfThenRule {

    public final Expression rule;
    public final ExpressionFactory.FuzzyExpression then;

    public IfThenRule(Expression rule, ExpressionFactory.FuzzyExpression then) {
        this.rule = rule;
        this.then = then;
    }

    public String toString() {
        return "If " + rule + " Then " + then;
    }
}

