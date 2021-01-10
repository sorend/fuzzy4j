package fuzzy4j.aggregation;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class DombiUnionTest {

    @Test
    public void checkDrasticality() {

        DombiUnion max = DombiUnion.BY_DRASTICALITY.create(0.0);

        DombiUnion drasticUnion = DombiUnion.BY_DRASTICALITY.create(1.0);

        assertEquals(0.9, max.apply(0.9, 0.2, 0.1));

        assertEquals(1.0, drasticUnion.apply(0.9, 0.2, 0.1));

    }
}
