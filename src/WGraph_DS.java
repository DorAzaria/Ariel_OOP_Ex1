package ex1.src;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class represents an undirected weighted graph.<br>
 * It has a HashMap which contains all the nodes in the graph,
 * another HashMap which contains the edges in the graph.<br>
 * It has an inner class 'NodeInfo' and some methods.
 * */
public class WGraph_DS implements weighted_graph, Iterable<node_info> , Serializable{
    private final HashMap<Integer,node_info> nodes;
    private final HashMap<Integer,HashMap<node_info,Double>> edges;
    private int mode_count, edges_size;
    /**
     * A default constructor.
     */
    public WGraph_DS() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
    }
    /**
     * @param key the node_id
     * @return the node from the nodes list, null if none.
     */
    @Override
    public node_info getNode(int key) { return nodes.getOrDefault(key, null); }
    /**
     * This method checks if there's an edge between n1 to n2. <br>
     * @param n1 a key of a node.
     * @param n2 a key of a node.
     * @return true if there is an edge.
     */
    @Override
    public boolean hasEdge(int n1, int n2) {
        if(edges.containsKey(n1)&&edges.get(n1).containsKey(getNode(n2)))
            return edges.containsKey(n2)&&edges.get(n2).containsKey(getNode(n1));
        return false;
    }
    /**
     * @param n1 a key of a node.
     * @param n2 a key of a node.
     * @return the weight between n1 to n2, if there's no edge it returns -1.
     */
    @Override
    public double getEdge(int n1, int n2) {
        return hasEdge(n1,n2) ? edges.get(n1).get(getNode(n2)) : -1;
    }
    /**
     * Adding a new initialized node to the nodes list.<br>
     * The given key is fits the same key of the nodes HashMap.
     * It also init its neighbor list.
     * @param key of a node.
     */
    @Override
    public void addNode(int key) {
        if(!nodes.containsKey(key)) {
            nodes.put(key, new NodeInfo(key, -1, ""));
            edges.put(key, new HashMap<>());
            mode_count++;
        }
    }
    /**
     * The given node may contain some used data.
     * We'll init a new node with this new data (a copy).
     * It also init its neighbor list but not copying the used node list.
     * @param node a given node.
     */
    public void addNode(node_info node) {
        if(!nodes.containsKey(node.getKey())) {
            nodes.put(node.getKey(), new NodeInfo(node.getKey(), node.getTag(), node.getInfo()));
            edges.put(node.getKey(), new HashMap<>());
            mode_count++;
        }
    }
    /**
     * Creating an edge_info between node1 and node2 and placing the weight
     * and adding it to the edges list.
     * @param n1 a key of a node.
     * @param n2 a key of a node.
     * @param w the weight value must be at least 0.
     */
    @Override
    public void connect(int n1, int n2, double w) {
        if(!nodes.containsKey(n1)||!nodes.containsKey(n2)||n1==n2) return;
        if(w >= 0) {
            if(!hasEdge(n1,n2)) {
                edges_size++;
                edges.get(n1).put(getNode(n2),w);
                edges.get(n2).put(getNode(n1),w);
                mode_count++;
            }else if(getEdge(n1,n2)!=w){
                    mode_count++;
                    edges.get(n1).put(getNode(n2), w);
                    edges.get(n2).put(getNode(n1), w);
            }
        }
    }
    /**
     * @return a pointer for an ArrayList containing all the nodes of the graph.
     */
    @Override
    public Collection<node_info> getV() { return new ArrayList<>(nodes.values()); }
    /**
     * @param node_id a key of a node.
     * @return an ArrayList containing all the neighbors of the given node.
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if(edges.containsKey(node_id)) return new ArrayList<>(edges.get(node_id).keySet());
        else return new ArrayList<>();
    }
    /**
     * Removes all of its neighbors from its neighbor list.
     * At the end of the process it removes the given node.
     * @param key of a node.
     * @return the removed node, null if none.
     */
    @Override
    public node_info removeNode(int key) {
        if(!nodes.containsKey(key)) return null;

        for(node_info neighbor : getV(key))
            removeEdge(key,neighbor.getKey());

        mode_count++;
        return nodes.remove(key);
    }
    /**
     * Removes the edge between n1 to n2.
     * @param n1 the removed node.
     * @param n2 its neighbor node.
     */
    @Override
    public void removeEdge(int n1, int n2) {
        if(hasEdge(n1,n2)) {
            edges.get(n1).remove(getNode(n2));
            edges.get(n2).remove(getNode(n1));
            edges_size--;
            mode_count++;
        }
    }
    /**
     * @return the number of nodes in this graph.
     */
    @Override
    public int nodeSize() { return nodes.size(); }
    /**
     * @return the number of edges in this graph.
     */
    @Override
    public int edgeSize() { return edges_size; }
    /**
     * @return the number of mode counts of this graph.
     */
    @Override
    public int getMC() { return mode_count; }
    /**
     * used for the copy method in the algorithms class.
     * @param mc the mode count.
     */
    public void setMC(int mc) { mode_count = mc; }
    /**
     * @return an iterator of the node's list.
     */
    @Override
    public Iterator<node_info> iterator() { return nodes.values().iterator(); }
    /**
     * @param o an Object (WGraph_DS in this case).
     * @return true if this graph is equals to another given graph - o.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WGraph_DS that = (WGraph_DS) o;

        if (mode_count != that.mode_count) return false;
        if (edges_size != that.edges_size) return false;
        if (!nodes.equals(that.nodes)) return false;
        return edges.equals(that.edges);
    }
    /**
     * This method could help hash-type data-structures act faster in this class.
     * @return a unique code that made by the values of the graph fields.
     */
    @Override
    public int hashCode() {
        int result = nodes.hashCode();
        result = 31 * result + edges.hashCode();
        result = 31 * result + mode_count;
        result = 31 * result + edges_size;
        return result;
    }
    /**
     * @return a string of the graph data.
     */
    @Override
    public String toString() {
        return "WGraph_DS{" +
                "nodes=" + nodes +
                ", edges=" + edges +
                ", mode_count=" + mode_count +
                ", edges_size=" + edges_size +
                '}';
    }
    /**
     * This class represents a node with some fields.
     * each node has a key which is given in its initial process.<br>
     * Each key is fits to the same key of the nodes HashMap.
     */
    private static class NodeInfo implements node_info, Serializable {
        private double _tag;
        private final int _key;
        private String _info;
        /**
         * A parametric constructor.<br>
         * The key is fits to where he placed in the nodes HashMap.<br>
         * @param key the value of the key.
         * @param tag the value of the tag.
         * @param info the value of the information.
         */
        public NodeInfo(int key, double tag, String info) {
            _key = key;
            _tag = tag;
            _info = info;
        }
        /**
         * @return the key of this node.
         */
        @Override
        public int getKey() {
            return _key;
        }
        /**
         * @return the info of this node.
         */
        @Override
        public String getInfo() {
            return _info;
        }
        /**
         * Set the information (meta-data) of this node.
         * @param s the value of the information.
         */
        @Override
        public void setInfo(String s) { _info = s;}
        /**
         * @return the tag of this node.
         */
        @Override
        public double getTag() {
            return _tag;
        }
        /**
         * Set the tag of this node.
         * @param t  the new value of the tag
         */
        @Override
        public void setTag(double t) {
            _tag = t;
        }
        /**
         * @return a string of the node's data.
         */
        @Override
        public String toString() {
            return "NodeInfo{" +
                    "_tag=" + _tag +
                    ", _key=" + _key +
                    ", _info='" + _info + '\'' +
                    '}';
        }
        /**
         * @param o an Object (NodeInfo in this case).
         * @return true if this node is equals to another given node - o.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NodeInfo nodeInfo = (NodeInfo) o;

            return _key == nodeInfo._key;
        }
        /**
         * This method could help hash-type data-structures act faster in this class.
         * @return a unique code that made by the values of the graph fields.
         */
        @Override
        public int hashCode() {
            return _key;
        }

    }

}