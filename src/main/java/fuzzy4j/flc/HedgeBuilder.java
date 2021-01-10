package fuzzy4j.flc;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class HedgeBuilder {

    public static HedgeBuilder not(Term term) {
        return new HedgeBuilder(term, Hedge.NOT);
    }

    public static HedgeBuilder any(Term term) {
        return new HedgeBuilder(term, Hedge.ANY);
    }

    public static HedgeBuilder extremely(Term term) {
        return new HedgeBuilder(term, Hedge.EXTREMELY);
    }

    public static HedgeBuilder very(Term term) {
        return new HedgeBuilder(term, Hedge.VERY);
    }

    public static HedgeBuilder seldom(Term term) {
        return new HedgeBuilder(term, Hedge.SELDOM);
    }

    public static HedgeBuilder somewhat(Term term) {
        return new HedgeBuilder(term, Hedge.SOMEWHAT);
    }

    public final Hedge hedge;
    public final Term term;

    public HedgeBuilder(Term term, Hedge hedge) {
        this.term = term;
        this.hedge = hedge;
    }
}
