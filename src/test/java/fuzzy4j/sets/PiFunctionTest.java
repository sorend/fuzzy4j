package fuzzy4j.sets;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by sorend on 22/3/14.
 */
public class PiFunctionTest {

    PiFunction impl;

    public void print() {
        PiFunction f = impl;
        System.out.println("a " + f.a + ", p " + f.p + ", r " + f.r + ", q " + f.q + ", b " + f.b);
    }

    @Test
    public void testApply_01() throws Exception {

        impl = new PiFunction(1, 2, 4, 2.0);

        assertEquals(1.0, impl.a);
        assertEquals(1.5, impl.p);
        assertEquals(2.0, impl.r);
        assertEquals(3.0, impl.q);
        assertEquals(4.0, impl.b);

        assertEquals(0.0, impl.apply(0.5));
        assertEquals(0.0, impl.apply(1.0));
        assertTrue(impl.apply(1.1) > 0.0);
        assertEquals(0.5, impl.apply(1.5));
        assertEquals(1.0, impl.apply(2.0));
        assertEquals(0.5, impl.apply(3.0));
        assertTrue(impl.apply(3.9) > 0.0);
        assertTrue(impl.apply(3.9) < 0.5);
        assertEquals(0.0, impl.apply(4.0));
        assertEquals(0.0, impl.apply(4.1));

    }

    @Test
    public void testCenter() throws Exception {
        impl = new PiFunction(1, 2, 3, 2.0);

        assertEquals(2.0, impl.center());
    }
}
