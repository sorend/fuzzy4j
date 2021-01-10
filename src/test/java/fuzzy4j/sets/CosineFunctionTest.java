package fuzzy4j.sets;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class CosineFunctionTest {

    CosineFunction impl;

    @Test
    public void membership_outside() {
        impl = new CosineFunction(1.0, 1.0);

        assertEquals(0.0, impl.apply(10));
        assertEquals(0.0, impl.apply(-10));
    }

    @Test
    public void testMembership_01() throws Exception {

        impl = new CosineFunction(1.0, 1.0);

        assertEquals(0.0, impl.apply(0.0));
        assertEquals(1.0, impl.apply(1.0));
        assertEquals(0.0, impl.apply(2.0));
    }

    @Test
    public void testMembership_02() throws Exception {

        impl = new CosineFunction(2.0, 5.0);

        assertEquals(0.0, impl.apply(3.0), 0.001);
        assertEquals(0.0, impl.apply(7.0), 0.001);
        assertEquals(1.0, impl.apply(5.0), 0.001);
        assertTrue(impl.apply(4.5) < impl.apply(4.6));
        assertTrue(impl.apply(5.6) > impl.apply(5.7));

    }

}
