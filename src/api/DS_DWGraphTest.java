package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DS_DWGraphTest {

    @Test
    void copy() {
        directed_weighted_graph g0 =new DS_DWGraph();
        node_data a1= new NodeData();
        node_data a2= new NodeData();
        node_data a3= new NodeData();
        g0.addNode(a1);
        g0.addNode(a2);
        g0.addNode(a3);
        g0.connect(a1.getKey(),a2.getKey(),3);
        g0.connect(a2.getKey(),a3.getKey(),2);
        g0.connect(a3.getKey(),a1.getKey(),4);
        directed_weighted_graph Copyg1 =new DS_DWGraph(g0);
        boolean f = true;
        for (node_data i : g0.getV()) {
            if (i == Copyg1.getNode(i.getKey())) // check if there is same memory address
                f = false;
        }
        assertTrue(f);
        f = true;
        for (node_data rmv : Copyg1.getV()) {
            g0.removeNode(rmv.getKey());
            break;
        }
        assertNotEquals(g0.nodeSize(), Copyg1.nodeSize());
    }

    @Test
    void copySec() {
        directed_weighted_graph g0 =new DS_DWGraph();
        node_data a1= new NodeData();
        node_data a2= new NodeData();
        node_data a3= new NodeData();
        g0.addNode(a1);
        g0.addNode(a2);
        g0.addNode(a3);
        g0.connect(a1.getKey(),a2.getKey(),3);
        g0.connect(a2.getKey(),a3.getKey(),2);
        g0.connect(a3.getKey(),a1.getKey(),4);
        dw_graph_algorithms ag0 = new DWGraph_Algo();
        ag0.init(g0);
        directed_weighted_graph g1 = ag0.copy();
        boolean f = true;
        for (node_data i : g0.getV()) {
            if (i == g1.getNode(i.getKey())) // check if there is same memory address
                f = false;
        }
        assertTrue(f);
        f = true;
        for (node_data rmv : g1.getV()) {
            g1.removeNode(rmv.getKey());
            break;
        }
        assertNotEquals(ag0.getGraph().nodeSize(), g1.nodeSize());
    }
}