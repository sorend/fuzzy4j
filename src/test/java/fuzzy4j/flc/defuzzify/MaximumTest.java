package fuzzy4j.flc.defuzzify;

import fuzzy4j.flc.Variable;
import fuzzy4j.sets.FuzzyFunction;
import fuzzy4j.sets.PiFunction;
import fuzzy4j.sets.TrapezoidalFunction;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class MaximumTest {

    Maximum impl;

    Variable var;
    FuzzyFunction set;

    @Before
    public void setup() {
        var = Variable.input("hello").start(0).end(15);
        set = new TrapezoidalFunction(0, 5, 10, 15);
    }

    @Test
    public void maxOfMax() throws Exception {
        impl = Maximum.maxOfMax();
        double res = impl.apply(var, set);
        assertEquals(10, res, 0.05);
    }

    @Test
    public void minOfMax() throws Exception {
        impl = Maximum.minOfMax();
        double res = impl.apply(var, set);
        assertEquals(5, res, 0.05);
    }

    @Test
    public void meanOfMax() throws Exception {
        impl = Maximum.meanOfMax();
        double res = impl.apply(var, set);
        assertEquals(7.5, res, 0.05);
    }

    public void pifunctionExample() {

        // this type of function should have only a single max value.
        set = new PiFunction(5, 10, 15, 1);

        double res1 = Maximum.meanOfMax().apply(var, set);

        double res2 = Maximum.minOfMax().apply(var, set);

        double res3 = Maximum.maxOfMax().apply(var, set);

        assertEquals(10, res1, 0.05);
        assertEquals(res1, res2);
        assertEquals(res1, res3);

    }

}
