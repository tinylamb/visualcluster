package cluster;

import smile.clustering.HierarchicalClustering;
import smile.clustering.linkage.WardLinkage;
import smile.math.Math;

/**
 * Created by samo on 2017/6/24.
 *
 * @author samo
 * @date 2017/06/24
 */
public class HierarchicalCluster extends BaseClustering {
    public HierarchicalCluster(String name) {
        super(name);
    }

    @Override
    public String[] callClustering(int clustersize) {
        double[][] proximity = new double[initdata.length][];
        for (int i = 0; i < initdata.length; i++) {
            proximity[i] = new double[i + 1];
            for (int j = 0; j < i; j++) {
                proximity[i][j] = Math.distance(initdata[i], initdata[j]);
            }
        }
        HierarchicalClustering hac = new HierarchicalClustering(new WardLinkage(proximity));
        int[] label = hac.partition(clustersize);
        return convertArr(label);
    }
}
