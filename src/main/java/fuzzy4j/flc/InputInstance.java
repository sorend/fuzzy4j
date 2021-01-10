package fuzzy4j.flc;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single input instance for evaluation by the fuzzy controller.
 *
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class InputInstance {

    /**
     * Wrap an existing map (changes to the instance will also be reflected in the map,
     * and vice versa).
     * @param map
     * @return
     */
    public static InputInstance wrap(Map<Variable, Double> map) {
        InputInstance instance = new InputInstance();
        instance.values = map;
        return instance;
    }

    private Map<Variable, Double> values = new HashMap();

    public InputInstance is(Variable variable, double value) {
        values.put(variable, value);
        return this;
    }

    public Double get(Variable variable) {
        return values.get(variable);
    }
}
