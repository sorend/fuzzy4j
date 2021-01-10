package fuzzy4j.util;

import fuzzy4j.sets.TriangularFunction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class FuzzyUtilTest {


    @Test
    public void testProbe() throws Exception {

        TriangularFunction tri = new TriangularFunction(0, 1, 2);

        final List<Double[]> vals = new ArrayList<Double[]>();

        FuzzyUtil.probe(tri, 0, 2, 100, new FuzzyUtil.PointCallback() {
            @Override
            public void has(int i, double x, double y) {
                vals.add(new Double[]{ x, y });
            }
        });

        assertEquals(100, vals.size());

        assertEquals(0.01, vals.get(0)[0]);
        assertEquals(1.99, vals.get(99)[0]);
    }
}
