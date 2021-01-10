package fuzzy4j.flc;

import fuzzy4j.sets.PiFunction;
import fuzzy4j.sets.TrapezoidalFunction;
import fuzzy4j.sets.TriangularFunction;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class TermTest {

    Term impl;

    @Test
    public void testTerm_triangular() throws Exception {
        impl = Term.term("low", 0, 1, 2);
        assertTrue(impl.set instanceof TriangularFunction);
        assertEquals(1.0, impl.set.apply(1));
        assertEquals(0.5, impl.set.apply(0.5));
        assertEquals("low", impl.name);
    }

    @Test
    public void testTerm_trapeze() throws Exception {
        impl = Term.term("medium", 0, 1, 2, 3);
        assertTrue(impl.set instanceof TrapezoidalFunction);
    }

    @Test
    public void testTerm() throws Exception {
        impl = Term.term("any", new PiFunction(0, 1, 3, 1));
        assertTrue(impl.set instanceof PiFunction);

        try {
            impl = Term.term(null, new PiFunction(0, 1, 3, 1));
            fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            impl = Term.term("none", null);
            fail();
        }
        catch (IllegalArgumentException e) {}
    }

    @Test
    public void testEquals() throws Exception {

        impl = Term.term("any", 0, 1, 2);
        Term term2 = Term.term("any", 5, 10, 20);

        assertEquals(impl, term2);
        assertEquals(impl.hashCode(), term2.hashCode());

        Term term3 = Term.term("none", 0, 1, 2);
        assertFalse(impl.equals(term3));
        assertTrue(impl.hashCode() != term3.hashCode());

        assertFalse(impl.equals(null));
        assertFalse(impl.equals(new Object()));
        assertTrue(impl.hashCode() != 0);
    }

    @Test
    public void testToString() throws Exception {
        impl = Term.term("any", 0, 1, 2);
        assertNotNull(impl.toString());
    }
}
