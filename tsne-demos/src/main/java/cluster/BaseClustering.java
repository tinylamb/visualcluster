package cluster;

import java.awt.*;
import java.io.File;

import javax.swing.*;

import com.jujutsu.tsne.TSne;
import com.jujutsu.tsne.barneshut.BHTSne;
import com.jujutsu.tsne.barneshut.TSneConfiguration;
import com.jujutsu.utils.TSneUtils;
import org.math.plot.FrameView;
import org.math.plot.Plot2DPanel;
import org.math.plot.plots.ColoredScatterPlot;
import org.math.plot.plots.ScatterPlot;
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
    static double[][] mapdata = null;
    static int initdim = 0;
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
                initdim = initdata[0].length;
                //TSneDemo.fast_tsne_no_labels(DATAPATH + "iris_X.txt");
                //printDoubleArr(initdata);
                mapdata = tsneMap(initdata, 2, initdim, 20.0, 1000);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    public double[][] tsneMap(double[][] data, int outputDims,
                              int initDims, double perplexity, int iters) {
        TSne tsne = new BHTSne();
        TSneConfiguration config = TSneUtils.buildConfig(data, outputDims,
            initDims, perplexity, iters);
        return tsne.tsne(config);
    }

    public void poltDataLabels(double[][] data, String[] labels) {
        Plot2DPanel plot = new Plot2DPanel();
        ColoredScatterPlot setosaPlot = new ColoredScatterPlot("setosaPlot", data, labels);
        setosaPlot.setTags(labels);
        plot.plotCanvas.setNotable(true);
        plot.plotCanvas.setNoteCoords(true);
        plot.plotCanvas.addPlot(setosaPlot);
        FrameView plotframe = new FrameView(plot);
        plotframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        plotframe.setVisible(true);
    }

    public void poltData(double[][] data) {
        Plot2DPanel plot = new Plot2DPanel();

        ScatterPlot setosaPlot = new ScatterPlot("polt", Color.BLACK, data);
        plot.plotCanvas.setNotable(true);
        plot.plotCanvas.setNoteCoords(true);
        plot.plotCanvas.addPlot(setosaPlot);

        FrameView plotframe = new FrameView(plot);
        plotframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        plotframe.setVisible(true);
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

    public abstract String[] callClusteringv3(int clustersize);
}
