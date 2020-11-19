package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {
    private weighted_graph graph;
    static weighted_graph g, g2;
    private weighted_graph_algorithms algorithms;
    static Random _rnd = null;

    @BeforeEach
    void setup() {
        graph = new WGraph_DS();
        algorithms = new WGraph_Algo();
        algorithms.init(graph);
    }

    @Test
    void init() {
        Assertions.assertSame(graph,algorithms.getGraph());
    }

    @Test
    void getGraph() {
        Assertions.assertEquals(graph,algorithms.getGraph());
    }

    @Test
    void copy() {
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.connect(1,2,3.0);
        weighted_graph copied = algorithms.copy();
        Assertions.assertNotSame(copied,graph);
        Assertions.assertEquals(copied,graph);
        Assertions.assertEquals(graph.getMC(),copied.getMC());
        graph.removeEdge(1,2);
        Assertions.assertNotEquals(copied,graph);
        graph.connect(1,2,3.0);
        Assertions.assertNotEquals(copied,graph);
        Assertions.assertNotEquals(graph.getMC(),copied.getMC());
        Assertions.assertEquals(graph.nodeSize(),copied.nodeSize());
        Assertions.assertEquals(graph.edgeSize(),copied.edgeSize());
        Assertions.assertEquals(graph.getV(),copied.getV());
        Assertions.assertEquals(graph.getV(1),copied.getV(1));
        copied.removeEdge(1,2);
        copied.connect(1,2,3.0);
        Assertions.assertEquals(graph,copied);
        algorithms.init(copied);
        graph = algorithms.copy();

        Assertions.assertEquals(graph.getMC(),copied.getMC());
        Assertions.assertEquals(graph,copied);
        Assertions.assertNotSame(copied,graph);
        Assertions.assertAll("Checks the whole list",
                ()-> Assertions.assertEquals(graph.getNode(1),copied.getNode(1)),
                ()-> Assertions.assertEquals(graph.getNode(2),copied.getNode(2)),
                ()-> Assertions.assertEquals(graph.getNode(3),copied.getNode(3)),
                ()-> Assertions.assertEquals(graph.getEdge(1,2),copied.getEdge(1,2)),
                ()-> Assertions.assertEquals(graph.nodeSize(),copied.nodeSize())
        );
    }

    @Test
    void isConnected() {
        Assertions.assertTrue(algorithms.isConnected());
        graph.addNode(1);
        Assertions.assertTrue(algorithms.isConnected());
        graph.addNode(2);
        Assertions.assertFalse(algorithms.isConnected());
        graph.connect(1,2,32.3);
        Assertions.assertTrue(algorithms.isConnected());
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addNode(6);
        graph.connect(2,3,32.3);
        graph.connect(3,4,32.3);
        graph.connect(4,5,32.3);
        graph.connect(5,6,32.3);
        graph.connect(6,1,32.3);
        Assertions.assertTrue(algorithms.isConnected());
        graph.removeEdge(1,6);
        Assertions.assertTrue(algorithms.isConnected());
        graph.removeEdge(3,4);
        Assertions.assertFalse(algorithms.isConnected());
    }

    @Test
    void shortestPathDist() {
        graph.addNode(1);
        graph.addNode(2);
        Assertions.assertEquals(algorithms.shortestPathDist(1,2),-1.0);
        graph.connect(1,2,3.0);
        Assertions.assertEquals(algorithms.shortestPathDist(1,2),3.0);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addNode(6);
        graph.addNode(7);
        graph.connect(1,3,3.0);
        graph.connect(2,4,3.0);
        graph.connect(3,5,3.0);
        graph.connect(4,6,3.0);
        graph.connect(5,7,3.0);
        graph.connect(7,6,3.0);
        Assertions.assertEquals(algorithms.shortestPathDist(1,6),9.0);
        graph.removeEdge(6,4);
        Assertions.assertEquals(algorithms.shortestPathDist(1,6),12.0);
        graph.removeEdge(6,7);
        Assertions.assertEquals(algorithms.shortestPathDist(1,6),-1.0);
    }

    @Test
    void shortestPath() {
        Assertions.assertNull(algorithms.shortestPath(1,2));
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addNode(6);
        graph.addNode(7);
        graph.connect(1,2,3.0);
        graph.connect(1,3,3.0);
        graph.connect(2,4,3.0);
        graph.connect(3,5,3.0);
        graph.connect(4,6,3.0);
        graph.connect(5,7,3.0);
        graph.connect(7,6,3.0);
        ArrayList<node_info> list =  new ArrayList<>(algorithms.shortestPath(1,6));
        Assertions.assertAll("Checks the path",
                () -> Assertions.assertEquals(list.get(0),graph.getNode(1)),
                () -> Assertions.assertEquals(list.get(1),graph.getNode(2)),
                () -> Assertions.assertEquals(list.get(2),graph.getNode(4)),
                () -> Assertions.assertEquals(list.get(3),graph.getNode(6))
        );
        graph.removeEdge(4,6);
        ArrayList<node_info> list2 =  new ArrayList<>(algorithms.shortestPath(1,6));
        Assertions.assertAll("Checks the path",
                () -> Assertions.assertEquals(list2.get(0),graph.getNode(1)),
                () -> Assertions.assertEquals(list2.get(1),graph.getNode(3)),
                () -> Assertions.assertEquals(list2.get(2),graph.getNode(5)),
                () -> Assertions.assertEquals(list2.get(3),graph.getNode(7)),
                () -> Assertions.assertEquals(list2.get(4),graph.getNode(6))
        );
        graph.removeEdge(7,6);
        Assertions.assertNull(algorithms.shortestPath(1,6));
    }

    @Test
    void save() {
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.connect(1,2,54.3);
        graph.connect(1,3,154.3);
        graph.connect(1,4,1154.3);
        graph.connect(2,3,11154.3);
        graph.connect(2,4,111154.3);
        graph.connect(3,4,1111154.3);
        Assertions.assertTrue(algorithms.save("Graph.bin"));
        Assertions.assertTrue(algorithms.save("hi"));
        Assertions.assertFalse(algorithms.save(""));
    }

    @Test
    void load() {
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.connect(1,2,54.3);
        graph.connect(1,3,154.3);
        graph.connect(1,4,1154.3);
        graph.connect(2,3,11154.3);
        graph.connect(2,4,111154.3);
        graph.connect(3,4,1111154.3);
        Assertions.assertTrue(algorithms.save("Graph.bin"));
        weighted_graph copied = algorithms.copy();
        algorithms.init(copied);
        Assertions.assertTrue(algorithms.save("Graph2.bin"));
        Assertions.assertTrue(algorithms.load("Graph.bin"));
        Assertions.assertTrue(algorithms.load("Graph2.bin"));
        Assertions.assertFalse(algorithms.load(""));
        Assertions.assertFalse(algorithms.load("naokfelwpasad3.dasa"));
    }

    @Test
    void Test1() {
        boolean check = Assertions.assertTimeout(Duration.ofMillis(100), ()-> {
            graphGenerator(80,79,100.0);
            weighted_graph_algorithms wg = new WGraph_Algo();
            wg.init(g);
            boolean ans = wg.isConnected();
            ans&=wg.isConnected();
            return ans;
        });
        Assertions.assertFalse(check);
    }

    @Test
    void Test2() {
        boolean check = Assertions.assertTimeout(Duration.ofMillis(100), ()-> {
            g = new WGraph_DS();
            for (int i = 1; i <= 10; i++) {
                g.addNode(i);
                if (i > 1) {
                    g.connect(i, i - 1, 93.2);
                }
            }
            weighted_graph_algorithms wg = new WGraph_Algo();
            wg.init(g);
            boolean ans = wg.isConnected();
            g.removeEdge(5, 6);
            ans &= wg.isConnected();
            return ans;
        });
        Assertions.assertFalse(check);
    }

    @Test
    @DisplayName("Create a 1M nodes and 5M edges graph")
    void Test3() {
        Assertions.assertTimeout(ofSeconds(11), () -> {
            int MILLION = 1000000;
            g = new WGraph_DS();
            for(int i = 1 ; i <= MILLION; i++) {
                g.addNode(i);
            }
            _rnd = new Random(1);

            while(g.edgeSize()<MILLION*5){
                int a = (int)(_rnd.nextDouble()*((double)MILLION-(0.0+1))+(0.0+1));
                int b = (int)(_rnd.nextDouble()*((double)MILLION-(0.0+1))+(0.0+1));
                int i = g.getNode(a).getKey();
                int j = g.getNode(b).getKey();
                g.connect(i,j,2+(10-2)*_rnd.nextDouble());
            }
        });
    }

    @Test
    @DisplayName("Copy a 1M nodes and 5M edges graph")
    void Test4() {
        boolean ans = Assertions.assertTimeout(ofSeconds(22), () -> {
            weighted_graph_algorithms algorithms = new WGraph_Algo();
            algorithms.init(g);
            g2 = algorithms.copy();
            return ((WGraph_DS)g2).equals(g);
        });
        Assertions.assertTrue(ans);
    }

    @Test
    void Test5() {
        boolean check = Assertions.assertTimeout(ofSeconds(7), ()-> {
            weighted_graph_algorithms algorithms = new WGraph_Algo();
            algorithms.init(g2);
            g2.connect(1,1000000,42332.12);
            boolean ans = (g.nodeSize() == g2.nodeSize());
            g.removeEdge(1,1000000);
            ans &= ((WGraph_DS)g).equals(g2);
            ans &= ((WGraph_DS)g2).equals(g);
            g.connect(1,1000000,42332.12);
            ans &= ((WGraph_DS)g).equals(g2);
            ans &= ((WGraph_DS)g2).equals(g);
            return ans;
        });
        Assertions.assertFalse(check);
    }

    @Test
    @DisplayName("Find a path distance")
    void Test6() {
        boolean check = Assertions.assertTimeout(ofSeconds(12), () -> {
            weighted_graph_algorithms algorithms = new WGraph_Algo();
            algorithms.init(g2);
            return (algorithms.shortestPathDist(1,1000000) == 42332.12);
        });
        Assertions.assertTrue(check);
    }

    @Test
    @DisplayName("find a path from node(1) to node(1M)")
    void Test7() {
        boolean check = Assertions.assertTimeout(ofSeconds(20), () -> {
            weighted_graph gr = new WGraph_DS();
            int max = 1000000/2;
            for(int i = 1 ; i <= max ; i++) {
                gr.addNode(i);
            }
            for(int i = 1 ; i <= max ; i++) {
                if(i>=3) {
                    if (i % 2 == 0) {
                        gr.connect(i,i-2,300.0);
                        if(i+1 < max)
                            gr.connect(i,i+1,1.0);
                        gr.connect(i,i-3,1.0);
                    }
                    else {
                        gr.connect(i,i-2,300.0);
                    }
                }
            }
            weighted_graph_algorithms algorithms = new WGraph_Algo();
            algorithms.init(gr);
            ArrayList<node_info> list = new ArrayList<>(algorithms.shortestPath(1,100000));
            boolean checkPath = true;
            int run = 1;
            for(int i = 0 ; i < list.size(); i++) {
                checkPath &= (list.get(i).getKey()==run);
                if(run%2==0)
                    run++;
                else
                    run+=3;
            }
            return checkPath;
        });
        Assertions.assertTrue(check);
    }

    @Test
    @DisplayName("Delete each node and check connections each time")
    void Test8() {
        boolean check = Assertions.assertTimeout(ofSeconds(4), ()-> {
            g = graph_creator(1000*50, 302650, 3);
            weighted_graph_algorithms ga = new WGraph_Algo();
            ga.init(g);
            //   System.out.println(g);
            int[] nodes = nodes(g);
            int i=0;
            boolean b = true;
            while(i<9) {
                b &= ga.isConnected();
                for(int x=0;x<1000;x++) {
                    int s = g.nodeSize();
                    int r = nextRnd(0, s);
                    int key = nodes[r]; // bug fix
                    g.removeNode(key);
                }
                i++;
            }
            return b;
        });
        Assertions.assertTrue(check);
    }
    public static void graphGenerator(int nodes, int edges,double weight) {
        g = new WGraph_DS();

        for(int i=0;i<nodes;i++) g.addNode(i);

        while(g.edgeSize() < edges) {
            double weight_RAND = ThreadLocalRandom.current().nextDouble(0.0, weight+1.0);
            int node1_RAND = ThreadLocalRandom.current().nextInt(0, nodes);
            int node2_RAND = ThreadLocalRandom.current().nextInt(0, nodes);
            g.connect(node1_RAND, node2_RAND,  weight_RAND);
        }
    }
    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph gi = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            gi.addNode(i);
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(gi);
        while(gi.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            gi.connect(i,j,gi.edgeSize());
        }
        return gi;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
}