package cluster;

import static cluster.BaseClustering.dataid;
import static cluster.BaseClustering.mapdata;

/**
 * Created by samo on 2017/6/23.
 *
 * @author samo
 * @date 2017/06/23
 */
public class ClusterTest {

    public static void main(String[] args) {
        BaseClustering cluster = new KmeansCluster("kmeans");
        //BaseClustering cluster = new HierarchicalCluster("hierarchical");
        String[] label = cluster.callClustering(mapdata.length/4);
        cluster.poltDataLabels(mapdata, dataid);
        cluster.poltDataLabels(mapdata, label);

    }
}
