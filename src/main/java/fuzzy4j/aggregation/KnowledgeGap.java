package fuzzy4j.aggregation;

/**
 * Knowledge gap averaging operator.
 *
 * Defined as:
 *      <code>M_K(A) = min max i ( ai ) , 1 − ( max i ( ai ) − min i ( ai ) )</code>
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class KnowledgeGap implements Mean {

    @Override
    public double compute(double... values) {
        double[] minmax = Maximum.minmax(values);
        return Math.min(minmax[1], 1 - (minmax[1] - minmax[0]));
    }
}
