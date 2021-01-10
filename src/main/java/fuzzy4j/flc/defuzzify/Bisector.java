package fuzzy4j.flc.defuzzify;

import fuzzy4j.flc.Variable;
import fuzzy4j.sets.FuzzyFunction;

/**
 * Support Bisector-type defuzzification.
 *
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class Bisector extends SamplingDefuzzify {

    @Override
    public double apply(Variable variable, FuzzyFunction set) {

        double min = variable.min();
        double max = variable.max();
        int samples = getSamples();
        double dx = (max - min) / samples;

        int left = 0, right = 0;
        double leftA = 0, rightA = 0;
        double xleft = min, xright = max;
        while (samples-- > 0) {
            if (leftA <= rightA) {
                xleft = min + (left + 0.5) * dx;
                leftA += set.apply(xleft);
                left++;
            }
            else {
                xright = max - (right + 0.5) * dx;
                rightA += set.apply(xright);
                right++;
            }
        }

        // apply bisector
        return (leftA * xright + rightA * xleft) / (leftA + rightA);
    }
}
