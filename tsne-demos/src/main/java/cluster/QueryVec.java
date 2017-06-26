package cluster;

import java.util.ArrayList;

/**
 * Created by samo on 2017/6/26.
 *
 * @author samo
 * @date 2017/06/26
 */
public class QueryVec {

    private ArrayList<double[]> vec;
    private ArrayList<String> tag;

    public QueryVec(ArrayList<double[]> vec, ArrayList<String> tag) {
        this.vec = vec;
        this.tag = tag;
    }

    public ArrayList<double[]> getVec() {
        return vec;
    }

    public void setVec(ArrayList<double[]> vec) {
        this.vec = vec;
    }

    public ArrayList<String> getTag() {
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }
}
