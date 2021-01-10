package fuzzy4j.negation;

import fuzzy4j.sets.TriangularFunction;
import org.junit.Test;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class YagerNegationTest {


    @Test
    public void testSimple() throws Exception {

        TriangularFunction ff = new TriangularFunction(0, 1, 2);
        YagerNegation yager = new YagerNegation(1.0, ff);

    }
}
