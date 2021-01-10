package fuzzy4j;

import fuzzy4j.sets.FuzzyFunction;

import java.util.*;

/**
 * @author Soren <sorend@gmail.com>
 */
public class FuzzyMeasure {

    Map<Integer, Double> mu_U = new HashMap<Integer, Double>();

    public FuzzyMeasure(FuzzyFunction possibilityDistribution, int start, int end) {
        for (int i = start; i <= end; i++)
            mu_U.put(i, possibilityDistribution.apply(i));
    }

    public double[] calculate(int value) {
        return calculate(value, value);
    }

    public double[] calculate(int start, int end) {

        // possibility
        List<Double> pos = new ArrayList<Double>();
        List<Double> nec = new ArrayList<Double>();

        for (int i = start; i <= end; i++) {
            double mu = mu_U.containsKey(i) ? mu_U.get(i) : 0.0;

            pos.add(Math.min(1.0, mu));
        }

        // support of the
        for (int i : mu_U.keySet()) {
            double neg_pos = (i >= start && i <= end) ? 0 : 1;
            nec.add(Math.max(neg_pos, mu_U.get(i)));
        }

        double posRes = Collections.max(pos);
        double necRes = Collections.min(nec);

        return new double[]{ posRes, necRes };
    }
}
