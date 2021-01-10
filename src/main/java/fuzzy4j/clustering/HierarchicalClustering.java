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

package fuzzy4j.clustering;

import java.util.*;

/**
 * Implements hierarchical clustering.
 *
 * @author Soren <sorend@gmail.com>
 */
public class HierarchicalClustering<V> {

    private double maximumDistance;
    private List<Set<Set<V>>> history = new ArrayList<Set<Set<V>>>();
    private Map<String, Set<V>> clusters;
    private fuzzy4j.clustering.SimilarityMeasure<V> similarityHelper;

    public HierarchicalClustering(fuzzy4j.clustering.SimilarityMeasure<V> similarityHelper, Set<V> data) {
        this(data.size(), similarityHelper, data);
    }

    public HierarchicalClustering(double maximumDistance, fuzzy4j.clustering.SimilarityMeasure<V> similarityHelper, Set<V> data) {
        this.maximumDistance = maximumDistance;
        this.similarityHelper = similarityHelper;
        this.clusters = new HashMap<String, Set<V>>();
        // initialize the clusters
        int nextID = 0;
        for (V start : data) {
            Set<V> cluster = new HashSet<V>();
            cluster.add(start);
            clusters.put(String.valueOf(nextID++), cluster);
        }
    }

    public static <V> double singleLinkageDistance(fuzzy4j.clustering.SimilarityMeasure<V> measure, Set<V> A, Set<V> B) {
        double min = Double.POSITIVE_INFINITY;
        for (V x : A) {
            for (V y : B) {
                min = Math.min(min, measure.distance(x, y));
            }
        }
        return min;
    }

    /**
     * Calculates average distance between two clusters.
     * See http://en.wikipedia.org/wiki/UPGMA
     *
     * @param A One cluster
     * @param B Another cluster
     */
    public static <V> double averageDistance(SimilarityMeasure<V> measure, Set<V> A, Set<V> B) {
       double sum = 0.0;
        for (V x : A) {
            for (V y : B) {
                sum += measure.distance(x, y);
            }
        }
        return (1.0 / (A.size() * B.size())) * sum;
    }

    public void calculate() {

        while (clusters.size() > 1) {

            // save clusters
            history.add(0, new HashSet<Set<V>>(clusters.values()));

            String fA = null, fB = null;
            double min = Double.POSITIVE_INFINITY;
            double max = Double.NEGATIVE_INFINITY;
            for (Map.Entry<String, Set<V>> entry : clusters.entrySet()) {
                String aID = entry.getKey();
                Set<V> A = entry.getValue();
                for (Map.Entry<String, Set<V>> entryB : clusters.entrySet()) {
                    String bID = entryB.getKey();
                    // aID, bID
                    if (aID.equals(bID))
                        continue;

                    Set<V> B = entryB.getValue();

                    double sim_A_B = averageDistance(similarityHelper, A, B);

                    // System.out.println("avg-dist(" + A + ", " + B + ") = " + sim_A_B);

                    if (sim_A_B < min) {
                        fA = aID;
                        fB = bID;
                        min = sim_A_B;
                    }
                    if (sim_A_B > max) {
                        max = sim_A_B;
                    }
                }
            }

            // System.out.println("distances: min=" + min + ", max=" + max + ", maximumDistance=" + maximumDistance);

            // only allow upto maximum distance
            if (min > maximumDistance)
                break;

            // merge the two most similar
            Set<V> A_B = new HashSet<V>();
            A_B.addAll(clusters.remove(fA));
            A_B.addAll(clusters.remove(fB));
            clusters.put(fA + "." + fB, A_B);
        }

        // save clusters
        history.add(0, new HashSet<Set<V>>(clusters.values()));

        // System.out.println("clusters = " + clusters);
        // System.out.println("history = " + history);
    }

    public List<Set<Set<V>>> histories() {
        return history;
    }

    public Set<Set<V>> history(int Q) {
        return history.get(Q);
    }
}
