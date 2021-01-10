package fuzzy4j.util;

import fuzzy4j.Valued;
import fuzzy4j.aggregation.weighted.WeightedValue;
import fuzzy4j.sets.FuzzyFunction;
import fuzzy4j.sets.SingletonsSet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Soren <sorend@gmail.com>
 */
public class FuzzyUtil {

    public static double[] doubles(Collection<Double> doubles) {
        double[] r = new double[doubles.size()];
        int i = 0;
        for (double d : doubles)
            r[i++] = d;
        return r;
    }

    public static WeightedValue[] asArray(Collection<WeightedValue> collection) {
        return collection.toArray(new WeightedValue[collection.size()]);
    }

    public static String formatArray(WeightedValue[] values) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i].toString());
            if (i != 0)
                sb.append(", ");
        }
        return sb.append("]").toString();
    }

    public static WeightedValue[] valueMaxNormalize(WeightedValue[] values) {
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < values.length; i++)
            max = Math.max(max, values[i].value);
        return valueMaxNormalize(max, values);
    }

    public static WeightedValue[] valueMaxNormalize(double max, WeightedValue[] values) {
        WeightedValue[] tmp = new WeightedValue[values.length];
        for (int i = 0; i < values.length; i++)
            tmp[i] = WeightedValue.w(values[i].weight, values[i].value / max);
        return tmp;
    }


    public static <V extends Valued> SingletonsSet<V> normalizedFold(Map<V, Double> map) {
        final SingletonsSet<V> set = new SingletonsSet<V>();
        minMaxNormalize(map, new NormalizedHandler<V>() {
            @Override
            public void normalized(V element, double value) {
                set.add(value, element);
            }
        });
        return set;
    }

    public static interface NormalizedHandler<V> {
        public void normalized(V element, double value);
    }

    public static <V> void minMaxNormalize(Map<V, Double> map, NormalizedHandler<V> handler) {
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (Double d : map.values()) {
            if (d > max)
                max = d;
            if (d < min)
                min = d;
        }
        for (Entry<V, Double> e : map.entrySet()) {
            handler.normalized(e.getKey(), (e.getValue() - min) / (max - min));
        }
    }

    public static <V> Map<V, Double> minMaxNormalize(Map<V, Double> map) {
        final Map<V, Double> res = new HashMap<V, Double>();
        minMaxNormalize(map, new NormalizedHandler<V>() {
            @Override
            public void normalized(V element, double value) {
                res.put(element, value);
            }
        });
        return res;
    }

    public static double[] probeFunction(FuzzyFunction f, double min, double max, int steps) {
        final double[] result = new double[steps];
        probe(f, min, max, steps, new PointCallback() {
            @Override
            public void has(int i, double x, double y) {
                result[i] = y;
            }
        });
        return result;
    }

    public static interface PointCallback {
        public void has(int i, double x, double y);
    }

    public static void probe(FuzzyFunction f, double min, double max, int steps, PointCallback callback) {
        double delta = (max - min) / steps;
        for (int i = 0; i < steps; i++) {
            double x = min + (delta * (i + 0.5));
            callback.has(i, x, f.apply(x));
        }
    }

    public static String truncDouble(double val) {
        return String.valueOf(val).replaceAll("0+$", "").replaceAll("\\.$", "");
    }

}
