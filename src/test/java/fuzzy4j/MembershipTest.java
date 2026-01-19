package fuzzy4j;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class MembershipTest {

    Membership<String> impl;

    @Test
    void testTheStuff() {

        impl = new Membership<String>("test", 1.0);

        Membership<String> other = new Membership<String>("test", 0.1);

        assertEquals("test", impl.obj());
        assertEquals(1.0, impl.value());
        assertTrue(other.equals(impl));
        assertEquals("test".hashCode(), impl.hashCode());
    }

}
