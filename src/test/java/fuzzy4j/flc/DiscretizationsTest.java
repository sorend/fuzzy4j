package fuzzy4j.flc;

import fuzzy4j.sets.TriangularFunction;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Soren <sorend@gmail.com>
 */
public class DiscretizationsTest {

    @Test
    void testNewUniformTriangular() {

        Term[] terms = Discretizations.uniformTriangular(0.0, 10.0, "low", "medium", "high");

        assertEquals(3, terms.length);

        assertEquals("low", terms[0].name());
        assertEquals("medium", terms[1].name());
        assertEquals("high", terms[2].name());

        assertEquals(1.0, terms[0].set().apply(0.0));
        assertEquals(0.0, terms[0].set().apply(5.0));

        assertEquals(1.0, terms[1].set().apply(5.0));
        assertEquals(0.0, terms[0].set().apply(10.0));

        assertEquals(1.0, terms[2].set().apply(10.0));
        assertEquals(0.0, terms[0].set().apply(5.0));
    }
}
