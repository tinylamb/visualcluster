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
    public String[] callClustering(int clustersize) {
        KMeans kmeans = new KMeans(initdata, clustersize);
        int[] label = kmeans.getClusterLabel();
        return convertArr(label);
    }
}
