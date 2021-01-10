package fuzzy4j.flc;

/**
 * Hedge is a modification of membership.
 *
 * Static implementations for different common hedges.
 *
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public interface Hedge {

    public static final Hedge ANY = new Hedge() {
        @Override
        public double apply(double value) {
            return 1.0;
        }
    };

    public static final Hedge EXTREMELY = new Hedge() {
        @Override
        public double apply(double value) {
            return (value < 0.5) ?
                    2 * value * value :
                    1 - 2 * (1 - value) * (1 - value);
        }
        public String toString() {
            return "extremely";
        }
    };

    public static final Hedge SELDOM = new Hedge() {
        @Override
        public double apply(double value) {
            return (value <= 0.5) ?
                    Math.sqrt(value / 2.0) :
                    1 - Math.sqrt((1 - value) / 2.0);
        }
        public String toString() {
            return "seldom";
        }
    };

    public static final Hedge SOMEWHAT = new Hedge() {
        @Override
        public double apply(double value) {
            return Math.sqrt(value);
        }
        public String toString() {
            return "somewhat";
        }
    };

    public static final Hedge NOT = new Hedge() {
        @Override
        public double apply(double value) {
            return 1.0 - value;
        }
        public String toString() {
            return "not";
        }

    };

    public static final Hedge VERY = new Hedge() {
        @Override
        public double apply(double value) {
            return value * value;
        }
        public String toString() {
            return "very";
        }
    };

    double apply(double value);
}
