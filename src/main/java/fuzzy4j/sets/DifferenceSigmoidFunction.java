package fuzzy4j.sets;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class DifferenceSigmoidFunction implements FuzzyFunction {

    private SigmoidFunction sigmoid1;
    private SigmoidFunction sigmoid2;

    public DifferenceSigmoidFunction(double alpha1, double x01, double alpha2, double x02) {
        sigmoid1 = new SigmoidFunction(alpha1, x01);
        sigmoid2 = new SigmoidFunction(alpha2, x02);
    }

    @Override
    public double apply(double x) {
        double v1 = sigmoid1.apply(x);
        double v2 = sigmoid2.apply(x);
        double diff = v1 - v2;
        return Math.max(0.0, diff);
    }
}
