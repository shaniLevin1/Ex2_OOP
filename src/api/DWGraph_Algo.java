package api;

import java.util.*;

public class DWGraph_Algo implements dw_graph_algorithms {
    private directed_weighted_graph gr;

    public void DWGraph_Algo() {
        this.gr = new DS_DWGraph();
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) {
        if (g != null) {
            this.gr = g;
        }
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public directed_weighted_graph getGraph() {
        return this.gr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWGraph_Algo that = (DWGraph_Algo) o;
        return Objects.equals(gr, that.gr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gr);
    }

    /**
     * Compute a deep copy of this weighted graph.
     *
     * @return
     */
    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph g = new DS_DWGraph(gr);
        return g;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        if (gr != null) {
            if (gr.nodeSize() == 0 || gr.nodeSize() == 1) {
                return true;
            }
            int counter1 = 0;
            for (node_data n : gr.getV()) {
                Queue<node_data> queue = new LinkedList<node_data>();
                queue.add(n);
                if (n.getTag() == 0) {
                    n.setTag(1);//1 means visited and 0 means not visited
                    counter1++;
                }
                while (!queue.isEmpty()) {
                    for (edge_data e : gr.getE(n.getKey())) {
                        if (gr.getNode(e.getDest()).getTag() == 0) {
                            queue.add(gr.getNode(e.getSrc()));
                            gr.getNode(e.getDest()).setTag(1);
                            counter1++;
                        }
                        queue.remove();
                    }
                }
            }
            if (counter1 != gr.nodeSize()) {
                return false;
            }
        }
        return true;
    }

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        return 0.4;
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        return null;
    }

    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        return false;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        return false;
    }

    @Override
    public String toString() {
        return gr.toString();
    }
}
