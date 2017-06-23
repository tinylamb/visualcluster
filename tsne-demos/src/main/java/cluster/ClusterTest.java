package cluster;

import java.io.File;

import com.jujutsu.tsne.demos.TSneDemo;
import com.jujutsu.utils.MatrixOps;

import static com.jujutsu.tsne.demos.TSneDemo.saveFile;

/**
 * Created by samo on 2017/6/23.
 *
 * @author samo
 * @date 2017/06/23
 */
public class ClusterTest {

    public static void main(String[] args) {
        BaseClustering cluster = new KmeansCluster("kmeans");
        double[][] result = cluster.callClusteringv2(2);
        saveFile(new File(BaseClustering.DATAPATH + "kmeansresult.txt"),
            MatrixOps.doubleArrayToString(result));
        TSneDemo.fast_tsne(BaseClustering.INIT_DATAPATH, BaseClustering.DATAPATH + "kmeansresult.txt");

    }
}
