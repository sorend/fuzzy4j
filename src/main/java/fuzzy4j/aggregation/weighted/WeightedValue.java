package fuzzy4j.aggregation.weighted;

/**
 * Simple implementation of a sequence in Java
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class WeightedValue implements Comparable<WeightedValue> {

    public static WeightedValue[] pairs(double... weightValueParis) {
        if (weightValueParis.length % 2 != 0)
            throw new IllegalArgumentException("not pairs passed, must be even number of arguments");
        WeightedValue[] r = new WeightedValue[weightValueParis.length / 2];
        int j = 0;
        for (int i = 0; i < weightValueParis.length; i += 2) {
            r[j++] = WeightedValue.w(weightValueParis[i], weightValueParis[i + 1]);
        }
        return r;
    }

    public static WeightedValue w(double weight, double value) {
        return new WeightedValue(weight, value);
    }

    public final double value;
    public final double weight;

    public WeightedValue(double weight, double value) {
        this.weight = weight;
        this.value = value;
    }

    @Override
    public int compareTo(WeightedValue o) {
        return Double.compare(this.weight, o.weight);
    }

    public String toString() {
        return String.format("(%.5f, %.5f)", weight, value);
        // return "(" + weight + ", " + value + ")";
    }
}
