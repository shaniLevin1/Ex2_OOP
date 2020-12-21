package gameClient;

import api.edge_data;
import gameClient.util.Point3D;

public class CL_Pokemon {
    private edge_data _edge;
    private double _value;
    private int _type;
    private Point3D _pos;
    private double min_dist;
    private int min_ro;
    private boolean HasAgent;
    private final double eps = 0.0000001;
    /**
     *
     * @return HasAgent : boolean-true if there is an agent that go after this pokemon, else false.
     */

    public boolean getHasAgent(){
        return HasAgent;
    }
    /**
     * Set the boolean HasAgent.
     * @param b : boolean-true if there is an agent that go after this pokemon, else false.
     */

    public void setHasAgent(boolean b){
        HasAgent=b;
    }

    /**
     * empty constructor for pokemon
     */
    public CL_Pokemon() { }
    /**
     * Copy constructor.
     * @param p-Point3D
     * @param t-_type
     * @param v-_value
     * @param e-edge_data
     */
    public CL_Pokemon(Point3D p, int t, double v, double s, edge_data e) {
        _type = t;
        _value = v;
        set_edge(e);
        _pos = p;
        min_dist = -1;
        min_ro = -1;
        HasAgent=false;
    }

    /**
     *
     * @return toString of the pokemon.
     */
    public String toString() {return "F:{v="+_value+", t="+_type+"}";}
    /**
     *
     * @return _edge- edge_data of the edge of the pokemon.
     */
    public edge_data get_edge() {
        return _edge;
    }

    /**
     * sets the given edge
     * @param _edge the edge to be set
     */
    public void set_edge(edge_data _edge) {
        this._edge = _edge;
    }
    /**
     *
     * @return _pos-Point3D of the pokemon.
     */
    public Point3D getLocation() {
        return _pos;
    }
    /**
     *
     * @return _type-the type of the pokemon.
     */
    public int getType() {return _type;}
    /**
     *
     * @return _value-the value of the pokemon.
     */
    public double getValue() {return _value;}

}