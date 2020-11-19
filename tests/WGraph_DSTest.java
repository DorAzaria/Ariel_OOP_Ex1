package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.util.ArrayList;

import java.util.Date;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {
    private weighted_graph graph;
    private static Random _rnd = null;


    @BeforeEach
    void setup() {
        graph = new WGraph_DS();
    }

    @Test
    @DisplayName("Check if node(10) is exist")
    void getNode() {
        graph.addNode(10);
        Assertions.assertNotNull(graph.getNode(10));
        Assertions.assertNull(graph.getNode(1));
    }

    @Test
    @DisplayName("Check if there's an edge bw 1<->10")
    void hasEdge() {
        graph.addNode(1);
        graph.addNode(10);
        graph.addNode(4);
        graph.addNode(7);
        graph.connect(1,10,38.94);
        Assertions.assertTrue(graph.hasEdge(1,10));
        Assertions.assertFalse(graph.hasEdge(4,7));
    }

    @Test
    @DisplayName("Check equals keys and node's keys")
    void addNode() {
        graph.addNode(1);
        graph.addNode(10);
        Assertions.assertEquals(graph.getNode(1).getKey(),1);
        Assertions.assertEquals(graph.getNode(10).getKey(),10);
        graph.addNode(10000);
        Assertions.assertEquals(graph.getNode(10000).getKey(),10000);
    }

    @Test
    @DisplayName("Check equals weight and edge's weight")
    void getEdge() {
        graph.addNode(1);
        graph.addNode(10);
        graph.connect(1,10,38.94);
        Assertions.assertEquals(graph.getEdge(1,10),38.94);
    }

    @Test
    @DisplayName("Check edge size changes in every connect")
    void connect() {
        graph.addNode(1);
        graph.addNode(10);
        graph.connect(1,10,38.09);
        Assertions.assertEquals(graph.edgeSize(),1);
        graph.connect(10,1,38.09);
        Assertions.assertEquals(graph.edgeSize(),1);
        graph.addNode(4);
        graph.connect(1,4,99.3);
        Assertions.assertEquals(graph.edgeSize(),2);
        graph.connect(1,1,32.1);
        Assertions.assertEquals(graph.edgeSize(),2);
        graph.connect(2022,12212,32.1);
        Assertions.assertEquals(5,graph.getMC());
        graph.connect(1,4,99.2);
        Assertions.assertEquals(6,graph.getMC());
        Assertions.assertEquals(graph.edgeSize(),2);
        graph.addNode(43);
        graph.connect(10,43,-1);
        Assertions.assertFalse(graph.hasEdge(10,43));
    }

    @Test
    @DisplayName("Check the nodes list of the graph")
    void getV() {
        Assertions.assertTrue(graph.getV().isEmpty());
        ArrayList<node_info> list = new ArrayList<>();
        graph.addNode(1);
        graph.addNode(20);
        graph.addNode(312);
        graph.addNode(4303);
        list.add(graph.getNode(1));
        list.add(graph.getNode(20));
        list.add(graph.getNode(312));
        list.add(graph.getNode(4303));
        Assertions.assertFalse(graph.getV().isEmpty());
        graph.addNode(53313);
        Assertions.assertNotEquals(list,graph.getV());
        list.add(graph.getNode(53313));
        Assertions.assertAll("Checks the whole list",
                ()-> Assertions.assertEquals(list.get(0),graph.getNode(1)),
                ()-> Assertions.assertEquals(list.get(1),graph.getNode(20)),
                ()-> Assertions.assertEquals(list.get(2),graph.getNode(312)),
                ()-> Assertions.assertEquals(list.get(3),graph.getNode(4303)),
                ()-> Assertions.assertEquals(list.get(4),graph.getNode(53313))
        );
        Assertions.assertTrue(list.size() == graph.getV().size()
                && list.containsAll( graph.getV())
                &&  graph.getV().containsAll(list));
    }

    @Test
    @DisplayName("Check the neighbors list of a node")
    void testGetV() {
        graph.addNode(1);
        graph.addNode(2);
        Assertions.assertTrue(graph.getV(1).isEmpty());
        graph.connect(1,2,9.1);
        ArrayList<node_info> list1 = new ArrayList<>();
        ArrayList<node_info> list2 = new ArrayList<>();
        list1.add(graph.getNode(2));
        Assertions.assertEquals(list1,graph.getV(1));
        list2.add(graph.getNode(1));
        Assertions.assertEquals(list2,graph.getV(2));
        Assertions.assertNotEquals(list1,list2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addNode(6);
        graph.connect(3,2,1.2);
        graph.connect(4,2,67.4);
        graph.connect(5,2,1.2);
        graph.connect(6,2,94.3);
        list2.add(graph.getNode(3));
        list2.add(graph.getNode(4));
        list2.add(graph.getNode(5));
        list2.add(graph.getNode(6));
        Assertions.assertEquals(list2,graph.getV(2));
        Assertions.assertAll("Checks the whole list",
                ()-> Assertions.assertEquals(list2.get(0),graph.getNode(1)),
                ()-> Assertions.assertEquals(list2.get(1),graph.getNode(3)),
                ()-> Assertions.assertEquals(list2.get(2),graph.getNode(4)),
                ()-> Assertions.assertEquals(list2.get(3),graph.getNode(5)),
                ()-> Assertions.assertEquals(list2.get(4),graph.getNode(6))

        );
        Assertions.assertTrue(list2.size() == graph.getV(2).size()
                && list2.containsAll( graph.getV(2))
                &&  graph.getV(2).containsAll(list2));
    }

    @Test
    void removeNode() {
        Assertions.assertNull(graph.removeNode(2));
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.connect(1,2,3.4);
        graph.connect(1,3,3.4);
        graph.connect(1,4,3.4);
        graph.connect(1,5,3.4);
        graph.connect(2,3,3.4);
        graph.connect(2,4,3.4);
        Assertions.assertEquals(5,graph.nodeSize());
        node_info node = graph.getNode(1);
        Assertions.assertEquals(node,graph.removeNode(1));
        Assertions.assertFalse(graph.getV().contains(node));
        Assertions.assertFalse(graph.getV(2).contains(node));
        Assertions.assertTrue(graph.getV(2).contains(graph.getNode(3)));
        Assertions.assertEquals(4,graph.nodeSize());
    }

    @Test
    void removeEdge() {
        graph.removeEdge(2,1);
        Assertions.assertEquals(0,graph.edgeSize());
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.connect(1,2,3.4);
        graph.connect(1,3,3.4);
        graph.connect(2,3,3.4);
        Assertions.assertEquals(3,graph.edgeSize());
        graph.removeEdge(1,2);
        Assertions.assertEquals(2,graph.edgeSize());
        graph.removeEdge(2,1);
        Assertions.assertEquals(2,graph.edgeSize());
        graph.removeEdge(2,2);
        Assertions.assertEquals(2,graph.edgeSize());
        graph.removeEdge(1,3);
        Assertions.assertEquals(1,graph.edgeSize());
        graph.removeEdge(2,3);
        Assertions.assertEquals(0,graph.edgeSize());
    }

    @Test
    @DisplayName("Checks every node size changes")
    void nodeSize() {
        Assertions.assertEquals(0,graph.nodeSize());
        graph.addNode(1);
        Assertions.assertEquals(1,graph.nodeSize());
        graph.removeNode(1);
        Assertions.assertEquals(0,graph.nodeSize());
    }

    @Test
    @DisplayName("Checks every edge size changes")
    void edgeSize() {
        Assertions.assertEquals(0,graph.edgeSize());
        graph.addNode(1);
        graph.addNode(2);
        graph.connect(1,2,643.4);
        Assertions.assertEquals(1,graph.edgeSize());
        graph.removeEdge(1,2);
        Assertions.assertEquals(0,graph.edgeSize());
    }

    @Test
    @DisplayName("Checks every mode count changes")
    void getMC() {
        Assertions.assertEquals(0,graph.getMC());
        graph.addNode(1);
        Assertions.assertEquals(1,graph.getMC());
        graph.addNode(2);
        Assertions.assertEquals(2,graph.getMC());
        graph.connect(1,2,584.2);
        Assertions.assertEquals(3,graph.getMC());
        graph.removeEdge(1,2);
        Assertions.assertEquals(4,graph.getMC());
        graph.removeNode(1);
        Assertions.assertEquals(5,graph.getMC());
    }

    @Test
    void setMC() {
        graph.addNode(1);
        graph.addNode(2);
        Assertions.assertEquals(graph.getMC(),2);
        caster().setMC(5);
        Assertions.assertEquals(5,graph.getMC());
    }

    @Test
    void iterator() {
        Assertions.assertIterableEquals((WGraph_DS)graph,graph.getV());
    }

    public WGraph_DS caster() { return ((WGraph_DS) graph); }

    @Test
    void Test1(){
      boolean ans = Assertions.assertTimeout(ofMillis(30), () -> {
            for(int i = 1 ; i <= 750; i++) {
                graph.addNode(i);
            }
        return (graph.nodeSize()==750);
        });
      Assertions.assertTrue(ans);
    }

    @Test
    void Test2() {
        boolean ans = Assertions.assertTimeout(ofMillis(500), () -> {
            for(int i = 1 ; i <= 750; i++) {
                graph.addNode(i);
            }
            int n = graph.nodeSize();
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    graph.connect(i, j, 102.3);
                }
            }
            return (graph.edgeSize() == (n * (n - 1) / 2));
        });
        Assertions.assertTrue(ans);
    }

    @Test
    void Test3() {
       boolean check = Assertions.assertTimeout(ofMillis(30), () -> {
            weighted_graph_algorithms algorithms = new WGraph_Algo();
            algorithms.init(graph);
            weighted_graph ultimateGraph = algorithms.copy();
           return caster().equals(ultimateGraph);
        });
       Assertions.assertTrue(check);
    }

    @Test
    void Test4() {
        boolean check = Assertions.assertTimeout(ofSeconds(1), () -> {
            for(int i = 1 ; i <= 750; i++) {
                graph.addNode(i);
            }
            int n = graph.nodeSize();
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    graph.connect(i, j, 102.3);
                }
            }
            weighted_graph_algorithms algorithms = new WGraph_Algo();
            algorithms.init(graph);
            weighted_graph ultimateGraph = algorithms.copy();
            return ((WGraph_DS)graph).equals(ultimateGraph);
        });
        Assertions.assertTrue(check);
    }

    @Test
    void runTime() {
        Assertions.assertTimeout(ofSeconds(10), () -> {
            int MILLION = 1000000;
            long start = new Date().getTime();
            weighted_graph ultimateGraph = new WGraph_DS();
            for(int i = 1 ; i <= MILLION; i++) {
                ultimateGraph.addNode(i);
            }
            _rnd = new Random(1);

            while(ultimateGraph.edgeSize()<MILLION*5){
                int a = (int)(_rnd.nextDouble()*((double)MILLION-(0.0+1))+(0.0+1));
                int b = (int)(_rnd.nextDouble()*((double)MILLION-(0.0+1))+(0.0+1));
                int i = ultimateGraph.getNode(a).getKey();
                int j = ultimateGraph.getNode(b).getKey();
                ultimateGraph.connect(i,j,2+(10-2)*_rnd.nextDouble());
            }
        });
    }
}