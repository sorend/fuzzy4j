package fuzzy4j.util;

import fuzzy4j.aggregation.weighted.WeightedValue;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class ParametersUtil {

    public static void assertWeightedSumOne(WeightedValue... args) {
        double sum = 0.0;
        for (int i = 0; i< args.length; i++) sum += args[i].weight;
        double diff = Math.abs(1.0 - sum);
        if (diff > 0.00001)
            throw new IllegalArgumentException("sum(weights) is not 1 as required.");
    }

    public static void assertTwoParameters(Class clz, String method, double... args) {
        if (args == null || args.length != 2)
            throw new IllegalArgumentException(clz.getName() + "." + method + "(...) is only supported for two parameters (got " + args.length + ")");
    }

}
