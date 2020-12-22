package tests;

import api.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {

    @Test
    void getEdge() {
        directed_weighted_graph g = new DWGraph_DS();
        node_data n0= new NodeData(0);
        node_data n1= new NodeData(1);
        node_data n2= new NodeData(2);
        g.addNode(n0);
        g.addNode(n1);
        g.addNode(n2);
        g.connect(n1.getKey(), n0.getKey(),3);
        g.connect(n0.getKey(), n2.getKey(),4);
        g.connect(n1.getKey(), n2.getKey(),5);
        g.removeEdge(1, 2);
        assertNull(g.getEdge(1, 2));

        g.connect(1, 2, 4);
        assertEquals(4, g.getEdge(1, 2).getWeight());
        g.connect(1, 2, 3);
        assertEquals(3, g.getEdge(1, 2).getWeight());
        g.connect(1, 2, -12);
        assertEquals(3, g.getEdge(1, 2).getWeight());
        g.removeNode(2);
        assertNull(g.getEdge(2, 0));
        assertEquals(3,g.getEdge(1,0).getWeight());

    }

    @Test
    void addNode() {
        directed_weighted_graph g = new DWGraph_DS();
        node_data n = new NodeData(4);
        node_data n1 = new NodeData(n);
        g.addNode(n);
        assertEquals(1, g.nodeSize());
        g.addNode(n1);
        assertEquals(1, g.nodeSize());
        for (int i = 0; i < 11; i++)
            g.addNode(new NodeData(i));
        assertEquals(11, g.nodeSize());
    }

    @Test
    void removeNode() {
        directed_weighted_graph g =new DWGraph_DS();
        node_data n3 = new NodeData(3);
        g.removeNode(3); //Remove same node twice.
        g.removeNode(3);
        assertNull(g.getNode(3)); //Make sure the node is gone.
        node_data n0 = new NodeData(0);
        node_data n1 = new NodeData(1);
        node_data n2 = new NodeData(2);
        g.addNode(n0);
        g.addNode(n1);
        g.addNode(n2);
        assertNull(g.getEdge(3, 1)); //Make sure edges are gone.
        g.removeNode(n0.getKey());
        assertNull(g.getNode(0)); //Make sure the node is gone.
    }

    @Test
    void removeEdge() {
        directed_weighted_graph g = new DWGraph_DS();
        node_data n0= new NodeData(0);
        node_data n1= new NodeData(1);
        node_data n2= new NodeData(2);
        g.addNode(n0);
        g.addNode(n1);
        g.addNode(n2);
        g.connect(n1.getKey(), n0.getKey(),3);
        g.connect(n0.getKey(), n2.getKey(),4);
        g.connect(n1.getKey(), n2.getKey(),5);
        g.removeEdge(1, 2);
        assertNull(g.getEdge(1, 2));
    }

}