package ex1.tests;

import ex1.src.CompareDistance;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompareDistanceTest {

    @Test
    void compare() {
        CompareDistance comparator = new CompareDistance();
        WGraph_DS graph_ds = new WGraph_DS();
        graph_ds.addNode(1);
        graph_ds.addNode(2);
        graph_ds.getNode(1).setTag(4.0);
        graph_ds.getNode(2).setTag(4.0);
        node_info o1 = graph_ds.getNode(1);
        node_info o2 = graph_ds.getNode(2);
        Assertions.assertEquals(comparator.compare(o1,o2),0);
        graph_ds.getNode(2).setTag(5.0);
        Assertions.assertEquals(comparator.compare(o1,o2),-1);
        graph_ds.getNode(2).setTag(3.0);
        Assertions.assertEquals(comparator.compare(o1,o2),1);

    }
}