package fuzzy4j;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;

/**
 * @author Soren A. Davidsen <sorend@gmail.com>
 */
public class ValuedMemberTest {

    ValuedMember<String> impl;

    @Test
    public void testTheStuff() throws Exception {

        impl = new ValuedMember<String>("test", 1.0);

        ValuedMember<String> other = new ValuedMember<String>("test", 0.1);

        assertEquals("test", impl.obj());
        assertEquals(1.0, impl.value());
        assertTrue(other.equals(impl));
        assertEquals("test".hashCode(), impl.hashCode());
    }

}
