package fuzzy4j.flc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class IfThenRuleTest {

    IfThenRule impl;

    Term low = Term.term("low", 0, 1, 2);
    Variable input = Variable.input("ok", low);

    Expression expression = ExpressionFactory.is(input, low);

    @Test
    void testToString() {

        // very simple class, so very simple test ..

        impl = new IfThenRule(expression, (ExpressionFactory.FuzzyExpression) expression);

        assertNotNull(impl.toString());

        assertEquals(expression, impl.rule);
        assertEquals(expression, impl.then);
    }
}
