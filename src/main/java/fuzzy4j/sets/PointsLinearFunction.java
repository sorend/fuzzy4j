package fuzzy4j.sets;

import fuzzy4j.util.SimpleInterval;

import java.util.Arrays;

/**
 * A apply function which uses datapoints to represent it's shape.
 *
 * Note. Datapoints must be ordered when creating the membershipfunction.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class PointsLinearFunction implements FuzzyFunction, SupportAware {

    private double defaultValue = 0.0;
    public final Point[] points;

    public PointsLinearFunction(double defaultValue, Point... points) {
        this(points);
        this.defaultValue = defaultValue;
    }

    @Override
    public SimpleInterval support() {
        return new SimpleInterval(true, points[0].x, points[points.length-1].x, true);
    }

    public PointsLinearFunction(Point... points) {
        this.points = points;

        // singleton
        if (points.length == 1)
            return;

        // examine pairs
        for (int i = 0; i < points.length - 1; i++) {
            Point a = points[i];
            Point b = points[i+1];

            assert a.x < b.x;

            if (Double.isInfinite(a.x) || Double.isInfinite(b.x))
                assert a.y == b.y;
        }
    }

    @Override
    public double apply(double x) {
        // singleton point
        if (points.length == 1)
            return points[0].x == x ? points[0].y : defaultValue;

        for (int i = 0; i < points.length - 1; i++) {
            Point left = points[i], right = points[i+1];

            // check if "x" is within these two points.

            if (x == left.x)
                return left.y;
            else if (x == right.x)
                return right.y;
            else if (x > left.x && x < right.x) {
                if (left.y == right.y) // no slope
                    return left.y;
                else {
                    double m = (right.y - left.y) / (right.x - left.x);
                    double b = left.y - (m * left.x);
                    return (m * x) + b;
                }
            }
        }

        return defaultValue;
    }

    public String toString() {
        return "PointsLinear" + Arrays.asList(points) + "";
    }

}
