package ex1.src;

import java.util.Comparator;

public class CompareDistance implements Comparator<node_info> {

    /**A conducive comparator for the priority of the queue Used for the Dijkstra algorithm.
     * Compares the tags of o1 and o2, each tag represent the distance from the given source.
     * @param o1 the first node
     * @param o2 the second node
     * @return
     */
    @Override
    public int compare(node_info o1, node_info o2) {
        return Double.compare(o1.getTag(),o2.getTag());
    }
}
