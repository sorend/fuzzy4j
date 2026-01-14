package fuzzy4j.sets;

/**
 * Representation of a point.
 *
 * (Useful for environments where java.awt is not available.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public record Point(double x, double y) implements Comparable<Point> {

    public static Point $(double x, double y) {
        return new Point(x, y);
    }

    @Override
    public int compareTo(Point o) {
        if (x > o.x) return 1;
        else if (x == o.x) return 0;
        else return -1;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
