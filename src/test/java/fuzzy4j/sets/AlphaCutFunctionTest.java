package fuzzy4j.sets;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class AlphaCutFunctionTest {

    @Test
    public void checkAlphacut_on_Linear() {

        LinearFunction l = new LinearFunction(Line.fromPoints(new Point(0.0, 0.0), new Point(1.0, 1.0)));

        assertEquals(0.0, l.apply(0.0));
        assertEquals(0.5, l.apply(0.5));
        assertEquals(1.0, l.apply(1.0));

        AlphaCutFunction alphacut = new AlphaCutFunction(l, 0.6);

        assertEquals(0.0, alphacut.apply(0.0));
        assertEquals(0.0, alphacut.apply(0.5));
        assertEquals(1.0, alphacut.apply(1.0));

    }
}
