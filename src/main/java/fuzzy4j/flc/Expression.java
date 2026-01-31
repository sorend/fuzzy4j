package fuzzy4j.flc;

/**
 * Representation of an expression for evaluation
 *
 * Soren (sorend@gmail.com)
 */
public interface Expression {

    double apply(InputInstanceAccessor accessor);
}
