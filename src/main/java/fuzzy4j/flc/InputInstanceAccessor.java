package fuzzy4j.flc;

import fuzzy4j.aggregation.Aggregation;

/**
 * Retrieves the discretized value.
 * @author Soren <sorend@gmail.com>
 */
public interface InputInstanceAccessor {

    // the current fuzzy "or" function
    Aggregation orFunction();

    // the current fuzzy "and" function
    Aggregation andFunction();

    // ex: valueOf("temp", "low")
    double valueOf(Variable variable, Term label);
}
