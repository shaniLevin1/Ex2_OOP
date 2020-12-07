package api;

import org.w3c.dom.Node;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

// NOTE: YPU SHOULD ADD EQUAL TO THIS CLASS AND FOR ALL THE CLASS BY THE WAY.
// GENERATE EQUALS.
public class DS_DWGraph implements directed_weighted_graph {
    private HashMap<Integer, node_data> Vmap;
    private HashMap<Integer, HashMap<Integer, edge_data>> SEmap;
    private HashMap<Integer, HashMap<Integer, edge_data>> DEmap;
    private int Ecounter;
    private int MCcounter;

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

    @Override
    public int hashCode() {
        return Objects.hash(Vmap, SEmap, DEmap, Ecounter, MCcounter);
    }

    public DS_DWGraph() {
        Vmap = new HashMap<Integer, node_data>();
        SEmap = new HashMap<Integer, HashMap<Integer, edge_data>>();
        DEmap = new HashMap<Integer, HashMap<Integer, edge_data>>();
        Ecounter = 0;
        MCcounter = 0;
    }

    // need to fix
    // Tal's complexity was: max(nk,nk')
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
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return
     */
    // need to fix
    // Note: edge cases.
    // Vmap.get(src).get(dest)
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
     * Note: this method should run in O(1) time.
     *
     * @param n
     */
    // need to add few commends
    // Note: need to add node to all of the hashmap and build the inner hashmap.
    // NEED to check what happened if n already exist on graph
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
     * * Note: this method should run in O(1) time.
     *
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    // need to fix and and few commends
    // need to update for both hashmap and pay attention of edge cases.
    // Need to check of positive w only
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
     * Note: this method should run in O(1) time.
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
     * Note: this method should run in O(k) time, k being the collection size.
     *
     * @param node_id
     * @return Collection<edge_data>
     */
    // need to fix.
    // need to return a collection edge_data of outgoing of node.(SEmap)
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
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */

    // need to fix.
    //Note: you should remove from all hashmap the node given id.
    //P.S.- pay attention to edge cases and complexity that run by max o(k)
    // need to check with Tal on sunday
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
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    // need to fix.
    // pay attention that you should remove the edge from both hashmap and you relate to o(1) complexity.
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
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return Vmap.size();
    }

    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return Ecounter;
    }

    public Collection<edge_data> getInE(int node_id) { //to remove
        return DEmap.get(node_id).values(); //This is the outgoing edge_data collection for 'node_id'.
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     *
     * @return
     */
    @Override
    public int getMC() {
        return MCcounter;
    }

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

