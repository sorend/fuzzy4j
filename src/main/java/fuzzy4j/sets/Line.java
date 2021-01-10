package fuzzy4j.sets;

/**
 * Simple linear function.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class Line {

    public static Line fromPoints(Point p_a, Point p_b) {
        double m = (p_b.y - p_a.y) / (p_b.x - p_a.x);
        double b = p_a.y - (m * p_a.x);
        return new Line(m, b);
    }

    public final double m;
    public final double b;

    public Line(double m, double b) {
        this.m = m;
        this.b = b;
    }

    public Point intersection(Line B) {
        double d_m = (B.m - this.m);
        if (d_m == 0.0)
            return null;
        double x = (this.b - B.b) / d_m;
        double y = this.m * x + this.b;
        return new Point(x, y);
    }

    public double apply(double x) {
        return (m * x) + b;
    }

    public boolean isIncreasing() {
        return m > 0.0;
    }
    public boolean isDecreasing() {
        return m < 0.0;
    }

    public String toString() {
        return String.format("%fx+%f", m, b);
    }
}
