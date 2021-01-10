package fuzzy4j.util;

import fuzzy4j.aggregation.Aggregation;
import fuzzy4j.sets.FuzzyFunction;
import fuzzy4j.sets.SupportAware;

/**
 * Representation of an interval.
 *
 * @author Soren <sorend@gmail.com>
 */
public class SimpleInterval implements Range, FuzzyFunction, SupportAware {

    public static SimpleInterval i(double start, double end) {
        return new SimpleInterval(true, start, end, true);
    }

    public static SimpleInterval ALL = new SimpleInterval(true, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
    public static SimpleInterval UNIT_INTERVAL = new SimpleInterval(true, 0.0, 1.0, true);

    public static SimpleInterval parse(String str) {
        return parse(str, ",");
    }

    /**
     * Parses an interval string representation.
     *
     * Closed interval: {@code [a, b]}
     * Open interval: {@code (a, b)} or {@code ]a, b[}
     *
     * @param str The string
     * @param delim Delimiter between interval "a" and "b".
     * @return
     */
    public static SimpleInterval parse(String str, String delim) {

        if (str == null)
            throw new IllegalArgumentException("str is null");

        String[] a = str.replaceAll("\\s+", "").split(delim);

        if (a.length != 2)
            throw new IllegalArgumentException("interval requires two parts, e.g. [0, 1]");

        a[0] = a[0].trim();
        a[1] = a[1].trim();

        boolean leftInclusive = a[0].charAt(0) == '[';
        boolean rightInclusive = a[1].charAt(a[1].length() - 1) == ']';

        double start = Double.parseDouble(a[0].substring(1));
        double end = Double.parseDouble(a[1].substring(0, a[1].length() - 1));

        return new SimpleInterval(leftInclusive, start, end, rightInclusive);
    }

    private double min;
    private double max;
    private boolean leftInclusive = true;
    private boolean rightInclusive = true;

    public SimpleInterval(boolean leftInclusive, double min, double max, boolean rightInclusive) {
        this.leftInclusive = leftInclusive;
        this.min = min;
        this.max = max;
        this.rightInclusive = rightInclusive;
    }

    /**
     * True if the value x is within the interval borders. See {@link SimpleInterval#within(double)}
     * @param x
     * @return
     */
    public double apply(double x) {
        return within(x) ? 1.0 : 0.0;
    }

    /**
     * The min of the interval.
     * @return
     */
    public double min() {
        return min;
    }

    /**
     * The max of the interval.
     * @return
     */
    public double max() {
        return max;
    }

    /**
     * Returns a new interval which is left exclusive.
     * @return
     */
    public SimpleInterval leftExclusive() {
        return new SimpleInterval(false, min, max, rightInclusive);
    }

    /**
     * Returns a new interval which is right exclusive.
     * @return
     */
    public SimpleInterval rightExclusive() {
        return new SimpleInterval(leftInclusive, min, max, false);
    }

    /**
     * Aggregates two intervals by applying an aggregation operator to min/max of the intervals.
     *
     * @param aggregation
     * @param other
     * @return
     */
    public SimpleInterval aggregate(Aggregation aggregation, SimpleInterval other) {
        return i(aggregation.apply(min(), other.min()), aggregation.apply(max(), other.max()));
    }

    /**
     * Returns the support of this interval (the interval itself)
     * @return
     */
    @Override
    public SimpleInterval support() {
        return this;
    }

    /**
     * Calculate if a given value is placed iwthin this interval or not.
     * @param value
     * @return
     */
    public boolean within(double value) {
        if (leftInclusive && value == min) // it is min, and interval is inclusive
            return true;
        else if (value > min && value < max) // between min and max
            return true;
        else if (rightInclusive && value == max) // it is max, and interval is inclusive
            return true;
        else // outside interval
            return false;
    }

    /**
     * Returns the formatted interval.
     * @return
     */
    public String toString() {
        return toString(",");
    }

    public String toString(String delim) {
        return String.format("%s%s%s%s%s", leftInclusive ? "[" : "(", FuzzyUtil.truncDouble(min), delim, FuzzyUtil.truncDouble(max), rightInclusive ? "]" : ")");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleInterval that = (SimpleInterval) o;

        if (leftInclusive != that.leftInclusive) return false;
        if (Double.compare(that.max, max) != 0) return false;
        if (Double.compare(that.min, min) != 0) return false;
        if (rightInclusive != that.rightInclusive) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = min != +0.0d ? Double.doubleToLongBits(min) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = max != +0.0d ? Double.doubleToLongBits(max) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (leftInclusive ? 1 : 0);
        result = 31 * result + (rightInclusive ? 1 : 0);
        return result;
    }
}
