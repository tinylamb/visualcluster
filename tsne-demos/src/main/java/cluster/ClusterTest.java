package cluster;

import java.io.File;

import smile.data.AttributeDataset;
import smile.data.parser.DelimitedTextParser;

import static cluster.BaseClustering.TEST_INIT_DATAPATH;
import static cluster.BaseClustering.TEST_INIT_LABELPATH;
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

    public static void testTsne() throws Exception {
        ClusterFactory f = ClusterFactory.getInstance();
        BaseClustering b = f.createCluster("kmeans");

        DelimitedTextParser parser = new DelimitedTextParser();
        parser.setDelimiter("[,\t ]+");
        AttributeDataset data_test = parser.parse("queryvec", new File(TEST_INIT_DATAPATH));
        double[][] initdata_test = data_test.toArray(new double[data_test.size()][]);
        int datadim_test = initdata_test[0].length;
        double[][] mapdata_test = b.tsneMapParalle(initdata_test, 2, datadim_test, 30.0, 2000);
        String[] label_test = b.readLabelFile(new File(TEST_INIT_LABELPATH));
        b.poltDataLabels(mapdata_test, label_test);
    }

    public static void testCluster() {
        String meccluster = "mec";
        ClusterFactory factory = ClusterFactory.getInstance();
        BaseClustering mec = factory.createCluster(meccluster);
        String[] label = mec.callClustering(initdata.length);
        mec.poltDataLabels(mapdata, dataid);
        mec.plotDataClusterWithLable(meccluster, mapdata, label, dataid);

        String kmeanscluster = "kmeans";
        BaseClustering kmeans = factory.createCluster(kmeanscluster);
        String[] kmeans_label = kmeans.callClustering(initdata.length / 4);
        kmeans.plotDataClusterWithLable(kmeanscluster, mapdata, kmeans_label, dataid);

        ClusterType hierarchicalcluster = ClusterType.HIERARCHICAL;
        BaseClustering hierarchical = factory.newCluster(hierarchicalcluster);
        String[] hierarchical_label = hierarchical.callClustering(initdata.length / 4);
        hierarchical.plotDataClusterWithLable(hierarchicalcluster.name(), mapdata, hierarchical_label, dataid);
    }

    public static void main(String[] args) throws Exception{
        testCluster();

    }
}
