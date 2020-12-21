package gameClient;

import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a multi Agents Arena which move on a graph - grabs Pokemons and avoid the Zombies.
 *
 * @author boaz.benmoshe
 */
public class Arena {
    private game_service game;
    public static final double EPS1 = 0.001, EPS2 = EPS1 * EPS1, EPS = EPS2;
    private directed_weighted_graph _gg;
    private List<CL_Agent> _agents;
    private List<CL_Pokemon> _pokemons;
    private List<String> _info;
    private JFrame jFrame;
    private static Point3D MIN = new Point3D(0, 100, 0);
    private static Point3D MAX = new Point3D(0, 100, 0);

    public static float getTime() {
        return time;
    }

    public static void setTime(float time) {
        Arena.time = time;
    }

    private static float time;
    /**
     *Constructor
     */
    public Arena(game_service g) {
        game = g;
        _info = new ArrayList<String>();

    }
    /**
     *Set the list of pokemons in the Arena.
     * @param f-List<CL_Pokemon>
     */

    public void setPokemons(List<CL_Pokemon> f) {
        this._pokemons = f;
    }
    /**
     *Set the list of agents in the Arena.
     * @param f-List<CL_Agent>
     */
    public void setAgents(List<CL_Agent> f) {
        this._agents = f;
    }
    /**
     *Set the graph in the Arena.
     * @param g-directed_weighted_graph
     */
    public void setGraph(directed_weighted_graph g) {
        this._gg = g;
    }
    /**
     *Return the list of the agents in this Arena.
     * @return _agents-List<CL_Agent>
     */
    public List<CL_Agent> getAgents() {
        return _agents;
    }
    /**
     *Return the list of the pokemons in this Arena.
     * @return _pokemons-List<CL_Pokemon>
     */
    public List<CL_Pokemon> getPokemons() {
        return json2Pokemons(game.getPokemons());
    }
    /**
     *Return the graph in this Arena.
     * @return _gg-directed_weighted_graph
     */

    public directed_weighted_graph getGraph() {
        return _gg;
    }
    /**
     *Return the list of the string of the info in this Arena.
     * @return _info-List<String>
     */
    public List<String> get_info() {
        return _info;
    }
    /**
     *Return the list of the agents from json string.
     * @param aa-String
     * @param gg-directed_weighted_graph
     * @return ans-List<CL_Agent>
     */

    public static List<CL_Agent> getAgents(String aa, directed_weighted_graph gg) {
        ArrayList<CL_Agent> ans = new ArrayList<CL_Agent>();
        try {
            JSONObject ttt = new JSONObject(aa);
            JSONArray ags = ttt.getJSONArray("Agents");
            for (int i = 0; i < ags.length(); i++) {
                CL_Agent c = new CL_Agent(gg, 0);
                c.update(ags.get(i).toString());
                ans.add(c);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ans;
    }
    /**
     * Return the ArrayList of pokemons fron json string.
     * @param fs-String
     * @return ans-ArrayList<CL_Pokemon>
     */
    public static ArrayList<CL_Pokemon> json2Pokemons(String fs) {
        ArrayList<CL_Pokemon> ans = new ArrayList<CL_Pokemon>();
        try {
            JSONObject ttt = new JSONObject(fs);
            JSONArray ags = ttt.getJSONArray("Pokemons");
            for (int i = 0; i < ags.length(); i++) {
                JSONObject pp = ags.getJSONObject(i);
                JSONObject pk = pp.getJSONObject("Pokemon");
                int t = pk.getInt("type");
                double v = pk.getDouble("value");
                String p = pk.getString("pos");
                CL_Pokemon f = new CL_Pokemon(new Point3D(p), t, v, 0, null);
                ans.add(f);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ans;
    }
    /**
     * Update the
     * @param fr-CL_Pokemon
     * @param g-directed_weighted_graph
     */
    public static void updateEdge(CL_Pokemon fr, directed_weighted_graph g) {
        Iterator<node_data> itr = g.getV().iterator();
        while (itr.hasNext()) {
            node_data v = itr.next();
            Iterator<edge_data> iter = g.getE(v.getKey()).iterator();
            while (iter.hasNext()) {
                edge_data e = iter.next();
                boolean f = isOnEdge(fr.getLocation(), e, fr.getType(), g);
                if (f) {
                    fr.set_edge(e);
                }
            }
        }
    }

    private static boolean isOnEdge(geo_location p, geo_location src, geo_location dest) {

        boolean ans = false;
        double dist = src.distance(dest);
        double d1 = src.distance(p) + p.distance(dest);
        if (dist > d1 - (EPS2*EPS2)) {
            ans = true;
        }
        return ans;
    }

    private static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph g) {
        geo_location src = g.getNode(s).getLocation();
        geo_location dest = g.getNode(d).getLocation();
        return isOnEdge(p, src, dest);
    }

    private static boolean isOnEdge(geo_location p, edge_data e, int type, directed_weighted_graph g) {
        int src = g.getNode(e.getSrc()).getKey();
        int dest = g.getNode(e.getDest()).getKey();
        if (type < 0 && dest > src) {
            return false;
        }
        if (type > 0 && src > dest) {
            return false;
        }
        return isOnEdge(p, src, dest, g);
    }

    private static Range2D GraphRange(directed_weighted_graph g) {
        Iterator<node_data> itr = g.getV().iterator();
        double x0 = 0, x1 = 0, y0 = 0, y1 = 0;
        boolean first = true;
        while (itr.hasNext()) {
            geo_location p = itr.next().getLocation();
            if (first) {
                x0 = p.x();
                x1 = x0;
                y0 = p.y();
                y1 = y0;
                first = false;
            } else {
                if (p.x() < x0) {
                    x0 = p.x();
                }
                if (p.x() > x1) {
                    x1 = p.x();
                }
                if (p.y() < y0) {
                    y0 = p.y();
                }
                if (p.y() > y1) {
                    y1 = p.y();
                }
            }
        }
        Range xr = new Range(x0, x1);
        Range yr = new Range(y0, y1);
        return new Range2D(xr, yr);
    }

    public static Range2Range w2f(directed_weighted_graph g, Range2D frame) {
        Range2D world = GraphRange(g);
        Range2Range ans = new Range2Range(world, frame);
        return ans;
    }

}
