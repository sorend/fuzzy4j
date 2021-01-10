package fuzzy4j.clustering;

import java.util.Vector;

/**
 * @author Soren <sorend@gmail.com>
 */
public class FuzzyCMeans {

    public static void main(String[] args) {
        new FuzzyCMeans(SimilarityMeasureHelper.EUCLIDIAN);
    }

    public class DataPoint {
        String name;
        double[] vs;
        public DataPoint(String n, double... a) {
            name = n;
            this.vs = a;
        }
        public double[] get() {
            return vs;
        }
        public double get(int i) {
            return vs[i];
        }
    }

    private SimilarityMeasure similarityHelper;

    public FuzzyCMeans(SimilarityMeasure similarityHelper) {
        this.similarityHelper = similarityHelper;

        Vector<DataPoint> dataSet = new Vector<DataPoint>();
        dataSet.add(new DataPoint("x1", 2, 9, 2));
        dataSet.add(new DataPoint("x2", 3, 8, 3));
        dataSet.add(new DataPoint("x3", 1, 7, 4));
        dataSet.add(new DataPoint("x4", 8, 4, 5));
        dataSet.add(new DataPoint("x5", 9, 2, 6));
        dataSet.add(new DataPoint("x6", 7, 2, 7));

        int c = 3; //The desired number of clusters

        //tuning parameter: "The greater the parameter m, the smaller the contribution
        //of feature vectors with _low_ apply degree in the cluster." (HLL)
        double m = 2;

        double[][] v = computeFuzzyCMeans(dataSet, c, m);

        // System.out.println("\nThe co-ordinates of the centers of the " + c + " clusters in the " + dataSet.get(0).vs.length + "-dimensional feature space.");

        // for (int i = 0; i < c; i++)
        //     System.out.printf("c%d=(%.2f, %.2f)\n", i, v[i][0], v[i][1]);

        double[] a = new double[1];
        double[] b = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            a[i] = 1.0;
            b[i] = 0.0;
        }

        // System.out.println("dist(a, b) = " + similarityHelper.distance(a, b));
    }


    /**
     * Method for computing the optimal co-ordinates of a desired number of cluster and the objects degrees of apply in those.
     *
     * @param dataSet A list of element's data points in an n-dimensional feature space.
     * @param c       The desired number of clusters.
     * @param m       Tuning parameter.
     * @return The co-ordinates of the c clusters created.
     */
    private double[][] computeFuzzyCMeans(Vector<DataPoint> dataSet, int c, double m) {
        //Initialization
        int n = dataSet.size(); //the number of objects
        int f = dataSet.get(0).vs.length;//the number of features each object persists
        double[][] v = new double[c][f]; //the cluster center's co-ordinates in the n-dimensional feature space
        double[][] P = new double[n][2]; //current partition
        double[][] Pmark = new double[n][2]; //previous partition

        //Initialize the partition P with random values such that column sums are 1.
        for (int i = 0; i < P.length; i++) {
            P[i][0] = Math.random();
            P[i][1] = 1 - P[i][0];
        }

        int t = 0; //number of iterations
        while (distPartitions(P, Pmark) > 0.001) //The epsilon threshold of the stop criterion
        {
            //Compute the center vectors
            for (int i = 0; i < c; i++)
                for (int j = 0; j < f; j++) //The number of features=the number of elements in the center's coordinate.
                    v[i][j] = computeV(dataSet, P, i, j, m);

            Pmark = P;
            t++;

            // System.out.println("\nPartitioning after the " + t + ". iteration:");
            printPartion(dataSet, P);

            // P = new double[n][2];
            P = new double[n][c];

            for (int k = 0; k < n; k++) //for all objects
                for (int i = 0; i < c; i++) //for all clusters
                    P[k][i] = updateMembershipDegree(dataSet, v, i, k, m);
        }

        return v;
    }


    /**
     * Implementation of the algorithm for computing the initial value of a cluster center
     *
     * @param dataSet DataSet A list of element's data points in an n-dimensional feature space.
     * @param P       Partition
     * @param i       Cluster number
     * @param j       The jth entry of the cluster
     * @param m       Tuning parameter--frequently set to 2 according to HLL's note.
     * @return A double containing a center coordinate of the cluster V_c.
     */
    private double computeV(Vector<DataPoint> dataSet, double[][] P, int i, int j, double m) {
        int n = P.length;
        int c = P[0].length;

        double nominator = 0, denominator = 0, x;
        for (int k = 0; k < n; k++) {
            x = dataSet.get(k).get(j); //!
            nominator += Math.pow(P[k][i], m) * x;
            denominator += Math.pow(P[k][i], m);
        }

        return nominator / denominator;
    }


    /**
     * Method for computing the distance between two partitions
     *
     * @param P     The first partition.
     * @param Pmark The second partition.
     * @return A double value reflecting the distance between the two partitions (0 if identical).
     */
    private double distPartitions(double[][] P, double[][] Pmark) {
        double max = 0, diff;

        int n = P.length;//the number of objects
        int f = P[0].length;//the number of features

        for (int i = 0; i < n; i++)
            for (int j = 0; j < f; j++) {
                diff = Math.abs(P[i][j] - Pmark[i][j]);
                if (diff > max)
                    max = diff;
            }

        return max;
    }


    /**
     * Method for updating a objects k's apply degree in the ith cluster
     *
     * @param V clusters
     * @param i The cluster of interest's ID
     * @param k The object of interest
     * @return The k. objects degree of apply in the ith cluster.
     */
    private double updateMembershipDegree(Vector<DataPoint> dataSet, double[][] V, int i, int k, double m) {
        double sum = 0;
        int c = V.length;//the number of clusters
        double[] x_k = dataSet.get(k).get();

        for (int j = 0; j < c; j++) {
            sum += Math.pow(similarityHelper.distance(x_k, V[i]) / similarityHelper.distance(x_k, V[j]), 2 / (m - 1));
        }

        return 1 / sum;
    }

    /**
     * Method for printing a partition to the console.
     */
    private void printPartion(Vector<DataPoint> dataSet, double[][] P) {
        int n = P.length;
        int f = P[0].length;

        System.out.print("feat./obj.");
        for (int i = 0; i < n; i++)
            System.out.print("\t" + dataSet.get(i).name);

        // System.out.println("");

        for (int j = 0; j < f; j++) {
            // System.out.print("feature#" + j);

            for (int i = 0; i < n; i++)
                System.out.printf("\t%.2f", P[i][j]);

            // System.out.println("");
        }
    }
}
