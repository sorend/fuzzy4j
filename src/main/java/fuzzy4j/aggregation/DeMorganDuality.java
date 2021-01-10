package fuzzy4j.aggregation;

/**
 * Wraps a t-norm operator using the DeMorgan duality.
 *
 * 1) a plus b = not(not(a) times not(b))
 * 2) a times b = not(not(a) plus not(b))
 *
 * not(x) = 1 - x, using standard Zadeh negation.
 *
* @author Soren A. Davidsen <sorend@gmail.com>
*/
class DeMorganDuality implements Norm, Aggregation {

    private Norm op;

    public DeMorganDuality(Norm op) {
        this.op = op;
    }

    @Override
    public double apply(double... values) {
        double[] inv = new double[values.length];
        for (int i = 0; i < values.length; i++)
            inv[i] = 1 - values[i];
        return 1 - op.apply(inv);
    }

    @Override
    public Type type() {
        if (op.type() == Type.UNKNOWN)
            return Type.UNKNOWN;
        else
            return op.type() == Norm.Type.T_NORM ? Norm.Type.T_CONORM : Norm.Type.T_NORM;
    }

    @Override
    public Norm duality() {
        // the duality of the duality is itself :)
        return op;
    }
}
