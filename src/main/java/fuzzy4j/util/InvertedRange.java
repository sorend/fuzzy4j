package fuzzy4j.util;

/**
 * @author Soren <sorend@gmail.com>
 */
public class InvertedRange implements Range {
    private Range inner;

    public InvertedRange(Range inner) {
        this.inner = inner;
    }

    @Override
    public boolean within(double value) {
        return !inner.within(value);
    }
}
