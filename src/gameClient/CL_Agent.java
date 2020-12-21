package gameClient;

import api.*;
import gameClient.util.Point3D;
import org.json.JSONObject;

import java.util.ArrayList;

public class CL_Agent {

    private int _id;
    private geo_location _pos;
    private double _speed;
    private edge_data _curr_edge;
    private node_data _curr_node;
    private directed_weighted_graph _gg;
    private CL_Pokemon _curr_pokemon;
    private ArrayList<node_data> _nextDest;
    private long _sg_dt;
    private double _value;
    private CL_Pokemon _curr_target;
    /**
     * returns the target pokemon associated with the current.
     * @return _curr_target-CL_Pokemon
     */
    public CL_Pokemon get_curr_target() {
        return _curr_target;
    }
    /**
     * Sets the current target of the current agent.
     * @param _curr_target-CL_Pokemon
     */
    public void set_curr_target(CL_Pokemon _curr_target) {
        this._curr_target = _curr_target;
    }
    /**
     * Constructor
     * @param g-directed_weighted_graph
     * @param start_node-key of the start node
     */
    public CL_Agent(directed_weighted_graph g, int start_node) {
        _gg = g;
        setMoney(0);
        this._curr_node = _gg.getNode(start_node);
        _pos = _curr_node.getLocation();
        _id = -1;
        setSpeed(0);
        this._nextDest=new ArrayList<node_data>();
    }

    /**
     * Updates the info from the json string.
     * @param json-String
     */
    public void update(String json) {
        JSONObject line;
        try {
            // "GameServer":{"graph":"A0","pokemons":3,"agents":1}}
            line = new JSONObject(json);
            JSONObject ttt = line.getJSONObject("Agent");
            int id = ttt.getInt("id");
            if(id==this.getID() || this.getID() == -1) {
                if(this.getID() == -1) {_id = id;}
                double speed = ttt.getDouble("speed");
                String p = ttt.getString("pos");
                Point3D pp = new Point3D(p);
                int src = ttt.getInt("src");
                int dest = ttt.getInt("dest");
                double value = ttt.getDouble("value");
                this._pos = pp;
                this.setCurrNode(src);
                this.setSpeed(speed);
                this.setNextNode(dest);
                this.setMoney(value);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * @return key-id of the src of the edge of the pokemon.
     */
    public int getSrcNode() {return this._curr_node.getKey();}
    /**
     *
     * @return a string performance of the json format
     */
    public String toJSON() {
        int d = this.getNextNode();
        String ans = "{\"Agent\":{"
                + "\"id\":"+this._id+","
                + "\"value\":"+this._value+","
                + "\"src\":"+this._curr_node.getKey()+","
                + "\"dest\":"+d+","
                + "\"speed\":"+this.getSpeed()+","
                + "\"pos\":\""+_pos.toString()+"\""
                + "}"
                + "}";
        return ans;
    }

    /**
     * Sets the value of the pokemon
     * @param v value
     */
    private void setMoney(double v) {_value = v;}
    /**
     * sets the next node to be chosen
     * @param dest-key of the next node of the agent
     * @return ans:boolean-true if there is an edge, else false.
     */
    public boolean setNextNode(int dest) {
        boolean ans = false;
        int src = this._curr_node.getKey();
        this._curr_edge = _gg.getEdge(src, dest);
        if(_curr_edge!=null) {
            ans=true;
        }
        else {_curr_edge = null;}
        return ans;
    }

    /**
     * Sets the current node
     * @param src-key of the curr node
     */
    public void setCurrNode(int src) {
        this._curr_node = _gg.getNode(src);
    }
    /**
     *
     * @return toString- using json format
     */
    public String toString() {
        return toJSON();
    }
    /**
     *
     * @return id-key of the node
     */
    public int getID() {
        // TODO Auto-generated method stub
        return this._id;
    }
    /**
     *
     * @return the location of the agent
     */
    public geo_location getLocation() {
        // TODO Auto-generated method stub
        return _pos;
    }
    /**
     *
     * @return value- the value of the pokemon
     */

    public double getValue() {
        // TODO Auto-generated method stub
        return this._value;
    }
    /**
     *
     * @return ans-key of the next node of the agent
     */

    public int getNextNode() {
        int ans = -2;
        if(this._curr_edge==null) {
            ans = -1;}
        else {
            ans = this._curr_edge.getDest();
        }
        return ans;
    }
    /**
     *
     * @return _speed-the speed of the agent
     */
    public double getSpeed() {
        return this._speed;
    }
    /**
     * Set the speed of the agent
     * @param v-speed
     */
    public void setSpeed(double v) {
        this._speed = v;
    }

    /**
     * returns the edge that the current agent is traveling on
     * @return the current edge
     */
    public edge_data get_curr_edge() {
        return this._curr_edge;
    }

}