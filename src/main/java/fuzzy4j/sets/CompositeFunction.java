package fuzzy4j.sets;

import fuzzy4j.aggregation.Aggregation;

import java.util.Arrays;

/**
 * Membership function which aggregates other fuzzy sets by an aggregation operator.
 *
 * For example, create product of two fuzzy sets:
 * <pre>
 * {@code
 * // create two triangular functions
 * TriangularFunction f1 = new TriangularFunction(1.0, 2.0, 3.0);
 * TriangularFunction f2 = new TriangularFunction(0.5, 1.0, 1.5);
 * // create a composite, which is the product of the two.
 * CompositeFunction prod = new CompositeFunction(AlgebraicProduct.INSTANCE, f1, f2);
 * }
 * </pre>
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class CompositeFunction implements FuzzyFunction {

    public final FuzzyFunction[] inners;
    public final Aggregation aggregation;

    public CompositeFunction(Aggregation aggregation, FuzzyFunction... inners) {
        this.aggregation = aggregation;
        this.inners = inners;
    }

    @Override
    public double apply(double x) {
        // find values
        double[] vals = new double[inners.length];
        for (int i = 0; i < vals.length; i++)
            vals[i] = inners[i].apply(x);
        // aggregate
        return aggregation.apply(vals);
    }

    @Override
    public String toString() {
        return aggregation + "(" + Arrays.asList(inners) + ")";
    }
}
