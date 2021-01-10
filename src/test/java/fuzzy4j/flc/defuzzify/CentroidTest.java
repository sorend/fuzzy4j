package fuzzy4j.flc.defuzzify;

import fuzzy4j.flc.Variable;
import fuzzy4j.sets.*;
import org.junit.Test;

import static fuzzy4j.sets.Point.$;
import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class CentroidTest {

    Centroid impl = new Centroid();
    Variable var = Variable.output("hello");

    @Test
    public void testExample_01_Triangular() {

        TriangularFunction set = new TriangularFunction(0, 5, 10);
        var.start(0).end(10);

        double res = impl.apply(var, set);

        assertEquals(5, res, 0.001);
    }

    @Test
    public void testExample_02_Trapezoidal() {

        TrapezoidalFunction set = new TrapezoidalFunction(0, 5, 10, 15);
        var.start(0).end(15);

        double res = impl.apply(var, set);

        assertEquals(7.5, res, 0.001);
    }

    @Test
    public void testExample_03_Composite() {

        // example taken from:
        // http://www.intelligent-systems.info/classes/ee509/9.PDF

        PointsLinearFunction f1 = new PointsLinearFunction(0.0, $(0, 0), $(1, 0.3), $(4, 0.3), $(5, 0));
        PointsLinearFunction f2 = new PointsLinearFunction(0.0, $(3, 0), $(4, 0.5), $(6, 0.5), $(7, 0));
        PointsLinearFunction f3 = new PointsLinearFunction(0.0, $(5, 0), $(6, 1), $(7, 1), $(8, 0));

        CompositeFunction f = new CompositeFunction(fuzzy4j.aggregation.Maximum.INSTANCE, f1, f2, f3);

        System.out.println("f = " + f);

        var.start(0).end(8);

        double res = impl.apply(var, f);

        System.out.println("res = " + res);

        assertEquals(4.9, res, 0.1);

    }

}
