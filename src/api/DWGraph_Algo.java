package api;

import com.google.gson.*;
import gameClient.util.Point3D;

import java.io.*;
import java.util.*;


public class DWGraph_Algo implements dw_graph_algorithms {
    private directed_weighted_graph gr;
    /**
     * Directed weighted graph with all its properties - copy constructor by a given graph
     */
    public DWGraph_Algo(directed_weighted_graph graph) {
        this.gr =  (directed_weighted_graph)graph;
    }

    /**
     * graph constructor
     */
    public DWGraph_Algo() {
        this.gr = new DS_DWGraph();
    }




    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g - the graph to be focused on
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

    /**
     * checks if who graphs are equals
     * @param o the graph to be compared with the current graph
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWGraph_Algo that = (DWGraph_Algo) o;
        return Objects.equals(gr, that.gr);
    }

    /**
     * returns the hashcode associated with a graph object
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(gr);
    }

    /**
     * Compute a deep copy of this weighted graph.
     *
     * @return copied graph
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
     * @return true if the graph isConnected- else returns false
     */
    @Override
    public boolean isConnected() {
        boolean b =true;
        if (gr != null) {
            if (gr.nodeSize() == 0 || gr.nodeSize() == 1) {
                return true;
            }

            for (node_data n : gr.getV()) {
                int counter1 = 0;
                for (node_data m : gr.getV()) {
                    m.setTag(0);
                }
                Queue<node_data> queue = new LinkedList<node_data>();
                queue.add(n);
                if (n.getTag() == 0) {
                    n.setTag(1);//1 means visited and 0 means not visited
                    counter1++;
                }
                while (!queue.isEmpty()) {
                    n=queue.poll();
                    for (edge_data e : gr.getE(n.getKey())) {
                        if (gr.getNode(e.getDest()).getTag() == 0) {
                            queue.add(gr.getNode(e.getDest()));
                            gr.getNode(e.getDest()).setTag(1);
                            counter1++;
                        }
                    }
                }
                if (counter1 != gr.nodeSize()) {
                    System.out.println(counter1);
                    b=false;
                }
            }

        }
        return b;
    }

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return the shortest distance between src and dest
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (gr.nodeSize()==0 || gr == null || (gr.nodeSize() == 1 && src != dest) || gr.getNode(src) == null || gr.getNode(dest) == null)
            return -1;
        if (src == dest)
            return 0;
        int countSrcDest = cSRCd(gr.getNode(dest));
        int count = 0;
        Iterator<node_data> x=gr.getV().iterator();
        while(x.hasNext()){
            x.next().setTag(0);
        }
        HashMap<Integer, Double> dist = new HashMap<>();//hashmap for the distances (int for key)
        PriorityQueue<node_data> queue = new PriorityQueue<>(new Comparator<node_data>() {
            @Override
            public int compare(node_data o1, node_data o2) {
                return -Double.compare(dist.get(o2.getKey()), dist.get(o1.getKey()));
            }//defining the comparator- returns the shortest distance from src node
        });
        dist.put(src, 0.0);
        queue.add(gr.getNode(src));
        while (!queue.isEmpty()) {
            node_data keep = queue.poll();
            for (edge_data i : gr.getE(keep.getKey())) {
                if (gr.getNode(i.getDest()).getTag() != 2) {
                    if (gr.getNode(i.getDest()).getKey() == dest)
                        count++;
                    if (dist.containsKey(i.getDest())) {
                        if (dist.get(i.getDest()) > dist.get(i.getSrc()) + gr.getEdge(i.getSrc(), i.getDest()).getWeight()) {
                            dist.put(i.getDest(), dist.get(i.getSrc()) + gr.getEdge(i.getSrc(), i.getDest()).getWeight());
                            queue.remove(i.getDest());//remove the old value (bigger than current)
                        }
                    } else
                        dist.put(i.getDest(), dist.get(i.getSrc()) + gr.getEdge(i.getSrc(), i.getDest()).getWeight());

                    if (queue.contains(gr.getNode(i.getDest()))) {
                        queue.remove(gr.getNode(i.getDest()));
                    }

                    gr.getNode(i.getDest()).setTag(1);
                    queue.add(gr.getNode(i.getDest()));
                }
            }

            if (count == countSrcDest)
                break;
            gr.getNode(keep.getKey()).setTag(2);//i finished with that node completely
        }

        return dist.get(dest);
    }

    /**
     * returns the amount of edges that dest is their destination
     * @param dest to check how many sources it has
     * @return the amount of edges that dest is their destination
     */
    public int cSRCd(node_data dest){
        int count=0;
        for(node_data i : gr.getV()){
           for(edge_data j: gr.getE(i.getKey())) {
               if (j.getDest() == dest.getKey())
                   count++;
           }
        }
        return count;
    }
    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return list of the patten
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        if (gr.nodeSize() == 0 || gr == null || (gr.nodeSize() == 1 && src != dest) || gr.getNode(src) == null || gr.getNode(dest) == null||gr.getE(src).size()==0)
            return null;
        HashMap<Integer, List<node_data>> shortest = new HashMap<Integer, List<node_data>>();
        List<node_data> l = new LinkedList<>();
        l.add(gr.getNode(src));
        if (src == dest)
            return new LinkedList<>();
        shortest.put(src, l);
        int countSrcDest = cSRCd(gr.getNode(dest));
        int count = 0;
        Iterator<node_data> x = gr.getV().iterator();
        while (x.hasNext()) {
            x.next().setTag(0);
        }
        HashMap<Integer, Double> dist = new HashMap<>();//hashmap for the distances (int for key)
        PriorityQueue<node_data> queue = new PriorityQueue<>(new Comparator<node_data>() {
            @Override
            public int compare(node_data o1, node_data o2) {
                return -Double.compare(dist.get(o2.getKey()), dist.get(o1.getKey()));
            }//defining the comparator- returns the shortest distance from src node
        });
        dist.put(src, 0.0);
        queue.add(gr.getNode(src));
//        if(src==0)
//            count++;
        while (!queue.isEmpty()) {
            node_data keep = queue.poll();
            for (edge_data i : gr.getE(keep.getKey())) {
                if (gr.getNode(i.getDest()).getTag() != 2) {
                    if (gr.getNode(i.getDest()).getKey() == dest || gr.getNode(i.getSrc()).getKey() == dest)
                        count++;
                    if (dist.containsKey(i.getDest())) {
                        if (dist.get(i.getDest()) > dist.get(i.getSrc()) + gr.getEdge(i.getSrc(), i.getDest()).getWeight()) {
                            dist.put(i.getDest(), dist.get(i.getSrc()) + gr.getEdge(i.getSrc(), i.getDest()).getWeight());
                            queue.remove(i.getDest());//remove the old value (bigger than current)
                        }
                    } else
                        dist.put(i.getDest(), dist.get(i.getSrc()) + gr.getEdge(i.getSrc(), i.getDest()).getWeight());
                    List<node_data> s = new LinkedList<node_data>();
                    Iterator<node_data> it = shortest.get((keep.getKey())).iterator();
                    while (it.hasNext()) {
                        s.add(it.next());
                    }
                    //deep copy for the list
                    s.add(gr.getNode(i.getDest()));
                    if (queue.contains(gr.getNode(i.getDest()))) {
                        queue.remove(gr.getNode(i.getDest()));
                    }

                    shortest.put(gr.getNode(i.getDest()).getKey(), s);
                }
                if(gr.getNode(i.getDest()).getTag()!=2) {
                    gr.getNode(i.getDest()).setTag(1);
                    queue.add(gr.getNode(i.getDest()));
                }

            if (count == countSrcDest)
                return shortest.get(dest);
        }
            gr.getNode(keep.getKey()).setTag(2);//i finished with that node completely
    }
if(!shortest.containsKey(dest))
    return null;
        return shortest.get(dest);
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

        Gson json = new GsonBuilder().create();
        JsonArray nodes = new JsonArray();
        JsonArray edges = new JsonArray();
        JsonObject graph = new JsonObject();
        for (node_data n : this.gr.getV()) {
            JsonObject o = new JsonObject();
            double location = n.getLocation().x(), y = n.getLocation().y(), z = n.getLocation().z();
            o.addProperty("pos", location + "," + y + "," + z);
            o.addProperty("id", n.getKey());
            nodes.add(o);

            for (edge_data e : this.gr.getE(n.getKey())) {
                JsonObject edge = new JsonObject();
                edge.addProperty("src", e.getSrc());
                edge.addProperty("w", e.getWeight());
                edge.addProperty("dest", e.getDest());
                edges.add(edge);
            }
        }
        graph.add("Nodes", nodes);
        graph.add("Edges", edges);
        File x = new File(file);
        try {
            FileWriter writer = new FileWriter(x);
            writer.write(json.toJson(graph));
            writer.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method loads a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {

        directed_weighted_graph graph1 = new DS_DWGraph(); //The graph to load onto.
        JsonObject graph; //The Gson Object to read from.
        File f = new File(file); //The file containing the data.
        try {
            FileReader reader = new FileReader(f);
            graph = new JsonParser().parse(reader).getAsJsonObject();
            JsonArray nodes = graph.getAsJsonArray("Nodes"); //Get The "Edges" member from the Json Value.
            JsonArray edges = graph.getAsJsonArray("Edges"); //Get The "Edges" member from the Json Value.
//            JsonArray nodes = graph.getAsJsonArray("Nodes"); //Get The "Edges" member from the Json Value.

            for (JsonElement n : nodes) {

                int id = ((JsonObject) n).get("id").getAsInt();
                double x1 = 0;
                double x2 = 0;
                double x3 = 0;
                String pos = ((JsonObject) n).get("pos").getAsString();
                String x="";
                int counter=0;
                for (int i = 0; i < pos.length(); i++) {
                    if(pos.charAt(i)==','){
                        if(counter==0) x1=Double.parseDouble(x);
                        if(counter==1) x2=Double.parseDouble(x);
                        if(counter==2) x3=Double.parseDouble(x);
                        counter++;
                        x="";
                    }
                    else{
                        x=x + pos.charAt(i);
                    }
                }
                geo_location location = new Point3D(x1, x2, x3);
                node_data n1 = new NodeData(id); //Insert into node_data n values from Json.
                n1.setLocation(location); //Insert into node_data n location values from Json that created.
                graph1.addNode(n1);
            }

            for (JsonElement e : edges) {
                int src = ((JsonObject) e).get("src").getAsInt(); //Receive src
                double weight = ((JsonObject) e).get("w").getAsDouble(); //Receive weight
                int dest = ((JsonObject) e).get("dest").getAsInt(); //Receive dest

                edge_data e1 = new EdgeData(src, dest, weight); //Build a new edge with given args
                graph1.connect(e1.getSrc(), e1.getDest(), e1.getWeight()); //Connect between nodes on the new graph
            }
            this.gr = graph1;
            // System.out.println("Graph loaded successfully");
            return true;

        } catch (FileNotFoundException e) {
            // System.out.println("Failed to load graph");
            e.printStackTrace();
        }

        return false;
    }
    /**
     * returns a string performance of the required information
     * @return a string performance of the required information
     */
    @Override
    public String toString() {
        return gr.toString();
    }
}
