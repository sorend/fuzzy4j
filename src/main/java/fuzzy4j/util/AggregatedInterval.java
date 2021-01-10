package fuzzy4j.util;

/**
 * Supports aggregating ranges
 *
 * @see SimpleInterval
 * @author Soren <sorend@gmail.com>
 */
public class AggregatedInterval implements Range {
    private Range[] ranges;

    public AggregatedInterval(Range... ranges) {
        this.ranges = ranges;
    }

    @Override
    public boolean within(double value) {
        for (Range range : ranges) {
            if (range.within(value))
                return true;
        }
        return false;
    }
}
