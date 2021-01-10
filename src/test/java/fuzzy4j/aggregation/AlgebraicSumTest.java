package fuzzy4j.aggregation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A Davidsen <sorend@gmail.com>
 */
public class AlgebraicSumTest {


    @Test
    public void checkImpl() {

        Aggregation aggr = AlgebraicSum.INSTANCE;

        // 1.0 + 1.0 - (1.0 * 1.0) = 2 - 1 = 1
        assertEquals(1.0, aggr.apply(1.0, 1.0));

        // 0.0 + 0.0 - (0 * 0) = 0 - 0 = 0
        assertEquals(0.0, aggr.apply(0.0, 0.0));

        // 0.8 + 0.5 - (0.5 * 0.8) = 1.3 - 0.4 = 0.9
        assertEquals(0.9, aggr.apply(0.8, 0.5));

    }

}
