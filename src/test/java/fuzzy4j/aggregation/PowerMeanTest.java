package fuzzy4j.aggregation;

import org.junit.Test;

import java.util.Random;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class PowerMeanTest {

    PowerMean impl;

    @Test
    public void testParametricFactory() throws Exception {

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
    public void testApply_basic() throws Exception {

        impl = PowerMean.FACTORY.create(0.5);

        assertEquals(Double.NaN, impl.apply());
        assertEquals(Double.NaN, impl.apply(null));

        assertEquals(0.0, impl.apply(0));
        assertEquals(1.0, impl.apply(1));
        assertEquals(0.22, impl.apply(0.22), 0.00001);
    }


    @Test
    public void testApply() throws Exception {

        impl = new PowerMean(1.0);

        assertEquals(1.0, impl.apply(1.0));

        // for p = 1, we have the arithmetic mean.
        ArithmeticMean mean = ArithmeticMean.INSTANCE;
        double[] rand = random(10);
        assertEquals(mean.apply(rand), impl.apply(rand));

        impl = new PowerMean(1.1);
        assertTrue(mean.apply(rand) < impl.apply(rand));

        impl = new PowerMean(0.9);
        assertTrue(mean.apply(rand) > impl.apply(rand));
    }

    @Test
    public void testToString() {

    }
}
