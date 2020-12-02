package api;

import api.edge_data;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class EdgeData implements edge_data {
    private int src;
    private int dest;
    private double weight;
    private String Info;
    private int Tag;

    public EdgeData(edge_data e){
        this.src=e.getSrc();
        this.dest=e.getDest();
        this.weight=e.getWeight();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeData edgeData = (EdgeData) o;
        return src == edgeData.src &&
                dest == edgeData.dest &&
                Double.compare(edgeData.weight, weight) == 0 &&
                Tag == edgeData.Tag &&
                Objects.equals(Info, edgeData.Info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(src, dest, weight, Info, Tag);
    }

    public EdgeData(int src, int dest, double w){
        if(w>0){
            this.src=src;
            this.dest=dest;
            this.weight=w;
        }
    }

    /**
     * The id of the source node of this edge.
     *
     * @return
     */
    @Override
    public int getSrc() {
        return this.src;
    }

    /**
     * The id of the destination node of this edge
     *
     * @return
     */
    @Override
    public int getDest() {
        return this.dest;
    }

    /**
     * @return the weight of this edge (positive value).
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * Returns the remark (meta data) associated with this edge.
     *
     * @return
     */
    @Override
    public String getInfo() {
        return this.Info;
    }

    /**
     * Allows changing the remark (meta data) associated with this edge.
     *
     * @param s
     */
    @Override
    public void setInfo(String s) {
        if (s != null) {
            this.Info = s;
        }
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     *
     * @return
     */
    @Override
    public int getTag() {
        return this.Tag;
    }

    /**
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.Tag = t;
    }
}