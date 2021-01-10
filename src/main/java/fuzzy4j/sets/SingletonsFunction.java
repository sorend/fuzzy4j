package fuzzy4j.sets;

import fuzzy4j.aggregation.Minimum;
import fuzzy4j.util.FuzzyUtil;
import fuzzy4j.util.SimpleInterval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class SingletonsFunction implements FuzzyFunction, SupportAware {

    private double defaultValue = 0.0;
    private Point[] elements;

    public SingletonsFunction(double defaultValue, Point... elements) {
        this.defaultValue = defaultValue;
        this.elements = elements;
    }

    public SingletonsFunction(double... points) {
        elements = new Point[points.length];
        for (int i = 0; i < points.length; i++)
            elements[i] = new Point(points[i], 1.0);
    }

    public SingletonsFunction(Point... elements) {
        this.elements = elements;
    }

    @Override
    public double apply(double x) {
        for (int i = 0; i < elements.length; i++)
            if (elements[i].x == x)
                return elements[i].y;
        return defaultValue;
    }

    @Override
    public SimpleInterval support() {
        double min = Double.POSITIVE_INFINITY, max = Double.NEGATIVE_INFINITY;
        for (Point p : elements) {
            min = Math.min(p.x, min);
            max = Math.max(p.x, max);
        }
        return new SimpleInterval(true, min, max, true);
    }

    public Iterator<Point> iterator() {
        return Arrays.asList(elements).iterator();
    }
}
