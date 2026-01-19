package fuzzy4j.aggregation;

/**
 * Wraps an aggregation operator using the DeMorgan duality.
 *
 * 1) a plus b = not(not(a) times not(b))
 * 2) a times b = not(not(a) plus not(b))
 *
 * not(x) = 1 - x, using standard Zadeh negation.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class DeMorganDuality implements Aggregation {

    private Aggregation op;

    public DeMorganDuality(Aggregation op) {
        this.op = op;
    }

    @Override
    public double apply(double... values) {
        double[] inv = new double[values.length];
        for (int i = 0; i < values.length; i++)
            inv[i] = 1 - values[i];
        return 1 - op.apply(inv);
    }
}

