package fuzzy4j.sets;

import fuzzy4j.aggregation.AlgebraicProduct;
import fuzzy4j.aggregation.Minimum;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class CompositeFunctionTest {

    CompositeFunction impl;

    @Test
    public void testMembership_01() throws Exception {

        ConstantFunction f1 = new ConstantFunction(0.5);
        TriangularFunction f2 = new TriangularFunction(0.0, 1.0, 2.0);

        impl = new CompositeFunction(AlgebraicProduct.INSTANCE, f1, f2);

        assertEquals(0.5, impl.apply(1.0));
        assertEquals(0.25, impl.apply(0.5));

        assertEquals(0.0, impl.apply(-10));
    }

    @Test
    public void testMembership_02() throws Exception {

        ConstantFunction f1 = new ConstantFunction(0.5);
        TriangularFunction f2 = new TriangularFunction(0.0, 1.0, 2.0);

        impl = new CompositeFunction(Minimum.INSTANCE, f1, f2);

        assertEquals(0.5, impl.apply(1.0));
        assertEquals(0.0, impl.apply(0.0));

        assertEquals(0.0, impl.apply(-10));
    }

}
