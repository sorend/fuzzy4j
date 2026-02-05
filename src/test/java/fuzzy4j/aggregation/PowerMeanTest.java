package fuzzy4j.aggregation;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class PowerMeanTest {

    PowerMean impl;

    @Test
    void testParametricFactory() {

        impl = PowerMean.FACTORY.create(1.0);

        assertEquals(1.0, impl.p);

        try {
            PowerMean.FACTORY.create();
            fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            PowerMean.FACTORY.create(1.0, 2.0, 3.0);
            fail();
        }
        catch (IllegalArgumentException e) {}
    }

    public double[] random(int n) {
        Random rand = new Random();
        double[] res = new double[n];
        for (int i = 0; i < n; i++)
            res[i] = rand.nextDouble();
        return res;
    }

    @Test
    void testApply_basic() {

        impl = PowerMean.FACTORY.create(0.5);

        assertEquals(Double.NaN, impl.compute());
        assertEquals(Double.NaN, impl.compute(null));

        assertEquals(0.0, impl.compute(0));
        assertEquals(1.0, impl.compute(1));
        assertEquals(0.22, impl.compute(0.22), 0.00001);
    }


    @Test
    void testApply() {

        impl = new PowerMean(1.0);

        assertEquals(1.0, impl.compute(1.0));

        // for p = 1, we have the arithmetic mean.
        ArithmeticMean mean = ArithmeticMean.INSTANCE;
        double[] rand = random(10);
        assertEquals(mean.compute(rand), impl.compute(rand));

        impl = new PowerMean(1.1);
        assertTrue(mean.compute(rand) < impl.compute(rand));

        impl = new PowerMean(0.9);
        assertTrue(mean.compute(rand) > impl.compute(rand));
    }

    @Test
    void testToString() {

    }
}
