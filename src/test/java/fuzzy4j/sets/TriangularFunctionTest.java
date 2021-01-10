package fuzzy4j.sets;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class TriangularFunctionTest {

    @Test
    public void center() {

        TriangularFunction f = new TriangularFunction(0, 1, 2);

        assertEquals(1.0, f.center());

        TriangularFunction f2 = new TriangularFunction(0, 10, 11);
        assertEquals(10.0, f2.center());
    }

    @Test
    public void simple() {

        TriangularFunction f = new TriangularFunction(0.0, 1.0, 2.0);

        assertEquals(0.0, f.apply(-100));
        assertEquals(0.0, f.apply(0.0));
        assertEquals(0.5, f.apply(0.5));

        assertEquals(1.0, f.apply(1.0));
        assertEquals(0.5, f.apply(1.5));

        assertEquals(0.0, f.apply(2.0));

        assertEquals(0.0, f.apply(100.0));
    }

    @Test
    public void leftOpen() {

        TriangularFunction f = new TriangularFunction(Double.NEGATIVE_INFINITY, 1.0, 2.0);

        assertEquals(1.0, f.apply(-100));
        assertEquals(1.0, f.apply(0.0));
        assertEquals(1.0, f.apply(0.5));

        assertEquals(1.0, f.apply(1.0));
        assertEquals(0.5, f.apply(1.5));

        assertEquals(0.0, f.apply(2.0));

        assertEquals(0.0, f.apply(100.0));
    }

    @Test
    public void rightOpen() {

        TriangularFunction f = new TriangularFunction(0.0, 1.0, Double.POSITIVE_INFINITY);

        assertEquals(0.0, f.apply(-100));
        assertEquals(0.0, f.apply(0.0));
        assertEquals(0.5, f.apply(0.5));

        assertEquals(1.0, f.apply(1.0));
        assertEquals(1.0, f.apply(1.5));

        assertEquals(1.0, f.apply(2.0));

        assertEquals(1.0, f.apply(100.0));
    }

    @Test
    public void testStartMiddleSame() {

        TriangularFunction f = new TriangularFunction(0, 0, 1);

        assertEquals(1.0, f.apply(0));
        assertEquals(0.0, f.apply(-0.000000001));

        f = new TriangularFunction(0, 1, 1);

        assertEquals(1.0, f.apply(1));
        assertEquals(0.0, f.apply(1.00000001));
    }

}
