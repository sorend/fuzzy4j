package fuzzy4j.flc.defuzzify;

import fuzzy4j.flc.Variable;
import fuzzy4j.sets.FuzzyFunction;
import fuzzy4j.util.FuzzyUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Support Centroid-type defuzzification.
 *
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class Centroid extends SamplingDefuzzify {

    private final Logger LOG = Logger.getLogger(Centroid.class.getName());

    private class CentroidCallback implements FuzzyUtil.PointCallback {
        double centroid_x = 0;
        double centroid_y = 0;
        double area = 0;
        @Override
        public void has(int i, double x, double y) {
            centroid_x += x * y;
            centroid_y += y * y;
            area += y;
        }
    }

    @Override
    public double apply(Variable variable, FuzzyFunction set) {

        CentroidCallback callback = new CentroidCallback();

        FuzzyUtil.probe(set, variable.min(), variable.max(), getSamples(), callback);

        return callback.centroid_x / callback.area;
    }
}
