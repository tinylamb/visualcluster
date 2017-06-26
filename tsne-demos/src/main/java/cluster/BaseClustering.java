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

    /**
     * clustername 聚类方法
     */
    private String clustername;

    /**
     * 数据初始化
     */
    static double[][] initdata = null;
    static double[][] mapdata = null;
    static String[] dataid = null;
    static int datadim = 0;

    /**
     * 数据路径
     */
    static String DATAPATH = "/Users/samo/Documents/githubRepo/"
        + "T-SNE-Java/tsne-demos/src/main/resources/datasets/";
    private static String[] datasource = {
        "mnist250_X.txt",
        "iris_X.txt",
        "MNist_2500.txt"
    };
    static String INIT_DATAPATH = DATAPATH + datasource[1];


    public BaseClustering(String name) {
        this.clustername = name;
        if (initdata == null) {
            DelimitedTextParser parser = new DelimitedTextParser();
            parser.setDelimiter("[,\t ]+");
            try {
                AttributeDataset data = parser.parse("queryvec",
                    new File(INIT_DATAPATH));
                initdata = data.toArray(new double[data.size()][]);
                //printDoubleArr(initdata);
                datadim = initdata[0].length;
                mapdata = tsneMap(initdata, 2, datadim, 20.0, 1000);
                dataid = new String[mapdata.length];
                for (int i = 0; i < mapdata.length; i++) {
                    dataid[i] = String.valueOf(i) + "你好";
                }

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
        ColoredScatterPlot setosaPlot = new ColoredScatterPlot("setosaPlot", data, labels);
        setosaPlot.setTags(labels);
        Plot2DPanel plot = new Plot2DPanel();
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

    public String[] convertArr(int[] label) {
        String[] slabel = new String[label.length];
        for (int i = 0; i < label.length; i++) {
            slabel[i] = String.valueOf(label[i]);
        }
        return slabel;
    }


    public abstract String[] callClustering(int clustersize);

}
