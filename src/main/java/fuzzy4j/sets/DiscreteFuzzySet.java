package fuzzy4j.sets;

import fuzzy4j.Membership;
import java.util.*;

public final class DiscreteFuzzySet<T> {

    private final Map<T, Double> mu;

    public DiscreteFuzzySet(List<Membership<T>> memberships) {
        Map<T, Double> map = new HashMap<>();
        for (Membership<T> m : memberships) {
            map.put(m.obj(), m.value());
        }
        this.mu = Collections.unmodifiableMap(map);
    }

    public double mu(T x) {
        return mu.getOrDefault(x, 0.0);
    }

    public Set<T> support() {
        return mu.keySet();
    }
}
