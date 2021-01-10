package fuzzy4j.sets;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class UnitIntervalWrapperFunctionTest {

    UnitIntervalWrapperFunction impl;

    @Test
    public void testMembership_max() throws Exception {

        FuzzyFunction ff = new FuzzyFunction() {
            @Override
            public double apply(double x) {
                return x * 0.1;
            }
        };

        impl = new UnitIntervalWrapperFunction(ff);

        assertEquals(0.0, impl.apply(0.0));
        assertEquals(0.01, impl.apply(0.1), 0.001);
        assertEquals(0.5, impl.apply(5));
        assertEquals(1.0, impl.apply(10));
        assertEquals(1.0, impl.apply(100));

    }

    @Test
    public void testMembership_min() throws Exception {

        FuzzyFunction ff = new FuzzyFunction() {
            @Override
            public double apply(double x) {
                return x - 10;
            }
        };

        impl = new UnitIntervalWrapperFunction(ff);

        assertEquals(0.0, impl.apply(10));
        assertEquals(1.0, impl.apply(11));
        assertEquals(1.0, impl.apply(20));
        assertEquals(0.0, impl.apply(0));
    }

}
