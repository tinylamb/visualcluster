package cluster;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.*;

import com.jujutsu.tsne.TSne;
import com.jujutsu.tsne.barneshut.BHTSne;
import com.jujutsu.tsne.barneshut.ParallelBHTsne;
import com.jujutsu.tsne.barneshut.TSneConfiguration;
import com.jujutsu.utils.TSneUtils;
import org.math.plot.FrameView;
import org.math.plot.Plot2DPanel;
import org.math.plot.PlotPanel;
import org.math.plot.plots.ColoredScatterPlot;
import org.math.plot.plots.ScatterPlot;

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
    static double[][] testdata = null;
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
        "MNist_2500.txt",
        "queryvec.txt",
        "iris_X_labels.txt",
        "MNist_2500.txt",
        "mnist2500_labels.txt"
    };
    static String INIT_DATAPATH = DATAPATH + datasource[3];
    static String TEST_INIT_DATAPATH = DATAPATH + datasource[1];
    static String TEST_INIT_LABELPATH = DATAPATH + datasource[4];


    public BaseClustering(String name) {
        this.clustername = name;
        if (initdata == null) {
            try {
                BaseDatainfo base = initDatainfo(new File(INIT_DATAPATH));
                System.out.println(base);
                initdata = base.getInitdata();
                datadim = initdata[0].length;
                mapdata = tsneMapParalle(initdata, 2, datadim, 30.0, 2000);
                dataid = base.getDataid();


            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    public String[] readLabelFile(File file) {
        try {
            ArrayList<String> id = new ArrayList<String>();
            FileInputStream stream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                String[] field = line.split("[,\t ]+", 0);
                if (field.length != 1) {
                    continue;
                }
                id.add(field[0]);
            }
            return id.toArray(new String[0]);

        } catch (Exception e) {
            return null;
        }
    }

    public BaseDatainfo initDatainfo(File file) {
        try {
            ArrayList<double[]> data = new ArrayList<double[]>();
            ArrayList<String> id = new ArrayList<String>();
            //FileInputStream stream = new FileInputStream(file);
            FileReader r = new FileReader(file);
            //BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            BufferedReader reader = new BufferedReader(r);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                String[] field = line.split("[,\t ]+", 0);
                if (field.length != 65) {
                    continue;
                }
                if (Math.random() > 0.2) {
                    continue;
                }
                String query = field[0];
                double[] vec = new double[64];
                for (int i = 0; i < vec.length; i++) {
                    String tmp = new DecimalFormat("0.000000")
                        .format(Double.valueOf(field[i + 1]));
                    vec[i] = Double.valueOf(tmp);
                }
                id.add(query);
                data.add(vec);
            }
            return new BaseDatainfo(data, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public double[][] tsneMap(double[][] data, int outputDims,
                              int initDims, double perplexity, int iters) {
        TSne tsne = new BHTSne();
        TSneConfiguration config = TSneUtils.buildConfig(data, outputDims,
            initDims, perplexity, iters);
        return tsne.tsne(config);
    }

    public double[][] tsneMapParalle(double[][] data, int outputDims,
                                int initDims, double perplexity, int iters) {
        TSne tsne = new ParallelBHTsne();
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

    public void plotDataClusterWithLable(final String title, double[][] data, String[] labels, String[] tags) {
        HashMap<String, QueryVec> cluster = new HashMap<String, QueryVec>();
        for (int i = 0; i < data.length; i++) {
            if (!cluster.containsKey(labels[i])) {
                ArrayList<double[]> v = new ArrayList<double[]>();
                v.add(data[i]);
                ArrayList<String> t = new ArrayList<String>();
                t.add(tags[i]);
                QueryVec qv = new QueryVec(v, t);
                cluster.put(labels[i], qv);
            } else {
                cluster.get(labels[i]).getVec().add(data[i]);
                cluster.get(labels[i]).getTag().add(tags[i]);
            }
        }
        Plot2DPanel plot = new Plot2DPanel();
        ArrayList<ArrayList<String>> taginfo = new ArrayList<ArrayList<String>>();
        for (Entry<String, QueryVec> tmp : cluster.entrySet()) {
            String cname = tmp.getKey();
            double[][] datav = tmp.getValue().getVec().toArray(new double[0][0]);
            plot.addScatterPlot(cname, datav);
            taginfo.add(tmp.getValue().getTag());
        }
        for (int i = 0; i < taginfo.size(); i++) {
            String[] tmptags = taginfo.get(i).toArray(new String[0]);
            ((ScatterPlot) plot.getPlot(i)).setTags(tmptags);
        }
        plot.setLegendOrientation(PlotPanel.SOUTH);
        plot.plotCanvas.setNotable(true);
        plot.plotCanvas.setNoteCoords(true);
        FrameView plotframe = new FrameView(plot);
        plotframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        plotframe.setTitle(title);
        plotframe.setVisible(true);
    }

    public void poltDataLabelsVisual(double[][] data, String[] labels) {
        //HashMap<String, double[][]> v = new HashMap<String, double[][]>();
        HashMap<String, ArrayList<double[]>> cluster =
            new HashMap<String, ArrayList<double[]>>();
        for (int i = 0; i < data.length; i++) {
            if (!cluster.containsKey(labels[i])) {
                ArrayList<double[]> v = new ArrayList<double[]>();
                v.add(data[i]);
                cluster.put(labels[i], v);
            } else {
                cluster.get(labels[i]).add(data[i]);
            }
        }
        //System.out.println("keyset : " + cluster.keySet());

        //ColoredScatterPlot setosaPlot = new ColoredScatterPlot("setosaPlot", data, labels);
        //setosaPlot.setTags(labels);
        Plot2DPanel plot = new Plot2DPanel();
        for (Entry<String, ArrayList<double[]>> tmp : cluster.entrySet()) {
            String cname = tmp.getKey();
            double[][] datav = tmp.getValue().toArray(new double[0][0]);
            plot.addScatterPlot(cname, datav);
        }
        plot.setLegendOrientation(PlotPanel.SOUTH);

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
