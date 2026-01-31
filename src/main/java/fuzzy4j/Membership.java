package fuzzy4j;

/**
 * Simple wrapper to make any object valued.
 *
 * The membership maintains the identity equals and hashCode of the wrapped object.
 *
 * Soren A. Davidsen (sorend@gmail.com)
 */
public record Membership<T>(T obj, double value) implements Valued {
}
