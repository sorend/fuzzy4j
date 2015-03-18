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

package fuzzy4j.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * @author Soren <soren@tanesha.net>
 */
public class LimitedTreeSetTest {

    @Test
    public void testAdd() throws Exception {

        LimitedTreeSet impl = new LimitedTreeSet(2);

        impl.add(1);
        impl.add(2);
        assertEquals(2, impl.size());

        impl.add(3);
        assertEquals(2, impl.size());

        assertEquals(1, impl.first());
        assertEquals(2, impl.last());

        // add something in front and watch it update.
        impl.add(0);
        assertEquals(2, impl.size());

        assertEquals(0, impl.first());
        assertEquals(1, impl.last());

        assertFalse(impl.add(0));
    }


    @Test
    public void testAdd_ReverseSimilarities() throws Exception {

        LimitedTreeSet impl = new LimitedTreeSet(2);

        TestSim zer = new TestSim(0);
        TestSim one = new TestSim(1);
        TestSim two = new TestSim(2);
        TestSim thr = new TestSim(3);

        impl.add(one);
        impl.add(two);
        assertEquals(2, impl.size());

        assertEquals(two, impl.first());
        assertEquals(one, impl.last());

        impl.add(thr);
        assertEquals(2, impl.size());

        assertEquals(thr, impl.first());
        assertEquals(two, impl.last());

    }

    /**
     * this is not valid test, breaks the Set contract
    @Test
    public void testAdd_SimilarityAndEquality() throws Exception {

        LimitedTreeSet impl = new LimitedTreeSet(2);


        TestSimEquality o_o = new TestSimEquality("1", 2);
        TestSimEquality t_t = new TestSimEquality("2", 1);
        TestSimEquality o_t = new TestSimEquality("1", 3);

        impl.add(o_o);
        impl.add(t_t);

        assertEquals(2, impl.size());

        System.out.println("impl.1 = " + impl);

        impl.add(o_t);

        assertEquals(2, impl.size());

        System.out.println("impl.2 = " + impl);

        assertTrue(impl.contains(t_t));
        assertTrue(impl.contains(o_t));

    }
     */

    public class TestSimEquality implements Comparable<TestSimEquality> {
        public String v;
        public double w;

        public TestSimEquality(String v, double w) {
            this.v = v;
            this.w = w;
        }

        @Override
        public String toString() {
            return "TestSimEquality{" +
                    "v='" + v + '\'' +
                    ", w=" + w +
                    '}';
        }

        @Override
        public int compareTo(TestSimEquality o) {
            return Double.compare(o.w, this.w);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestSimEquality that = (TestSimEquality) o;

            if (!v.equals(that.v)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return v.hashCode();
        }
    }

    public class TestSim implements Comparable<TestSim> {
        double v;
        public TestSim(double v) { this.v = v;}
        public int compareTo(TestSim o) { return Double.compare(o.v, v); }
    }

}
