package fuzzy4j.aggregation.weighted;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static fuzzy4j.aggregation.weighted.WeightedValue.w;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class RollingAIWATest {

    RollingAIWA impl;

    /**
     * Test from notes.
     * @throws Exception
     */
    @Test
    public void testCalc_Notes() throws Exception {

        impl = new RollingAIWA(2./3.);

        double A = impl.add(w(0.4, 0.1)).add(w(1.0, 0.7)).value();
        impl.clear();
        double B = impl.add(w(0.4, 0.9)).add(w(1.0, 0.4)).value();


        assertEquals(0.565, A, 0.001);
        assertEquals(0.442, B, 0.001);
    }

}
