package api;

import org.w3c.dom.Node;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class NodeData implements node_data{
    private int id;
    private static int Kcounter;
    private int Tag;
    private String Info;
    private double weight;

    public NodeData() {
        this.id=Kcounter++;
        this.Tag=0;
        this.weight=0;
        this.Info=" ";
    }

    public NodeData(node_data a){
        this.id=a.getKey();
        this.Tag=a.getTag();
        this.weight=a.getWeight();
        this.Info=a.getInfo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeData nodeData = (NodeData) o;
        return id == nodeData.id &&
                Tag == nodeData.Tag &&
                Double.compare(nodeData.weight, weight) == 0 &&
                Objects.equals(Info, nodeData.Info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Tag, Info, weight);
    }

    public NodeData(int key){
        this.id=key;
    }

    /**
     * Returns the key (id) associated with this node.
     *
     * @return
     */

    @Override
    public int getKey() {
        return this.id;
    }

    /**
     * Returns the location of this node, if
     * none return null.
     *
     * @return
     */
    @Override
    public geo_location getLocation() {
        return null;
    }

    /**
     * Allows changing this node's location.
     *
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {

    }

    /**
     * Returns the weight associated with this node.
     *
     * @return
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * Allows changing this node's weight.
     *
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        this.weight=w;
    }

    /**
     * Returns the remark (meta data) associated with this node.
     *
     * @return
     */
    @Override
    public String getInfo() {
        return Info;
    }

    /**
     * Allows changing the remark (meta data) associated with this node.
     *
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.Info=s;
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
     * Allows setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.Tag=t;
    }

    @Override
    public String toString() {
        return "" + id;
    }
}
