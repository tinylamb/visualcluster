package cluster;

import java.util.ArrayList;

/**
 * Created by samo on 2017/6/26.
 *
 * @author samo
 * @date 2017/06/26
 */
public class BaseDatainfo {
    private double[][] initdata;
    private String[] dataid;

    public BaseDatainfo(double[][] initdata, String[] dataid) {
        this.initdata = initdata;
        this.dataid = dataid;
    }

    public BaseDatainfo(ArrayList<double[]> data, ArrayList<String> id) {
        double[][] datalist = data.toArray(new double[0][0]);
        String[] idlist = id.toArray(new String[0]);
        this.initdata = datalist;
        this.dataid = idlist;
    }

    public double[][] getInitdata() {
        return initdata;
    }

    public void setInitdata(double[][] initdata) {
        this.initdata = initdata;
    }

    public String[] getDataid() {
        return dataid;
    }

    public void setDataid(String[] dataid) {
        this.dataid = dataid;
    }


    @Override
    public String toString() {
        System.out.println("datasize : " + dataid.length);
        String query = dataid[0];
        double[] vec = initdata[0];
        StringBuffer sb = new StringBuffer();
        sb.append(query)
            .append("|");
        for (double d : vec) {
            sb.append(d)
                .append(" ");
        }
        return sb.toString();
    }
}
