package fuzzy4j.sets;

/**
 * Representation of a point.
 *
 * (Useful for environments where java.awt is not available.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class Point implements Comparable<Point> {

    public static Point $(double x, double y) {
        return new Point(x, y);
    }

    public final double x;
    public final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point o) {
        if (x > o.x) return 1;
        else if (x == o.x) return 0;
        else return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (Double.compare(point.x, x) != 0) return false;
        if (Double.compare(point.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = x != +0.0d ? Double.doubleToLongBits(x) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = y != +0.0d ? Double.doubleToLongBits(y) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
