package fuzzy4j.algebra;

import fuzzy4j.aggregation.Aggregation;
import fuzzy4j.aggregation.Norm;

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

    public static TNorm dombi(double p) {
        if (p < 0.0)
            throw new IllegalArgumentException("p < 0");
        return new DombiTNorm(p);
    }

    public static TNorm dombiByDrasticality(double d) {
        if (d < 0.0 || d > 1.0)
            throw new IllegalArgumentException("drasticality \\not\\in [0, 1]");
        double p = (1.0 - d) / d;
        return new DombiTNorm(p);
    }

    public static class DombiTNorm implements TNorm {
        private final double p;

        private DombiTNorm(double p) {
            this.p = p;
        }

        @Override
        public double t(double a, double b) {
            if (Double.isInfinite(p))
                return Math.min(a, b);

            if (a == 0.0 || b == 0.0)
                return 0.0;

            double s = Math.pow((1.0 - a) / a, p) + Math.pow((1.0 - b) / b, p);
            return 1.0 / (1.0 + Math.pow(s, 1.0 / p));
        }

        @Override
        public String toString() {
            return "T_NORM_dombi_" + p;
        }
    }

    public static TNorm duboisPrade(double p) {
        if (p < 0 || p > 1)
            throw new IllegalArgumentException("requires p in [0, 1]");
        return new DuboisPradeTNorm(p);
    }

    public static class DuboisPradeTNorm implements TNorm {
        public final double p;

        public DuboisPradeTNorm(double p) {
            this.p = p;
        }

        @Override
        public double t(double a, double b) {
            return (a * b) / Math.max(Math.max(a, b), p);
        }

        @Override
        public String toString() {
            return "T_NORM_dp";
        }
    }

    public static TNorm aczelAlsina(double p) {
        if (p < 0.0)
            throw new IllegalArgumentException("expected p >= 0");
        if (p == 0.)
            return drastic();
        else if (Double.isInfinite(p))
            return minimum();
        else
            return new AczelAlsinaTNorm(p);
    }

    public static class AczelAlsinaTNorm implements TNorm {
        private final double p;

        public AczelAlsinaTNorm(double p) {
            this.p = p;
        }

        @Override
        public double t(double a, double b) {
            double lap = Math.pow(-Math.log(a), p);
            double lbp = Math.pow(-Math.log(b), p);
            return Math.exp(-Math.pow(lap + lbp, 1.0 / p));
        }

        @Override
        public String toString() {
            return "T_NORM_aczel_alsina_" + p;
        }
    }

    public static TNorm drastic() {
        return DrasticTNorm.INSTANCE;
    }

    public static class DrasticTNorm implements TNorm {
        public static final DrasticTNorm INSTANCE = new DrasticTNorm();

        private DrasticTNorm() {
        }

        @Override
        public double t(double a, double b) {
            return a == 1.0 ? b : b == 1.0 ? a : 0.0;
        }

        @Override
        public String toString() {
            return "T_NORM_drastic";
        }
    }

    public static TNorm einstein() {
        return EinsteinTNorm.INSTANCE;
    }

    public static class EinsteinTNorm implements TNorm {
        public static final EinsteinTNorm INSTANCE = new EinsteinTNorm();

        @Override
        public double t(double a, double b) {
            return (a * b) / (2 - (a + b - (a * b)));
        }

        @Override
        public String toString() {
            return "T_NORM_einstein";
        }
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
            return new FrankTNorm(p);
    }

    public static class FrankTNorm implements TNorm {
        private final double p;

        private FrankTNorm(double p) {
            this.p = p;
        }

        @Override
        public double t(double a, double b) {
            return Math.log(1.0 + (Math.pow(p, a) - 1.0) * (Math.pow(p, b) - 1.0) / (p - 1.0)) / Math.log(p);
        }

        @Override
        public String toString() {
            return "T_NORM_frank_" + p;
        }
    }

    public static TNorm nilpotentMinimum() {
        return NilpotentMinimumTNorm.INSTANCE;
    }

    public static class NilpotentMinimumTNorm implements TNorm {
        public static final NilpotentMinimumTNorm INSTANCE = new NilpotentMinimumTNorm();

        @Override
        public double t(double a, double b) {
            return a + b > 1.0 ? Math.min(a, b) : 0.0;
        }

        @Override
        public String toString() {
            return "T_NORM_nilpotent_minimum";
        }
    }

    public static TNorm yager(double p) {
        if (p < 0.0)
            throw new IllegalArgumentException("p < 0");
        else if (p == 0.0)
            return drastic();
        else if (Double.isInfinite(p))
            return minimum();
        else
            return new YagerTNorm(p);
    }

    public static class YagerTNorm implements TNorm {
        private final double p;

        private YagerTNorm(double p) {
            this.p = p;
        }

        @Override
        public double t(double a, double b) {
            double s = Math.pow(1.0 - a, p) + Math.pow(1.0 - b, p);
            return 1.0 - Math.min(1, Math.pow(s, 1.0 / p));
        }

        @Override
        public String toString() {
            return "T_NORM_yager_" + p;
        }
    }

    public static TNorm sugenoWeber(double p) {
        if (p < -1.)
            throw new IllegalArgumentException("requires p >= -1");
        else if (p == 1.)
            return drastic();
        else if (Double.isInfinite(p))
            return product();
        else
            return new SugenoWeberTNorm(p);
    }

    public static class SugenoWeberTNorm implements TNorm {
        private final double p;

        public SugenoWeberTNorm(double p) {
            this.p = p;
        }

        @Override
        public double t(double a, double b) {
            return Math.max(0., (a + b - 1. + (p * a * b)) / (1. + p));
        }

        @Override
        public String toString() {
            return "T_NORM_sugeno_weber_" + p;
        }
    }

    public static TNorm lukasiewicz() {
        return LukasiewiczTNorm.INSTANCE;
    }

    public static class LukasiewiczTNorm implements TNorm {
        public static final LukasiewiczTNorm INSTANCE = new LukasiewiczTNorm();

        @Override
        public double t(double a, double b) {
            return Math.max(0, a + b - 1);
        }

        @Override
        public String toString() {
            return "T_NORM_lukasiewicz";
        }
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
            return new SchweizerSklarTNorm(p);
    }

    public static class SchweizerSklarTNorm implements TNorm {
        private final double p;

        public SchweizerSklarTNorm(double p) {
            this.p = p;
        }

        @Override
        public double t(double a, double b) {
            return Math.pow(Math.max(0.0, Math.pow(a, p) + Math.pow(b, p) - 1.0), 1.0 / p);
        }

        @Override
        public String toString() {
            return "T_NORM_schweizer_sklar_" + p;
        }
    }

    public static TNorm hamacher(double p) {
        if (p < 0.0)
            throw new IllegalArgumentException("p < 0");
        else if (Double.isInfinite(p))
            return drastic();
        else
            return new HamacherTNorm(p);
    }

    public static TNorm hamacherProduct() {
        return hamacher(0.0);
    }

    public static class HamacherTNorm implements TNorm {
        private final double p;

        private HamacherTNorm(double p) {
            this.p = p;
        }

        @Override
        public double t(double a, double b) {
            if (a == 0.0 && b == 0.0 && p == 0.0)
                return 0.0;
            else
                return (a * b) / (p + (1 - p) * (a + b + (a * b)));
        }

        @Override
        public String toString() {
            return "T_NORM_hamacher_" + p;
        }
    }

    public static TNorm minimum() {
        return MinimumTNorm.INSTANCE;
    }

    public static class MinimumTNorm implements TNorm {
        public static final MinimumTNorm INSTANCE = new MinimumTNorm();

        @Override
        public double t(double a, double b) {
            return Math.min(a, b);
        }

        @Override
        public String toString() {
            return "T_NORM_min";
        }
    }

    public static TNorm product() {
        return ProductTNorm.INSTANCE;
    }

    public static class ProductTNorm implements TNorm {
        public static final ProductTNorm INSTANCE = new ProductTNorm();

        @Override
        public double t(double a, double b) {
            return a * b;
        }

        @Override
        public String toString() {
            return "T_NORM_prod";
        }
    }

    public static class TNormWrapper implements Norm {
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

        @Override
        public Type type() {
            return Type.T_NORM;
        }

        @Override
        public Norm duality() {
            return new SNormWrapper(fromTNorm(tnorm));
        }
    }

    public static Norm asNorm(TNorm tnorm) {
        return new TNormWrapper(tnorm);
    }

    public static class SNormWrapper implements Norm {
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

        @Override
        public Type type() {
            return Type.T_CONORM;
        }

        @Override
        public Norm duality() {
            return new TNormWrapper(toTNorm(snorm instanceof DualSNorm ? (DualSNorm) snorm : null));
        }
    }
}
