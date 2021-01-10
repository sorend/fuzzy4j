package fuzzy4j.aggregation;

/**
 * Dombi class intersection operator [1].
 *
 * To get a dombi class union operator, use {@link fuzzy4j.aggregation.DombiIntersection#duality()}.
 *
 * [1] J. Dombi, "A general class of fuzzy operators, the DeMorgan class of fuzzy operators and fuzziness measures induced by fuzzy operators",
 *      Fuzzy Sets and Systems 8, pp. 149-163, 1982.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class DombiIntersection implements Norm {


    public static final ParametricFactory<DombiIntersection> BY_EXPONENT = new ParametricFactory<DombiIntersection>() {
        @Override
        public DombiIntersection create(double... params) {
            double p = params[0];
            if (p < 0.0)
                throw new IllegalArgumentException("p < 0");
            return new DombiIntersection(p);
        }
    };

    public static final ParametricFactory<DombiIntersection> BY_DRASTICALITY = new ParametricFactory<DombiIntersection>() {
        @Override
        public DombiIntersection create(double... params) {
            double d = params[0];
            if (d < 0.0 || d > 1.0)
                throw new IllegalArgumentException("drasticality \\not\\in [0, 1]");

            double p = (1.0 - d) / d;

            return new DombiIntersection(p);
        }
    };

    private double p;

    private DombiIntersection(double p) {
        this.p = p;
    }

    @Override
    public double apply(double... values) {

        if (Double.isInfinite(p))
            return Minimum.INSTANCE.apply(values);

        double s = 0.0;
        for (double x : values)
            if (x == 0.0) // if any x is 0, then s = Inf and 1/Inf = 0.
                return 0.0;
            else
                s += Math.pow((1.0 - x) / x, p);

        return 1.0 / (1.0 + Math.pow(s, 1.0 / p));
    }

    @Override
    public Type type() {
        return Type.T_NORM;
    }

    @Override
    public Norm duality() {
        return DombiUnion.BY_EXPONENT.create(p);
    }

    @Override
    public String toString() {
        return type() + "_dombi_" + p;
    }
}
