package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gameClient.util.Point3D;

import java.util.*;

public class Ex2 implements Runnable {
    private static ArrayList<CL_Pokemon> _pokemons;
    private static edge_data[] keepTarget;
    private static ArrayList<CL_Agent> _agents;
    private static game_service game1;
    private static Arena _ar;
    private static MyFrame _win;
    private static List<node_data> AgentPath;
    private static dw_graph_algorithms graphAlgo;
    private static directed_weighted_graph graph;
    private static int AgentNum;
    private static HashMap<Integer, HashMap<Integer, List<node_data>>> Dijkstra;
    private static double eps = 0.0000001;
    private static int id1;
    private static int level1;
    private static Thread client;


    public Ex2(String id, String level) {
        try {
            id1 = Integer.parseInt(id);
            level1 = Integer.parseInt(level);
        } catch (NumberFormatException e) {
            id1 = 0;
            level1 = 16;
        }
    }

    public static void main(String[] args) {
        String args0 = "", args1 = "";
        if (args.length >= 2) {
            args0 = args[0];
            args1 = args[1];
        }
        try {
            if (client != null)
                client.join();
            client = new Thread(new Ex2(args0, args1));
            game1 = Game_Server_Ex2.getServer(level1);
            if(game1.login(id1))
                System.out.println("Game login succeeded for level "+level1+" and for id "+id1);
            else
                System.out.println("Game login failed for level "+level1+" and for id "+id1);
            init();
            client.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Init the Arena according the server.
     */

    private static void init() {
        JsonElement gameElement = JsonParser.parseString(game1.toString());
        JsonObject gameServerObjects = gameElement.getAsJsonObject();
        JsonElement gameServerElements = gameServerObjects.get("GameServer");
        JsonObject gameServerObject = gameServerElements.getAsJsonObject();
        AgentNum = gameServerObject.get("agents").getAsInt();
        keepTarget = new edge_data[AgentNum];

        graphAlgo = new DWGraph_Algo(load(game1.getGraph()));
        graph = graphAlgo.getGraph();
        _ar = new Arena(game1);
        _ar.setGraph(graph);
        game1.getPokemons();
        _pokemons = _ar.json2Pokemons(game1.getPokemons());
        for (int i = 0; i < _pokemons.size(); i++) {
            _ar.updateEdge(_pokemons.get(i), graph);
        }
        _ar.setPokemons(_pokemons);
        locateAgents();
        _ar.setAgents(_agents);
        buildDijkstra();

        _ar.setTime(game1.timeToEnd());

        _win = new MyFrame("test Ex2");
        _win.setSize(1000, 700);
        _win.update(_ar);
        _win.show();

    }


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        game1.startGame();
        _win.setTitle("Ex2 - OOP: " + game1.toString());
        long dt = 91;
        int ind = 0;

        while (game1.isRunning()) {
            moveAgents(game1, graph);
            try {
                if (ind % 1 == 0) {
                    _win.repaint();
                }
                Thread.sleep(dt);
                ind++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String res = game1.toString();

        System.out.println(res);
        System.exit(0);

    }

    /**
     * Creating hashmap of all the nodes and for each node the shortest path to every other node in the graph, by using Dijkstra algorithm.
     */
    private static void buildDijkstra() { //
        Dijkstra = new HashMap<Integer, HashMap<Integer, List<node_data>>>();
        List<node_data> list = new LinkedList<>();
        for (node_data current : graphAlgo.getGraph().getV()) {
            HashMap<Integer, List<node_data>> d = new HashMap<>();
            Dijkstra.put(current.getKey(), d);
            for (node_data n : graphAlgo.getGraph().getV()) {
                list = graphAlgo.shortestPath(current.getKey(), n.getKey());
                Dijkstra.get(current.getKey()).put(n.getKey(), list);
            }
        }
    }

    /**
     * Locate the agents at the beginning of the game.
     */
    private static void locateAgents() {
        CL_Pokemon[] pokemon = new CL_Pokemon[AgentNum];
        for (int i = 0; i < pokemon.length; i++) {
            pokemon[i] = choosePokemon();
            assert pokemon[i] != null; //instead of exception
            game1.addAgent(pokemon[i].get_edge().getSrc());
            pokemon[i].setHasAgent(true);

        }
        _agents = (ArrayList<CL_Agent>) Arena.getAgents(game1.getAgents(), graphAlgo.getGraph());
        for (int i = 0; i < AgentNum; i++) {
            _agents.get(i).set_curr_target(pokemon[i]);
            keepTarget[i] = pokemon[i].get_edge();
        }
    }

    /**
     * Choosing the pokemon with the best ratio between the value and the weight of the edge for the start of the game.
     *
     * @return best-the best pokemon
     */

    private static CL_Pokemon choosePokemon() {//check which pokemon is the best in the graph
        double max = Double.MIN_VALUE;
        CL_Pokemon best = mostValue(_pokemons);
        for (CL_Pokemon temp : _pokemons) {
            if (!temp.getHasAgent()) {
                if ((temp.getValue() / temp.get_edge().getWeight()) > max) {
                    max = temp.getValue() / temp.get_edge().getWeight();
                    best = temp;
                }
            }
        }
        return best;
    }

    /**
     *  Load a graph by receiving a json string to the graph of this class.
     * @param json
     * @return the graph which loaded from the json string.
     */
    private static directed_weighted_graph load(String json) {
        directed_weighted_graph graph1 = new DWGraph_DS();
        JsonObject graph = new JsonParser().parse(json).getAsJsonObject();
        JsonArray nodes = graph.getAsJsonArray("Nodes");
        JsonArray edges = graph.getAsJsonArray("Edges");

        for (JsonElement n : nodes) {

            int id = ((JsonObject) n).get("id").getAsInt();
            double x1 = 0;
            double x2 = 0;
            double x3 = 0;
            String pos = ((JsonObject) n).get("pos").getAsString();
            String x = "";
            int counter = 0;
            for (int i = 0; i < pos.length(); i++) {
                if (pos.charAt(i) == ',') {
                    if (counter == 0) x1 = Double.parseDouble(x);
                    if (counter == 1) x2 = Double.parseDouble(x);
                    if (counter == 2) x3 = Double.parseDouble(x);
                    counter++;
                    x = "";
                } else {
                    x = x + pos.charAt(i);
                }
            }
            geo_location location = new Point3D(x1, x2, x3);
            node_data n1 = new NodeData(id);
            n1.setLocation(location);
            graph1.addNode(n1);
        }

        for (JsonElement e : edges) {
            int src = ((JsonObject) e).get("src").getAsInt();
            double weight = ((JsonObject) e).get("w").getAsDouble();
            int dest = ((JsonObject) e).get("dest").getAsInt();

            edge_data e1 = new EdgeData(src, dest, weight);
            graph1.connect(e1.getSrc(), e1.getDest(), e1.getWeight());
        }
        return graph1;
    }

    /**
     * moves the agents using dijkastra shortest path algorithm and highest value
     *
     * @param game the game to move the agents in
     * @param g    the graph witch all of the operations happening on
     */
    private synchronized void moveAgents(game_service game, directed_weighted_graph g) {
        int dest = -1;
        String l = game.move();
        _win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
        _ar.setTime(game1.timeToEnd());
        String f = game.getPokemons();
        _agents = (ArrayList<CL_Agent>) _ar.getAgents(l, graph);
        _ar.setAgents(_agents);
        _pokemons = _ar.json2Pokemons(f);
        _ar.setPokemons(_pokemons);
        for (CL_Pokemon p : _pokemons) {
            _ar.updateEdge(p, graph);
        }

        _ar.setGraph(graph);
        CL_Agent a;
        AgentPath = new LinkedList<>();
        for (int i = 0; i < _agents.size(); i++) {
            a = _agents.get(i);
            if (a.get_curr_target() == null) {
                AgentPath = chooseNewTarget(a);// should return the best list ratio
                if(AgentPath==null){
                    dest = minimalEdge(a);
                }
                else {
                    if (AgentPath.size() >= 2) {
                        dest = AgentPath.get(1).getKey();
                    } else {
                        dest = a.get_curr_target().get_edge().getDest(); // catch the pokemon
                    }
                }

            } else {
                dest = minimalEdge(a);
            }
            game1.chooseNextEdge(a.getID(), dest);

        }
    }

    private static int minimalEdge(CL_Agent a) {
        double min = Double.MAX_VALUE;
        edge_data e = null;
        for (edge_data edgeData : graph.getE(a.getSrcNode())) {
            if (min > e.getWeight()) {
                min = e.getWeight();
                e = edgeData;
            }
        }
        return e.getDest();
    }

    /**
     * returns a path that the agent will have to pass to the 'best' pokemon
     *
     * @param a the checked agent
     * @return list of the nodes to the target
     */
    private List<node_data> chooseNewTarget(CL_Agent a) { //return the list from the agent to the
        CL_Pokemon best=null;
        double bestPath = 0;
        double max = Double.MIN_VALUE;
        double ratio;
        double x, y;
        List<node_data> list = new LinkedList<>();
        for (CL_Pokemon temp : _pokemons) {
            if (!temp.getHasAgent()) {
                list = Dijkstra.get(a.getSrcNode()).get(temp.get_edge().getSrc());
                if (list != null) { //it means there is no path
                    x = dist(list);
                    y = temp.get_edge().getWeight();
                    bestPath = x + y; //add conditions if there is no path or if the src and the dest are equals
                    ratio = temp.getValue() / bestPath;
                    if (ratio > max) {
                        max = ratio;
                        best = temp;
                    }
                }
            }
        }

        best.setHasAgent(true);
        a.set_curr_target(best);
        return list;
    }

    /**
     * Return the sum of the edges weight.
     *
     * @param list-List<node_data>
     * @return sum-dist
     */

    private double dist(List<node_data> list) {
        double sum = 0;
        if (list != null) {
            int i;
            int x, y;
            for (i = 0; i < list.size() - 1; i++) {
                x = list.get(i).getKey();
                y = list.get(i + 1).getKey();
                sum += graphAlgo.getGraph().getEdge(x, y).getWeight();
            }
        }
        return sum;
    }

    /**
     * returns the pokemon with the highest value from a given list
     *
     * @param list the given pokemon list
     * @return the 'best' pokemon
     */
    public static CL_Pokemon mostValue(ArrayList<CL_Pokemon> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        CL_Pokemon f_highest = list.get(0);
        for (int i = 0; i < list.size() - 1; i++) {
            if (!list.get(i).getHasAgent() && f_highest.getValue() < list.get(i).getValue()) {
                f_highest = list.get(i);
            }
        }
        return f_highest;
    }

}
