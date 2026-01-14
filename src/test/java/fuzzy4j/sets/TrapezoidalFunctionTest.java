package fuzzy4j.sets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Soren <sorend@gmail.com>
 */
public class TrapezoidalFunctionTest {

    TrapezoidalFunction impl;

    @Test
    void testMembership() {

        impl = new TrapezoidalFunction(10, 20, 30, 40);

        assertEquals(0.0, impl.apply(0));
        assertEquals(0.0, impl.apply(10));
        assertEquals(1.0, impl.apply(20));
        assertEquals(1.0, impl.apply(30));
        assertEquals(0.0, impl.apply(40));
        assertEquals(0.0, impl.apply(50));

        assertEquals(0.5, impl.apply(15));
        assertEquals(1.0, impl.apply(25));
        assertEquals(0.5, impl.apply(35));

    }


    @Test
    public void testStartSameAsFirstTop() {

        impl = new TrapezoidalFunction(0, 0, 1, 3);

        assertEquals(1.0, impl.apply(0));
        assertEquals(0.0, impl.apply(-0.00000001));

        impl = new TrapezoidalFunction(0, 1, 2, 2);

        assertEquals(1.0, impl.apply(2));
        assertEquals(0.0, impl.apply(2.0000000001));
    }
}
