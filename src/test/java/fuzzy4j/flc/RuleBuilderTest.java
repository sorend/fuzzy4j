package fuzzy4j.flc;

import fuzzy4j.aggregation.Aggregation;
import fuzzy4j.aggregation.Maximum;
import fuzzy4j.aggregation.Minimum;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class RuleBuilderTest {

    ControllerBuilder contr;
    Variable input;
    Variable out;
    Term term1;
    Term term2;
    RuleBuilder impl;

    @Before
    public void setUp() throws Exception {
        contr = ControllerBuilder.newBuilder();
        term1 = Term.term("low", 0, 5, 10);
        term2 = Term.term("high", 5, 10, 15);
        input = Variable.input("input", term1, term2);
        out = Variable.output("output", term1, term2);
        impl = new RuleBuilder(contr);
    }

    @Test
    public void testBasic() throws Exception {

        impl.var(input).is(term1).then().var(out).is(term2);

        assertEquals(1, contr.getRules().size());

        assertEquals(out, contr.getRules().get(0).then.variable);
        assertEquals(term2, contr.getRules().get(0).then.value);

        assertTrue(contr.getRules().get(0).rule instanceof ExpressionFactory.FuzzyExpression);
    }

    @Test
    public void testBasic_or() throws Exception {

        impl.var(input).is(term1).or().var(input).is(term2).then().var(out).is(term2);

        assertEquals(1, contr.getRules().size());

        assertEquals(out, contr.getRules().get(0).then.variable);
        assertEquals(term2, contr.getRules().get(0).then.value);

        final InputInstance instance = new InputInstance();
        InputInstanceAccessor a = new InputInstanceAccessor() {
            @Override
            public Aggregation orFunction() {
                return Maximum.INSTANCE;
            }

            @Override
            public Aggregation andFunction() {
                return Minimum.INSTANCE;
            }

            @Override
            public double valueOf(Variable variable, Term label) {
                return label.set.apply(instance.get(variable));
            }
        };

        instance.is(input, 5.0);
        assertEquals(1.0, contr.getRules().get(0).rule.apply(a));

        instance.is(input, 10.0);
        assertEquals(1.0, contr.getRules().get(0).rule.apply(a));
    }


    @Test
    public void testBasic_and() throws Exception {

        impl.var(input).is(term1).and().var(input).is(term2).then().var(out).is(term2);

        assertEquals(1, contr.getRules().size());

        assertEquals(out, contr.getRules().get(0).then.variable);
        assertEquals(term2, contr.getRules().get(0).then.value);

        final InputInstance instance = new InputInstance();
        InputInstanceAccessor a = new InputInstanceAccessor() {
            @Override
            public Aggregation orFunction() {
                return Maximum.INSTANCE;
            }

            @Override
            public Aggregation andFunction() {
                return Minimum.INSTANCE;
            }

            @Override
            public double valueOf(Variable variable, Term label) {
                return label.set.apply(instance.get(variable));
            }
        };

        instance.is(input, 5.0);
        assertEquals(0.0, contr.getRules().get(0).rule.apply(a));

        instance.is(input, 10.0);
        assertEquals(0.0, contr.getRules().get(0).rule.apply(a));

        instance.is(input, 7.5);
        assertEquals(0.5, contr.getRules().get(0).rule.apply(a));
    }


}
