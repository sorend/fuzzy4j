package fuzzy4j.aggregation;

import org.junit.After;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class OWATest {

    OWA impl;

    @After
    public void printAfter() {
        System.out.println("OWA = " + impl);
    }

    @Test
    public void testOrness_and_Dispersion() throws Exception {

        impl = new OWA(0, 0, 1, 0, 0);
        assertEquals(0.5, impl.orness(), 0.00001);
        assertEquals(0.0, impl.dispersion(), 0.00001);

        impl = new OWA(0.2, 0.2, 0.2, 0.2, 0.2);
        assertEquals(0.5, impl.orness(), 0.00001);
        assertEquals(1.0, impl.dispersion(), 0.00001);
        assertEquals(0.5, impl.andness(), 0.00001);
    }

    @Test
    public void testMeowaFactory_Paper_Example() throws Exception {

        impl = OWA.MEOWA_FACTORY(3, 0.80);

        assertNotNull(impl);

        assertEquals(3, impl.weights.length);
        assertEquals(0.08187, impl.weights[0], 0.00001);
        assertEquals(0.23627, impl.weights[1], 0.00001);
        assertEquals(0.68187, impl.weights[2], 0.00001);
    }


    @Test
    public void testMeowa_Parametric_Factory() throws Exception {

        impl = OWA.MEOWA_FACTORY(3, 0.80);

        Aggregation meowa = OWA.MEOWA.create(0.80);

        assertNotNull(meowa);
        assertNotNull(impl);

        double val = meowa.apply(0.3, 0.6, 0.9);
        double val2 = impl.apply(0.3, 0.6, 0.9);

        assertEquals(val, val2);
    }

    @Test
    public void testMeowaFactory_Andness() throws Exception {

        impl = OWA.MEOWA_FACTORY(3, 0.80);
        OWA impl2 = OWA.MEOWA_FACTORY(3, 0.5);
        OWA impl3 = OWA.MEOWA_FACTORY(3, 0.0);

        assertNotNull(impl);
        assertNotNull(impl2);
        assertNotNull(impl3);

        assertTrue(impl.weights[0] < impl2.weights[0]);
        assertTrue(impl2.weights[0] < impl3.weights[0]);

        System.out.println("OWA_2 = " + impl2);
        System.out.println("OWA_3 = " + impl3);
    }

    @Test
    public void testOWAOperator_Max() throws Exception {
        impl = new OWA(1.0, 0, 0, 0);
        assertEquals(0.8, impl.apply(0.5, 0.2, 0.8, 0.1));
    }

    @Test
    public void testOWAOperator_Min() throws Exception {
        impl = new OWA(0, 0, 0, 1.0);
        assertEquals(0.1, impl.apply(0.5, 0.2, 0.8, 0.1));
    }

    @Test
    public void testMeowa_Max() throws Exception {

        impl = OWA.MEOWA_FACTORY(3, 0.0);

        assertEquals(impl.weights[0], 1.0, 0.001);

    }

    @Test
    public void testMeowa_Min() throws Exception {

        impl = OWA.MEOWA_FACTORY(3, 1.0);

        assertEquals(impl.weights[2], 1.0, 0.001);

    }



}
