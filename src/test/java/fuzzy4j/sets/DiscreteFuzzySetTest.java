package fuzzy4j.sets;

import fuzzy4j.Membership;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DiscreteFuzzySetTest {

    @Test
    void creation() {
        List<Membership<String>> memberships = List.of(
            new Membership<>("A", 0.5),
            new Membership<>("B", 1.0),
            new Membership<>("C", 0.0)
        );

        DiscreteFuzzySet<String> set = new DiscreteFuzzySet<>(memberships);

        assertNotNull(set);
    }

    @Test
    void creationWithEmptyList() {
        DiscreteFuzzySet<String> set = new DiscreteFuzzySet<>(List.of());

        assertNotNull(set);
    }

    @Test
    void support() {
        List<Membership<String>> memberships = List.of(
            new Membership<>("A", 0.5),
            new Membership<>("B", 1.0),
            new Membership<>("C", 0.75)
        );

        DiscreteFuzzySet<String> set = new DiscreteFuzzySet<>(memberships);
        Set<String> support = set.support();

        assertEquals(3, support.size());
        assertTrue(support.contains("A"));
        assertTrue(support.contains("B"));
        assertTrue(support.contains("C"));
    }

    @Test
    void supportEmptySet() {
        DiscreteFuzzySet<String> set = new DiscreteFuzzySet<>(List.of());
        Set<String> support = set.support();

        assertTrue(support.isEmpty());
    }

    @Test
    void muExistingElement() {
        List<Membership<Integer>> memberships = List.of(
            new Membership<>(1, 0.3),
            new Membership<>(2, 0.7),
            new Membership<>(3, 1.0)
        );

        DiscreteFuzzySet<Integer> set = new DiscreteFuzzySet<>(memberships);

        assertEquals(0.3, set.mu(1), 0.001);
        assertEquals(0.7, set.mu(2), 0.001);
        assertEquals(1.0, set.mu(3), 0.001);
    }

    @Test
    void muNonExistingElement() {
        List<Membership<Integer>> memberships = List.of(
            new Membership<>(1, 0.3),
            new Membership<>(2, 0.7)
        );

        DiscreteFuzzySet<Integer> set = new DiscreteFuzzySet<>(memberships);

        assertEquals(0.0, set.mu(99), 0.001);
        assertEquals(0.0, set.mu(-1), 0.001);
        assertEquals(0.0, set.mu(0), 0.001);
    }
}
