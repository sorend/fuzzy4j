package fuzzy4j.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * @author Soren <sorend@gmail.com>
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
                    ", i=" + w +
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
