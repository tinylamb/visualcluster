package cluster;

import static cluster.BaseClustering.dataid;
import static cluster.BaseClustering.initdata;
import static cluster.BaseClustering.mapdata;

/**
 * Created by samo on 2017/6/23.
 *
 * @author samo
 * @date 2017/06/23
 */
public class ClusterTest {

    public static void main(String[] args) {
        ClusterFactory factory = ClusterFactory.getInstance();
        //BaseClustering cluster = new KmeansCluster("kmeans");
        BaseClustering cluster = factory.createCluster("mec");
        //BaseClustering cluster = new HierarchicalCluster("hierarchical");
        String[] label = cluster.callClustering(initdata.length);
        cluster.poltDataLabels(mapdata, dataid);
        //cluster.poltDataLabelsVisual(mapdata, label);
        cluster.plotDataClusterWithLable(mapdata, label, dataid);
        //cluster.poltDataLabels(mapdata, label);

    }
}
