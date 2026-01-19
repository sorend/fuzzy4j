package fuzzy4j.algebra;

import fuzzy4j.aggregation.Aggregation;

public class Norms {

    public static class DualSNorm implements SNorm {
        private final TNorm tnorm;

        public DualSNorm(TNorm tnorm) {
            this.tnorm = tnorm;
        }

        @Override
        public double s(double a, double b) {
            return 1.0 - tnorm.t(1.0 - a, 1.0 - b);
        }
    }

    public static SNorm fromTNorm(TNorm tnorm) {
        return new DualSNorm(tnorm);
    }

    public static TNorm toTNorm(DualSNorm snorm) {
        return snorm.tnorm;
    }

    private static TNorm named(String name, TNorm tnorm) {
        return new TNorm() {
            @Override
            public double t(double a, double b) {
                return tnorm.t(a, b);
            }

            @Override
            public String toString() {
                return name;
            }
        };
    }

    public static TNorm dombi(double p) {
        if (p < 0.0)
            throw new IllegalArgumentException("p < 0");
        return named("T_NORM_dombi_" + p, (a, b) -> {
            if (Double.isInfinite(p))
                return Math.min(a, b);

            if (a == 0.0 || b == 0.0)
                return 0.0;

            double s = Math.pow((1.0 - a) / a, p) + Math.pow((1.0 - b) / b, p);
            return 1.0 / (1.0 + Math.pow(s, 1.0 / p));
        });
    }

    public static TNorm dombiByDrasticality(double d) {
        if (d < 0.0 || d > 1.0)
            throw new IllegalArgumentException("drasticality \\not\\in [0, 1]");
        double p = (1.0 - d) / d;
        return dombi(p);
    }

    public static TNorm duboisPrade(double p) {
        if (p < 0 || p > 1)
            throw new IllegalArgumentException("requires p in [0, 1]");
        return named("T_NORM_dp", (a, b) -> (a * b) / Math.max(Math.max(a, b), p));
    }

    public static TNorm aczelAlsina(double p) {
        if (p < 0.0)
            throw new IllegalArgumentException("expected p >= 0");
        if (p == 0.)
            return drastic();
        else if (Double.isInfinite(p))
            return minimum();
        else
            return named("T_NORM_aczel_alsina_" + p, (a, b) -> {
                double lap = Math.pow(-Math.log(a), p);
                double lbp = Math.pow(-Math.log(b), p);
                return Math.exp(-Math.pow(lap + lbp, 1.0 / p));
            });
    }

    private static final TNorm DRASTIC = named("T_NORM_drastic", (a, b) -> a == 1.0 ? b : b == 1.0 ? a : 0.0);

    public static TNorm drastic() {
        return DRASTIC;
    }

    private static final TNorm EINSTEIN = named("T_NORM_einstein", (a, b) -> (a * b) / (2 - (a + b - (a * b))));

    public static TNorm einstein() {
        return EINSTEIN;
    }

    public static TNorm frank(double p) {
        if (p < 0.0)
            throw new IllegalArgumentException("p must be > 0");
        else if (p == 0.0)
            return minimum();
        else if (p == 1.0)
            return product();
        else if (Double.isInfinite(p))
            return lukasiewicz();
        else
            return named("T_NORM_frank_" + p, (a, b) -> Math.log(1.0 + (Math.pow(p, a) - 1.0) * (Math.pow(p, b) - 1.0) / (p - 1.0)) / Math.log(p));
    }

    private static final TNorm NILPOTENT_MINIMUM = named("T_NORM_nilpotent_minimum", (a, b) -> a + b > 1.0 ? Math.min(a, b) : 0.0);

    public static TNorm nilpotentMinimum() {
        return NILPOTENT_MINIMUM;
    }

    public static TNorm yager(double p) {
        if (p < 0.0)
            throw new IllegalArgumentException("p < 0");
        else if (p == 0.0)
            return drastic();
        else if (Double.isInfinite(p))
            return minimum();
        else
            return named("T_NORM_yager_" + p, (a, b) -> {
                double s = Math.pow(1.0 - a, p) + Math.pow(1.0 - b, p);
                return 1.0 - Math.min(1, Math.pow(s, 1.0 / p));
            });
    }

    public static TNorm sugenoWeber(double p) {
        if (p < -1.)
            throw new IllegalArgumentException("requires p >= -1");
        else if (p == 1.)
            return drastic();
        else if (Double.isInfinite(p))
            return product();
        else
            return named("T_NORM_sugeno_weber_" + p, (a, b) -> Math.max(0., (a + b - 1. + (p * a * b)) / (1. + p)));
    }

    private static final TNorm LUKASIEWICZ = named("T_NORM_lukasiewicz", (a, b) -> Math.max(0, a + b - 1));

    public static TNorm lukasiewicz() {
        return LUKASIEWICZ;
    }

    public static TNorm schweizerSklar(double p) {
        if (Double.isInfinite(p)) {
            if (p == Double.NEGATIVE_INFINITY)
                return minimum();
            else
                return drastic();
        } else if (p == 0.0)
            return product();
        else
            return named("T_NORM_schweizer_sklar_" + p, (a, b) -> Math.pow(Math.max(0.0, Math.pow(a, p) + Math.pow(b, p) - 1.0), 1.0 / p));
    }

    public static TNorm hamacher(double p) {
        if (p < 0.0)
            throw new IllegalArgumentException("p < 0");
        else if (Double.isInfinite(p))
            return drastic();
        else
            return named("T_NORM_hamacher_" + p, (a, b) -> {
                if (a == 0.0 && b == 0.0 && p == 0.0)
                    return 0.0;
                else
                    return (a * b) / (p + (1 - p) * (a + b + (a * b)));
            });
    }

    public static TNorm hamacherProduct() {
        return hamacher(0.0);
    }

    private static final TNorm MINIMUM = named("T_NORM_min", Math::min);

    public static TNorm minimum() {
        return MINIMUM;
    }

    private static final TNorm PRODUCT = named("T_NORM_prod", (a, b) -> a * b);

    public static TNorm product() {
        return PRODUCT;
    }

    private static final TNorm ALGEBRAIC_PRODUCT = named("T_NORM_algebraic_product", (a, b) -> a * b);

    public static TNorm algebraicProduct() {
        return ALGEBRAIC_PRODUCT;
    }

    public static class TNormWrapper implements Aggregation {
        private final TNorm tnorm;

        public TNormWrapper(TNorm tnorm) {
            this.tnorm = tnorm;
        }

        @Override
        public double apply(double... values) {
            if (values.length != 2) {
                throw new IllegalArgumentException("TNorm requires exactly 2 values");
            }
            return tnorm.t(values[0], values[1]);
        }
    }

    public static Aggregation asAggregation(TNorm tnorm) {
        return new TNormWrapper(tnorm);
    }

    public static class SNormWrapper implements Aggregation {
        private final SNorm snorm;

        public SNormWrapper(SNorm snorm) {
            this.snorm = snorm;
        }

        @Override
        public double apply(double... values) {
            if (values.length != 2) {
                throw new IllegalArgumentException("SNorm requires exactly 2 values");
            }
            return snorm.s(values[0], values[1]);
        }
    }

    public static Aggregation asAggregation(SNorm snorm) {
        return new SNormWrapper(snorm);
    }
}
