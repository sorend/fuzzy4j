package fuzzy4j.negation;

import fuzzy4j.sets.FuzzyFunction;
import fuzzy4j.sets.TriangularFunction;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class SugenoNegationTest {

    SugenoNegation impl;

    FuzzyFunction ff = new TriangularFunction(0, 1, 2);

    @Test
    public void testSimple() {

        // (1 - x) / (1 + 1 * x)
        impl = new SugenoNegation(1.0, ff);

        // x = 0, (1 - 0) / (1 + 1 * 0) = 1 / 1 = 1
        assertEquals(1.0, impl.apply(0));

        // x = 1, (1 - 1) / ... = 0 / ... = 0
        assertEquals(0.0, impl.apply(1));
        assertEquals(1.0, impl.apply(2));

        // x = 0.5, (1 - 0.5) / (1 + 1 * 0.5) = 0.5 / 1.5 = 0.333..
        assertEquals(0.333, impl.apply(0.5), 0.001);

        // x = 0.1, (1 - 0.1) / (1 + 1 * 0.1) = 0.9 / 1.1 = 0.8181..
        assertEquals(0.818, impl.applyNegation(0.1), 0.001);

        // x = 0.9, (1 - 0.9) / (1 + 1 * 0.9) = 0.1 / 1.9 = 0.052..
        assertEquals(0.052, impl.applyNegation(0.9), 0.001);
    }

    @Test
    public void testToString() {
        impl = new SugenoNegation(1.0, ff);
        assertNotNull(impl.toString());
    }

}
