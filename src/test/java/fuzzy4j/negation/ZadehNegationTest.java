package fuzzy4j.negation;

import fuzzy4j.sets.FuzzyFunction;
import fuzzy4j.sets.TriangularFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class ZadehNegationTest {

    ZadehNegation impl;

    FuzzyFunction ff = new TriangularFunction(0, 1, 2);

    @Test
    void testNegation_01() {

        impl = new ZadehNegation(ff);

        assertEquals(1.0, impl.applyNegation(0));
        assertEquals(0.0, impl.applyNegation(1));
        assertEquals(0.5, impl.applyNegation(.5));

        assertEquals(0.0, impl.apply(1));
        assertEquals(1.0, impl.apply(0));
        assertEquals(1.0, impl.apply(2));
    }

    @Test
    void testToString() {
        impl = new ZadehNegation(ff);
        assertNotNull(impl.toString());
    }
}
