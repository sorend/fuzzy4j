package fuzzy4j.flc;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class IfThenRuleTest {

    IfThenRule impl;

    Term low = Term.term("low", 0, 1, 2);
    Variable input = Variable.input("ok", low);

    Expression expression = ExpressionFactory.is(input, low);

    @Test
    public void testToString() throws Exception {

        // very simple class, so very simple test ..

        impl = new IfThenRule(expression, (ExpressionFactory.FuzzyExpression) expression);

        assertNotNull(impl.toString());

        assertEquals(expression, impl.rule);
        assertEquals(expression, impl.then);
    }
}
