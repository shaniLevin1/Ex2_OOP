package tests;

import api.DS_DWGraph;
import api.NodeData;
import api.directed_weighted_graph;
import api.node_data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class DS_DWGraphTest {

    @Test
    void getNode() {
        directed_weighted_graph g0 =new DS_DWGraph();
        node_data n1=new NodeData(1);
        node_data n2=new NodeData(2);
        node_data n3=new NodeData(3);
        node_data n4=new NodeData(4);
        g0.addNode(n1);
        g0.addNode(n2);
        g0.addNode(n3);
        g0.addNode(n4);
    }

    @Test
    void getEdge() {
    }

    @Test
    void addNode() {
    }

    @Test
    void connect() {
    }

    @Test
    void getV() {
    }

    @Test
    void getE() {
    }

    @Test
    void removeNode() {
    }

    @Test
    void removeEdge() {
    }

    @Test
    void nodeSize() {
    }

    @Test
    void edgeSize() {
    }

    @Test
    void getInE() {
    }

    @Test
    void getMC() {
    }
}