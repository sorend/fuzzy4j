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

/**
 * A PriorityQueue maintains a partial ordering of its elements such that the
 * least element can always be found in constant time.  Put()'s and pop()'s
 * require log(size) time.
 * <p/>
 * <p><b>NOTE</b>: This class pre-allocates a full array of
 * length <code>maxSize+1</code>, in {@link #initialize}.
 *
 * @lucene.internal
 */
public abstract class PriorityQueue<T> {
    private int size;
    private int maxSize;
    private T[] heap;

    /**
     * Determines the ordering of objects in this priority queue.  Subclasses
     * must define this one method.
     *
     * @return <code>true</code> iff parameter <tt>a</tt> is less than parameter <tt>b</tt>.
     */
    protected abstract boolean lessThan(T a, T b);

    /**
     * This method can be overridden by extending classes to return a sentinel
     * object which will be used by {@link #initialize(int)} to fill the queue, so
     * that the code which uses that queue can always assume it's full and only
     * change the top without attempting to insert any new object.<br>
     * <p/>
     * Those sentinel values should always compare worse than any non-sentinel
     * value (i.e., {@link #lessThan} should always favor the
     * non-sentinel values).<br>
     * <p/>
     * By default, this method returns false, which means the queue will not be
     * filled with sentinel values. Otherwise, the value returned will be used to
     * pre-populate the queue. Adds sentinel values to the queue.<br>
     * <p/>
     * If this method is extended to return a non-null value, then the following
     * usage pattern is recommended:
     * <p/>
     * <pre>
     * // extends getSentinelObject() to return a non-null value.
     * PriorityQueue<MyObject> pq = new MyQueue<MyObject>(numHits);
     * // save the 'top' element, which is guaranteed to not be null.
     * MyObject pqTop = pq.top();
     * &lt;...&gt;
     * // now in order to add a new element, which is 'better' than top (after
     * // you've verified it is better), it is as simple as:
     * pqTop.change().
     * pqTop = pq.updateTop();
     * </pre>
     * <p/>
     * <b>NOTE:</b> if this method returns a non-null value, it will be called by
     * {@link #initialize(int)} {@link #size()} times, relying on a new object to
     * be returned and will not check if it's null again. Therefore you should
     * ensure any call to this method creates a new instance and behaves
     * consistently, e.g., it cannot return null if it previously returned
     * non-null.
     *
     * @return the sentinel object to use to pre-populate the queue, or null if
     *         sentinel objects are not supported.
     */
    protected T getSentinelObject() {
        return null;
    }

    /**
     * Subclass constructors must call this.
     */
    @SuppressWarnings("unchecked")
    protected final void initialize(int maxSize) {
        size = 0;
        int heapSize;
        if (0 == maxSize)
            // We allocate 1 extra to avoid if statement in top()
            heapSize = 2;
        else {
            if (maxSize == Integer.MAX_VALUE) {
                // Don't wrap heapSize to -1, in this case, which
                // causes a confusing NegativeArraySizeException.
                // Note that very likely this will simply then hit
                // an OOME, but at least that's more indicative to
                // caller that this values is too big.  We don't +1
                // in this case, but it's very unlikely in practice
                // one will actually insert this many objects into
                // the PQ:
                heapSize = Integer.MAX_VALUE;
            } else {
                // NOTE: we add +1 because all access to heap is
                // 1-based not 0-based.  heap[0] is unused.
                heapSize = maxSize + 1;
            }
        }
        heap = (T[]) new Object[heapSize]; // T is unbounded type, so this unchecked cast works always
        this.maxSize = maxSize;

        // If sentinel objects are supported, populate the queue with them
        T sentinel = getSentinelObject();
        if (sentinel != null) {
            heap[1] = sentinel;
            for (int i = 2; i < heap.length; i++) {
                heap[i] = getSentinelObject();
            }
            size = maxSize;
        }
    }

    /**
     * Adds an Object to a PriorityQueue in log(size) time. If one tries to add
     * more objects than maxSize from initialize an
     * {@link ArrayIndexOutOfBoundsException} is thrown.
     *
     * @return the new 'top' element in the queue.
     */
    public final T add(T element) {
        size++;
        heap[size] = element;
        upHeap();
        return heap[1];
    }

    /**
     * Adds an Object to a PriorityQueue in log(size) time.
     * It returns the object (if any) that was
     * dropped off the heap because it was full. This can be
     * the given parameter (in case it is smaller than the
     * full heap's minimum, and couldn't be added), or another
     * object that was previously the smallest value in the
     * heap and now has been replaced by a larger one, or null
     * if the queue wasn't yet full with maxSize elements.
     */
    public T insertWithOverflow(T element) {
        if (size < maxSize) {
            add(element);
            return null;
        } else if (size > 0 && !lessThan(element, heap[1])) {
            T ret = heap[1];
            heap[1] = element;
            updateTop();
            return ret;
        } else {
            return element;
        }
    }

    /**
     * Returns the least element of the PriorityQueue in constant time.
     */
    public final T top() {
        // We don't need to check size here: if maxSize is 0,
        // then heap is length 2 array with both entries null.
        // If size is 0 then heap[1] is already null.
        return heap[1];
    }

    /**
     * Removes and returns the least element of the PriorityQueue in log(size)
     * time.
     */
    public final T pop() {
        if (size > 0) {
            T result = heap[1];             // save first value
            heap[1] = heap[size];           // move last to first
            heap[size] = null;              // permit GC of objects
            size--;
            downHeap();                 // adjust heap
            return result;
        } else
            return null;
    }

    /**
     * Should be called when the Object at top changes values. Still log(n) worst
     * case, but it's at least twice as fast to
     * <p/>
     * <pre>
     * pq.top().change();
     * pq.updateTop();
     * </pre>
     * <p/>
     * instead of
     * <p/>
     * <pre>
     * o = pq.pop();
     * o.change();
     * pq.push(o);
     * </pre>
     *
     * @return the new 'top' element.
     */
    public final T updateTop() {
        downHeap();
        return heap[1];
    }

    /**
     * Returns the number of elements currently stored in the PriorityQueue.
     */
    public final int size() {
        return size;
    }

    /**
     * Removes all entries from the PriorityQueue.
     */
    public final void clear() {
        for (int i = 0; i <= size; i++) {
            heap[i] = null;
        }
        size = 0;
    }

    private final void upHeap() {
        int i = size;
        T node = heap[i];             // save bottom node
        int j = i >>> 1;
        while (j > 0 && lessThan(node, heap[j])) {
            heap[i] = heap[j];              // shift parents down
            i = j;
            j = j >>> 1;
        }
        heap[i] = node;               // install saved node
    }

    private final void downHeap() {
        int i = 1;
        T node = heap[i];             // save top node
        int j = i << 1;               // find smaller child
        int k = j + 1;
        if (k <= size && lessThan(heap[k], heap[j])) {
            j = k;
        }
        while (j <= size && lessThan(heap[j], node)) {
            heap[i] = heap[j];              // shift up child
            i = j;
            j = i << 1;
            k = j + 1;
            if (k <= size && lessThan(heap[k], heap[j])) {
                j = k;
            }
        }
        heap[i] = node;               // install saved node
    }

    /**
     * This method returns the internal heap array as Object[].
     *
     * @lucene.internal
     */
    protected final Object[] getHeapArray() {
        return (Object[]) heap;
    }
}