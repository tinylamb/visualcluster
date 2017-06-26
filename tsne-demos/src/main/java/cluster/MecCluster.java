package cluster;

import smile.clustering.MEC;
import smile.math.distance.EuclideanDistance;

/**
 * Created by samo on 2017/6/26.
 *
 * @author samo
 * @date 2017/06/26
 */
public class MecCluster extends BaseClustering{

    public MecCluster(String name) {
        super(name);
    }

    @Override
    public String[] callClustering(int clustersize) {
        MEC<double[]> mec = new MEC<double[]>(initdata,
            new EuclideanDistance(), clustersize, 0.5);
        int[] label = mec.getClusterLabel();
        System.out.println("cluster num : " + mec.getNumClusters());
        return convertArr(label);
    }
}
