package fuzzy4j.clustering;

/**
 * @author Soren <sorend@gmail.com>
 */
public class SimilarityMeasureHelper {

    public static SimilarityMeasure<Double[]> EUCLIDIAN = new EuclidianSimilarityMeasure();

    private static class EuclidianSimilarityMeasure implements SimilarityMeasure<Double[]> {
        @Override
        public double distance(Double[] x1, Double[] x2) {
            int p = x1.length;
            double sum = 0;

            for (int k = 0; k < p; k++) sum += (x1[k] - x2[k]) * (x1[k] - x2[k]);

            return Math.sqrt(sum);
        }
    }

}
