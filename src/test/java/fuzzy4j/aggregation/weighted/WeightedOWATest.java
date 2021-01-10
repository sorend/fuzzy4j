package fuzzy4j.aggregation.weighted;

import org.junit.Test;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class WeightedOWATest {

    WeightedOWA impl;

    @Test
    public void simpleTests() {

        // p = 0.3.., lambda=0.0, weights=( 1.0 )
        impl = new WeightedOWA(1./3., 1., 1.);

        // input: ((0.5, 1.0))
        double A = impl.apply(WeightedValue.pairs(0.5, 1.0));

        System.out.println("A = " + A);

    }
}
