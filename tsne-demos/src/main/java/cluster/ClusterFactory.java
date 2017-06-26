package cluster;

/**
 * Created by samo on 2017/6/26.
 *
 * @author samo
 * @date 2017/06/26
 */
public class ClusterFactory {
    private static ClusterFactory ourInstance = new ClusterFactory();

    public static ClusterFactory getInstance() {
        return ourInstance;
    }

    private ClusterFactory() {
    }

    public BaseClustering createCluster(final String cluster) {
        if (cluster.equals("kmeans")) {
            return new KmeansCluster(cluster);
        } else if (cluster.equals("hierarchical")) {
            return new HierarchicalCluster(cluster);
        } else if (cluster.equals("mec")) {
            return new MecCluster(cluster);
        }
        return null;
    }

    public void testFactory() {
        System.out.println("testFactory");
    }

}
