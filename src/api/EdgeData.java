package api;

import java.util.Objects;

public class EdgeData implements edge_data {
    private int src;
    private int dest;
    private double weight;
    private String Info;
    private int Tag;

    /**
     * copy constructor for edge data
     * @param e the edge to copy
     */
    public EdgeData(edge_data e){
        this.src=e.getSrc();
        this.dest=e.getDest();
        this.weight=e.getWeight();
    }

    /**
     * checks if two objects are equals
     * @param o the object to compare with
     * @return true if equals, else returns false
     */

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

    /**
     * returns the hashcode of a specific edge data object
     * @return the hashcode of a specific edge data object
     */
    @Override
    public int hashCode() {
        return Objects.hash(src, dest, weight, Info, Tag);
    }

    /**
     * constructor for edge data by src, dest and weight
     * @param src edge source
     * @param dest edge destination
     * @param w edge weight
     */
    public EdgeData(int src, int dest, double w){
        if(w>0){
            this.src=src;
            this.dest=dest;
            this.weight=w;
        }
    }

    /**
     * returns the id of the source node of the current edge
     * @return the id of the source node of the current edge
     */
    @Override
    public int getSrc() {
        return this.src;
    }

    /**
     * returns the id of the destination node of this edge
     * @return the id of the destination node of this edge
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
     * @return the remark (meta data) associated with this edge.
     */
    @Override
    public String getInfo() {
        return this.Info;
    }

    /**
     * Allows changing the remark (meta data) associated with this edge.
     *
     * @param s the information to set
     */
    @Override
    public void setInfo(String s) {
        if (s != null) {
            this.Info = s;
        }
    }

    /**
     * Temporal data which can be used be algorithms
     * @return tag situation of the edge
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