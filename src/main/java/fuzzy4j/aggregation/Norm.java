package fuzzy4j.aggregation;

/**
 * Marker interface for norm-class aggregation operators
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public interface Norm extends Aggregation {

    public enum Type {
        T_NORM("⊗"),
        T_CONORM("⊕"),
        UNKNOWN("?");

        private String eval;
        private Type(String eval) {
            this.eval = eval;
        }
        public String toString() {
            return eval;
        }
    }

    public Type type();

    public Norm duality();
}
