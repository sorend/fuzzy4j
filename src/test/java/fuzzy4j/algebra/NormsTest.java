package fuzzy4j.algebra;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NormsTest {

    TNorm impl;

    @Test
    void testDombiCalc() {
        impl = Norms.dombi(0.9);

        double r = impl.t(1.0, 1.0);
        assertEquals(1.0, r, 0.001);

        r = impl.t(1.0, 0.0);
        assertEquals(0.0, r, 0.001);

        r = impl.t(1.0, 0.1);
        assertNotNull(r);
    }

    @Test
    void testDombiByDrasticality() {
        impl = Norms.dombiByDrasticality(0.5);
        assertNotNull(impl);
    }

    @Test
    void testDuboisPrade() {
        impl = Norms.duboisPrade(0);

        assertEquals(0.5, impl.t(0.5, 0.8));

        impl = Norms.duboisPrade(1.0);

        assertEquals(0.4, impl.t(0.5, 0.8));
    }

    @Test
    void testDuboisPradeValidation() {
        try {
            Norms.duboisPrade(-0.00001);
        } catch (IllegalArgumentException e) {
        }

        try {
            Norms.duboisPrade(1.000001);
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    void testAczelAlsina() {
        impl = Norms.aczelAlsina(1.0);
        assertNotNull(impl);
    }

    @Test
    void testDrastic() {
        impl = Norms.drastic();
        assertEquals(0.0, impl.t(0.5, 0.5));
        assertEquals(0.5, impl.t(1.0, 0.5));
        assertEquals(0.5, impl.t(0.5, 1.0));
    }

    @Test
    void testEinstein() {
        impl = Norms.einstein();
        assertEquals(0.0, impl.t(0.0, 0.5));
        assertNotNull(impl.t(0.5, 0.5));
    }

    @Test
    void testFrank() {
        impl = Norms.frank(2.0);
        assertNotNull(impl);
    }

    @Test
    void testFrankSpecialCases() {
        assertEquals(Norms.minimum(), Norms.frank(0.0));
        assertEquals(Norms.product(), Norms.frank(1.0));
        assertEquals(Norms.lukasiewicz(), Norms.frank(Double.POSITIVE_INFINITY));
    }

    @Test
    void testNilpotentMinimum() {
        impl = Norms.nilpotentMinimum();
        assertEquals(0.0, impl.t(0.5, 0.5));
        assertEquals(0.5, impl.t(0.5, 0.8));
    }

    @Test
    void testYager() {
        impl = Norms.yager(2.0);
        assertNotNull(impl);
    }

    @Test
    void testYagerSpecialCases() {
        assertEquals(Norms.drastic(), Norms.yager(0.0));
        assertEquals(Norms.minimum(), Norms.yager(Double.POSITIVE_INFINITY));
    }

    @Test
    void testSugenoWeber() {
        impl = Norms.sugenoWeber(2.0);
        assertNotNull(impl);
    }

    @Test
    void testSugenoWeberSpecialCases() {
        assertEquals(Norms.drastic(), Norms.sugenoWeber(1.0));
        assertEquals(Norms.product(), Norms.sugenoWeber(Double.POSITIVE_INFINITY));
    }

    @Test
    void testLukasiewicz() {
        impl = Norms.lukasiewicz();
        assertEquals(0.0, impl.t(0.5, 0.5), 0.001);
        assertEquals(0.3, impl.t(0.5, 0.8), 0.001);
    }

    @Test
    void testSchweizerSklar() {
        impl = Norms.schweizerSklar(2.0);
        assertNotNull(impl);
    }

    @Test
    void testSchweizerSklarSpecialCases() {
        assertEquals(Norms.minimum(), Norms.schweizerSklar(Double.NEGATIVE_INFINITY));
        assertEquals(Norms.drastic(), Norms.schweizerSklar(Double.POSITIVE_INFINITY));
        assertEquals(Norms.product(), Norms.schweizerSklar(0.0));
    }

    @Test
    void testHamacher() {
        impl = Norms.hamacher(2.0);
        assertNotNull(impl);
    }

    @Test
    void testHamacherProduct() {
        impl = Norms.hamacherProduct();
        assertNotNull(impl);
    }

    @Test
    void testHamacherSpecialCases() {
        assertEquals(Norms.drastic(), Norms.hamacher(Double.POSITIVE_INFINITY));
    }

    @Test
    void testMinimum() {
        impl = Norms.minimum();
        assertEquals(0.5, impl.t(0.5, 0.8));
        assertEquals(0.5, impl.t(0.5, 0.5));
    }

    @Test
    void testProduct() {
        impl = Norms.product();
        assertEquals(0.4, impl.t(0.5, 0.8));
        assertEquals(0.25, impl.t(0.5, 0.5));
    }

    @Test
    void testToString() {
        assertNotNull(Norms.dombi(1.0).toString());
        assertNotNull(Norms.duboisPrade(0.5).toString());
        assertNotNull(Norms.aczelAlsina(1.0).toString());
        assertNotNull(Norms.drastic().toString());
        assertNotNull(Norms.einstein().toString());
        assertNotNull(Norms.frank(1.0).toString());
        assertNotNull(Norms.nilpotentMinimum().toString());
        assertNotNull(Norms.yager(1.0).toString());
        assertNotNull(Norms.sugenoWeber(1.0).toString());
        assertNotNull(Norms.lukasiewicz().toString());
        assertNotNull(Norms.schweizerSklar(1.0).toString());
        assertNotNull(Norms.hamacher(1.0).toString());
        assertNotNull(Norms.minimum().toString());
        assertNotNull(Norms.product().toString());
    }
}
