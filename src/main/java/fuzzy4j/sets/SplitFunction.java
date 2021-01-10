package fuzzy4j.sets;

/**
 * A apply function which allows to join two sets based on a split in x values.
 *
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class SplitFunction implements FuzzyFunction {

    private double splitAt;

    private FuzzyFunction below, above;

    public SplitFunction(double splitAt, FuzzyFunction below, FuzzyFunction above) {
        this.splitAt = splitAt;
        this.below = below;
        this.above = above;
    }

    @Override
    public double apply(double x) {
        if (x <= splitAt)
            return below.apply(x);
        else
            return above.apply(x);
    }
}
