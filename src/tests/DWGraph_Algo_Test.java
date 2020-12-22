package tests;

import api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_Algo_Test {

    @Test
    void copy() {
        directed_weighted_graph g0 =new DWGraph_DS();
        node_data a1= new NodeData();
        node_data a2= new NodeData();
        node_data a3= new NodeData();
        g0.addNode(a1);
        g0.addNode(a2);
        g0.addNode(a3);
        g0.connect(a1.getKey(),a2.getKey(),3);
        g0.connect(a2.getKey(),a3.getKey(),2);
        g0.connect(a3.getKey(),a1.getKey(),4);
        directed_weighted_graph Copyg1 =new DWGraph_DS(g0);
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
        directed_weighted_graph graph0 =new DWGraph_DS();
        node_data n0= new NodeData();
        node_data n1= new NodeData();
        node_data n2= new NodeData();
        graph0.addNode(n0);
        graph0.addNode(n1);
        graph0.addNode(n2);
        graph0.connect(n0.getKey(),n1.getKey(),1);
        graph0.connect(n1.getKey(),n2.getKey(),5);
        graph0.connect(n2.getKey(),n0.getKey(),2);
        dw_graph_algorithms algoGraph0 = new DWGraph_Algo();
        algoGraph0.init(graph0);
        directed_weighted_graph copyAlgoGraph0 = algoGraph0.copy();
        boolean f = true;
        for (node_data i : graph0.getV()) {
            if (i == copyAlgoGraph0.getNode(i.getKey())) // check if there is same memory address
                f = false;
        }
        assertTrue(f);
        f = true;
        for (node_data rmv : copyAlgoGraph0.getV()) {
            copyAlgoGraph0.removeNode(rmv.getKey());
            break;
        }
        assertNotEquals(algoGraph0.getGraph().nodeSize(), copyAlgoGraph0.nodeSize());
    }
    @Test
    void isConnected(){
        directed_weighted_graph g0 =new DWGraph_DS();
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
        assertTrue(ag0.isConnected());
    }
    @Test
    void shortestPath(){
        directed_weighted_graph g0 =new DWGraph_DS();
        node_data a0= new NodeData(0);
        node_data a1= new NodeData(1);
        node_data a2= new NodeData(2);
        node_data a3= new NodeData(3);
        node_data a4= new NodeData(4);
        g0.addNode(a0);
        g0.addNode(a1);
        g0.addNode(a2);
        g0.addNode(a3);
        g0.addNode(a4);
        g0.connect(a1.getKey(),a2.getKey(),100);
        g0.connect(a2.getKey(),a3.getKey(),10);
        g0.connect(a4.getKey(),a3.getKey(),2);
        g0.connect(a2.getKey(),a4.getKey(),5);
        g0.connect(a4.getKey(),a2.getKey(),5);
        g0.connect(a4.getKey(),a1.getKey(),9);
        g0.connect(a1.getKey(),a4.getKey(),9);
        g0.connect(a0.getKey(),a1.getKey(),2);
        dw_graph_algorithms ag0 = new DWGraph_Algo();
        ag0.init(g0);
        int[] key={0,1};
        int j=0;
        System.out.println(ag0.shortestPath(0,5));
    }
}