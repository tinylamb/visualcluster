package cluster;

import java.io.File;

import smile.data.AttributeDataset;
import smile.data.parser.DelimitedTextParser;

/**
 * Created by samo on 2017/6/23.
 *
 * @author samo
 * @date 2017/06/23
 */
public abstract class BaseClustering {

    static double perplexity = 20.0;
    private static int initial_dims = 50;
    private String clustername;
    static double[][] initdata = null;
    static String DATAPATH = "/Users/samo/Documents/githubRepo/"
        + "T-SNE-Java/tsne-demos/src/main/resources/datasets/";

    static String INIT_DATAPATH = DATAPATH + "iris_X.txt";

    public BaseClustering(String name) {
        this.clustername = name;
        if (initdata == null) {
            DelimitedTextParser parser = new DelimitedTextParser();
            parser.setDelimiter("[,\t ]+");
            try {
                AttributeDataset data = parser.parse("queryvec",
                    new File(INIT_DATAPATH));
                initdata = data.toArray(new double[data.size()][]);
                //TSneDemo.fast_tsne_no_labels(DATAPATH + "iris_X.txt");
                //printDoubleArr(initdata);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    public void printDoubleArr(double[][] arr) {
        for (double[] tmp : arr) {
            for (double d : tmp) {
                System.out.print(d + ",");
            }
            System.out.println();
        }
    }

    public void printIntArr(int[][] arr) {
        for (int[] tmp : arr) {
            for (int d : tmp) {
                System.out.print(d + ",");
            }
            System.out.println();
        }
    }


    public abstract int[] callClustering(int clustersize);

    public abstract double[][] callClusteringv2(int clustersize);
}
