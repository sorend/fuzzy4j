package fuzzy4j.sets;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class ExponentialDecayFunctionTest {

    ExponentialDecayFunction impl;

    @Test
    public void testMembership() throws Exception {

        impl = new ExponentialDecayFunction(0., 1.);

        // sould be 1 where x = x1 (0)
        assertEquals(1., impl.apply(0.), 0.001);
        // should be <1 little further out.
        assertTrue(impl.apply(0.00001) < 1.0);

        // for this function, around 9 we should ahve f(x) = ~0 (to 3 decimals)
        assertEquals(0.0, impl.apply(9.0), 0.001);

        // should be bounded at 1
        assertEquals(1., impl.apply(-1.), 0.001);
    }
}
