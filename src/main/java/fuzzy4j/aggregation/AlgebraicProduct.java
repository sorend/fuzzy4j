package fuzzy4j.aggregation;

/**
 * Algebraic product aggregation operator.
 *
 * Defined as:
 *   <code>M_A(a_1, a_2, ... a_n) = Prod^n_k=1 a_k</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class AlgebraicProduct extends AbstractNorm {

    public static AlgebraicProduct INSTANCE = new AlgebraicProduct();

    public AlgebraicProduct() {
        super(Type.T_NORM, AlgebraicSum.INSTANCE);
    }

    @Override
    public double apply(double... values) {
        if (values.length < 1)
            return 0.0;
        double r = values[0];
        for (int i = 1; i < values.length; i++)
            r *= values[i];
        return r;
    }

    public String toString() {
        return "PROD";
    }

}
