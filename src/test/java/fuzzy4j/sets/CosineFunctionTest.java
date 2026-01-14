package fuzzy4j.sets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class CosineFunctionTest {

    CosineFunction impl;

    @Test
    void membership_outside() {
        impl = new CosineFunction(1.0, 1.0);

        assertEquals(0.0, impl.apply(10));
        assertEquals(0.0, impl.apply(-10));
    }

    @Test
    void testMembership_01() {

        impl = new CosineFunction(1.0, 1.0);

        assertEquals(0.0, impl.apply(0.0));
        assertEquals(1.0, impl.apply(1.0));
        assertEquals(0.0, impl.apply(2.0));
    }

    @Test
    void testMembership_02() {

        impl = new CosineFunction(2.0, 5.0);

        assertEquals(0.0, impl.apply(3.0), 0.001);
        assertEquals(0.0, impl.apply(7.0), 0.001);
        assertEquals(1.0, impl.apply(5.0), 0.001);
        assertTrue(impl.apply(4.5) < impl.apply(4.6));
        assertTrue(impl.apply(5.6) > impl.apply(5.7));

    }

}
