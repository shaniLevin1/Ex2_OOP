package api;

import java.util.Objects;
/**
 * this class represents a node in the graph with all it properties: id, Tag, Info, weight and location on the graph
 */

public class NodeData implements node_data{
    private int id;
    private static int Kcounter=0;
    private int Tag;
    private String Info;
    private double weight;
    private geo_location g;

    /**
     * empty constructor
     */

    public NodeData() {
        this.id=Kcounter++;
        this.Tag=0;
        this.weight=0;
        this.Info=" ";
        this.g=null; //need to check
    }

    /**
     * copy constructor by id\key
     * @param key
     */
    public NodeData(int key) {
        this.id=key;
        this.Tag=0;
        this.weight=0;
        this.Info=" ";
    }

    /**
     * deep copy constructor by a given node
     * @param a the object to be copied
     */
    public NodeData(node_data a){
        this.id=a.getKey();
        this.Tag=a.getTag();
        this.weight=a.getWeight();
        this.Info=a.getInfo();
        this.g=a.getLocation();
    }

    /**
     * checks if two elements are cloned
     * @param o the object to compare with
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeData nodeData = (NodeData) o;
        return id == nodeData.id &&
                Tag == nodeData.Tag &&
                Double.compare(nodeData.weight, weight) == 0 &&
                Objects.equals(Info, nodeData.Info) &&
                Objects.equals(g, nodeData.g);
    }

    /**
     *
     * @return the hashcode of a given object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, Tag, Info, weight);
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
     * none returns null.
     *
     * @return
     */
    @Override
    public geo_location getLocation() {
        return g;
    }

    /**
     * Allows changing this node's location.
     *
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {
        g=p;
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
     * @param s the object to set it's info
     */
    @Override
    public void setInfo(String s) {
        this.Info=s;
    }

    /**
     *returns the tag of the current node
     * @return tag
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

}
