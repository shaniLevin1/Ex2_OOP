package api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;


public class DS_DWGraph implements directed_weighted_graph {
    private HashMap<Integer, node_data> Vmap;
    private HashMap<Integer, HashMap<Integer, edge_data>> SEmap;
    private HashMap<Integer, HashMap<Integer, edge_data>> DEmap;
    private int Ecounter;
    private int MCcounter;

    /**
     * checks if two graphs are equal
     * @param o the graph to compare with
     * @return true if equals , else returns false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DS_DWGraph that = (DS_DWGraph) o;
        return Ecounter == that.Ecounter &&
                Objects.equals(Vmap, that.Vmap) &&
                Objects.equals(SEmap, that.SEmap) &&
                Objects.equals(DEmap, that.DEmap);
    }

    /**
     * returns the hashcode of a required object
     * @return the hashcode of a required object
     */
    @Override
    public int hashCode() {
        return Objects.hash(Vmap, SEmap, DEmap, Ecounter, MCcounter);
    }

    /**
     * empty constructor for graph
     */
    public DS_DWGraph() {
        Vmap = new HashMap<Integer, node_data>();
        SEmap = new HashMap<Integer, HashMap<Integer, edge_data>>();
        DEmap = new HashMap<Integer, HashMap<Integer, edge_data>>();
        Ecounter = 0;
        MCcounter = 0;
    }

    /**
     * deep copy constructor for graph
     * @param d the graph to be copied
     */

    public DS_DWGraph(directed_weighted_graph d) {
        this.Ecounter = d.edgeSize();
        Vmap = new HashMap<Integer, node_data>();
        SEmap = new HashMap<Integer, HashMap<Integer, edge_data>>();
        DEmap = new HashMap<Integer, HashMap<Integer, edge_data>>();
        for (node_data n : d.getV()) {
            node_data x = new NodeData(n);
            Vmap.put(n.getKey(), x);
            HashMap<Integer, edge_data> Emap1 = new HashMap<Integer, edge_data>();
            HashMap<Integer, edge_data> Emap2 = new HashMap<Integer, edge_data>();
            SEmap.put(n.getKey(), Emap1);
            DEmap.put(n.getKey(), Emap2);
            for (edge_data x1 : d.getE(n.getKey())) {
                edge_data x2 = new EdgeData(x1);
                Emap1.put(x2.getDest(), x1);
                Emap2.put(x2.getSrc(), x1);
            }
        }
    }

    /**
     * returns the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key) {
        return Vmap.get(key);
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     *
     * @param src edge source
     * @param dest edge destination
     * @return the required edge
     */

    @Override
    public edge_data getEdge(int src, int dest) {
        if (Vmap.containsKey(src) && Vmap.containsKey(dest)) {
            if (SEmap.get(src).containsKey(dest)) {
                return SEmap.get(src).get(dest);
            }
        }
        return null;
    }

    /**
     * adds a new node to the graph with the given node_data.
     *
     * @param n the node to be added
     */

    @Override
    public void addNode(node_data n) {
        if (n != null) {
            Vmap.put(n.getKey(), n);
            HashMap<Integer, edge_data> Emap1 = new HashMap<Integer, edge_data>();
            HashMap<Integer, edge_data> Emap2 = new HashMap<Integer, edge_data>();
            SEmap.put(n.getKey(), Emap1);
            DEmap.put(n.getKey(), Emap2);
            MCcounter++;
        }
    }

    /**
     * Connects an edge with weight w between node src to node dest.
     *
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the distance  between src-->dest.
     */

    @Override
    public void connect(int src, int dest, double w) {
        if (Vmap.containsKey(src) && Vmap.containsKey(dest)) {
            edge_data e = new EdgeData(src, dest, w);
            SEmap.get(src).put(dest, e);
            DEmap.get(dest).put(src, e);
            MCcounter++;
            if (getEdge(src, dest) != null) { // need to check on tester.
                Ecounter++;
            }
        }
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_data> getV() {
        if (Vmap != null) {
            return Vmap.values();
        }
        return null;
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the edges getting out of
     * the given node (all the edges starting (source) at the given node).
     *
     * @param node_id the node to return its out-coming edges
     * @return Collection<edge_data> of the required node :its  out-coming edges
     */

    @Override
    public Collection<edge_data> getE(int node_id) {
        if (Vmap.containsKey(node_id)) {
            return SEmap.get(node_id).values();
        }
        return null;
    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     *
     * @param key the node to remove
     * @return the removed node (null if none).
     */

    @Override
    public node_data removeNode(int key) {
        if (Vmap != null && Vmap.containsKey(key)) {
            for(HashMap<Integer,edge_data> e:SEmap.values()){
                   e.remove(key);
            }
            for(HashMap<Integer,edge_data> e:DEmap.values()){
                e.remove(key);
            }
            SEmap.remove(key);
            DEmap.remove(key);
            MCcounter++;
            return Vmap.remove(key);
        }
        return null;
    }

    /**
     * Deletes required edge from the graph,
     *
     * @param src edge srd
     * @param dest edge dest
     * @return the data of the removed edge (null if none).
     */

    @Override
    public edge_data removeEdge(int src, int dest) {
        if (getEdge(src, dest) != null) {
            SEmap.get(src).remove(dest);
            MCcounter++;
            Ecounter--;
            return DEmap.get(dest).remove(src);

        }
        return null;
    }

    /**
     * Returns the number of vertices (nodes) in the graph.
     *
     * @return number of vertices
     */
    @Override
    public int nodeSize() {
        return Vmap.size();
    }

    /**
     * Returns the number of edges
     *
     * @return edge amount
     */
    @Override
    public int edgeSize() {
        return Ecounter;
    }

    /**
     * returns collection of the out-going edges associated with a required node data
     * @param node_id the required node to return its collection
     * @return collection of the out-going edges associated with a required node data
     */
    public Collection<edge_data> getInE(int node_id) { //to remove
        return DEmap.get(node_id).values(); //This is the outgoing edge_data collection for 'node_id'.
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     *
     * @return the amount of changes in the graph
     */
    @Override
    public int getMC() {
        return MCcounter;
    }

    /**
     * returns string performance for the current graph
     * @return string performance for the current graph
     */
    @Override
    public String toString() {
        String str = "";
        for (Integer x : Vmap.keySet()) {
            str += "" + x + " --> out [";
            for (edge_data i : getE(x)) {
                str += i.getDest() + " (" + SEmap.get(x).get(i.getDest()).getWeight() + ") , ";//EdgeMap.get(x).keySet().toString() + " \n ";
            }
            str += "] \n";
            str += "" + x + " --> in [";
            for (edge_data i : getInE(x)) {
                str += i.getSrc() + " (" + DEmap.get(x).get(i.getSrc()).getWeight() + ") , ";//EdgeMap.get(x).keySet().toString() + " \n ";
            }
            str += "] \n";
        }
        return str + " ";
    }
}

