/*
 * Copyright (c) 2012, Søren Atmakuri Davidsen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package fuzzy4j.sets;

import fuzzy4j.aggregation.Aggregation;
import fuzzy4j.Valued;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author Soren <soren@tanesha.net>
 */
public final class SingletonsSet<V extends Valued> {

    public static <V extends Valued> SingletonsSet<V> create(V... elements) {
        SingletonsSet<V> set = new SingletonsSet<V>();
        for (V v : elements) {
            set.members.put(v, v.value());
        }
        return set;
    }

    private Map<V, Double> members = new HashMap<V, Double>();

    public SingletonsSet() {
    }

    public Set<V> members() {
        return members.keySet();
    }

    public boolean contains(V element) {
        return members.containsKey(element);
    }

    public double membership(V element) {
        if (members.containsKey(element))
            return members.get(element);
        else
            return 0.0;
    }

    public SingletonsSet<V> add(double membership, V element) {
        if (membership < 0.0 || membership > 1.0)
            throw new RuntimeException("Membership must be in [0, 1]");
        // only add if apply is > 0.0
        else if (membership > 0.0)
            members.put(element, membership);

        return this;
    }

    public SingletonsSet<V> remove(V element) {
        members.remove(element);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingletonsSet weightedSingletons = (SingletonsSet) o;

        if (!members.equals(weightedSingletons.members)) return false;

        return true;
    }

    public SingletonsSet<V> intersect(SingletonsSet<V> other) {
        SingletonsSet<V> newSet = new SingletonsSet<V>();
        for (V elem : members.keySet()) {
            double intersect = Math.min(members.get(elem), other.membership(elem));
            if (intersect > 0.0) {
                newSet.members.put(elem, intersect);
            }
        }
        return newSet;
    }

    public SingletonsSet<V> union(SingletonsSet<V> other) {
        SingletonsSet<V> newSet = new SingletonsSet<V>();
        Set<V> unionSet = new HashSet<V>(members.keySet());
        unionSet.addAll(other.members.keySet());
        for (V elem : unionSet) {
            double union = Math.max(membership(elem), other.membership(elem));
            if (union > 0.0) {
                newSet.members.put(elem, union);
            }
        }
        return newSet;
    }

    public SingletonsSet<V> negation() {
        SingletonsSet<V> newSet = new SingletonsSet<V>();
        for (V elem : members.keySet())
            newSet.members.put(elem, 1 - members.get(elem));
        return newSet;
    }

    public double aggregate(Aggregation operator) {
        double[] d = new double[members.size()];
        int i = 0;
        for (Double v : members.values())
            d[i++] = v;
        return operator.apply(d);
    }

    public SingletonsSet<V> alphacut(double alpha) {
        SingletonsSet<V> set = new SingletonsSet<V>();
        for (Entry<V, Double> member : members.entrySet()) {
            if (member.getValue() >= alpha)
                set.add(1.0, member.getKey());
        }
        return set;
    }

    public double sigmaCount() {
        double sum = 0.0;
        for (Double d : members.values())
            sum += d;
        return sum;
    }

    @Override
    public int hashCode() {
        return members.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("{ ");
        for (Iterator<V> it = members.keySet().iterator(); it.hasNext();) {
            V v = it.next();
            double membership = members.get(v);
            b.append(String.format("%f/%s", membership, v.toString()));
            if (it.hasNext())
                b.append(", ");
        }
        return b.append(" }").toString();
    }
}
