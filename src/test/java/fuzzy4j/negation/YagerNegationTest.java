package fuzzy4j.negation;

import fuzzy4j.sets.TriangularFunction;
import org.junit.jupiter.api.Test;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class YagerNegationTest {


    @Test
    void testSimple() {

        TriangularFunction ff = new TriangularFunction(0, 1, 2);
        YagerNegation yager = new YagerNegation(1.0, ff);

    }
}
