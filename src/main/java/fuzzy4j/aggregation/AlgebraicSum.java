package fuzzy4j.aggregation;

/**
 * Implementation of algebraic sum.
 *
 * Defined as:
 *      N_S(a, b) = a + b - (a*b)
 * For multiple operators
 *      N_S(a1, a2... an) = 1 - Prod^n_k=1 1-a_k
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class AlgebraicSum extends AbstractNorm {

    public static final AlgebraicSum INSTANCE = new AlgebraicSum();

    public AlgebraicSum() {
        super(Type.T_CONORM, AlgebraicProduct.INSTANCE);
    }

    @Override
    public double apply(double... values) {
        double s = 1.0;
        for (double v : values)
            s *= (1.0 - v);
        return 1.0 - s;
    }

    public String toString() {
        return "AlgebraicSum";
    }
}
