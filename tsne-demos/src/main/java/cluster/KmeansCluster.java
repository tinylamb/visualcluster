package cluster;

import smile.clustering.KMeans;

/**
 * Created by samo on 2017/6/23.
 *
 * @author samo
 * @date 2017/06/23
 */
public class KmeansCluster extends BaseClustering {

    public KmeansCluster(String clustername) {
        super(clustername);
    }


    @Override
    public int[] callClustering(int clustersize) {
        KMeans kmeans = new KMeans(initdata, clustersize);
        return kmeans.getClusterLabel();
    }

    @Override
    public double[][] callClusteringv2(int clustersize) {
        int[] label = callClustering(clustersize);
        double[][] result = new double[label.length][];
        for (int i = 0; i < result.length; i++) {
            result[i] = new double[]{label[i]};
        }
        return result;
    }
}
