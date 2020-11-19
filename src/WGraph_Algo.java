package ex1.src;

import java.io.*;
import java.util.*;
public class WGraph_Algo implements weighted_graph_algorithms, Serializable {
    private WGraph_DS graph;
    private final HashMap<node_info,node_info> parent = new HashMap<>();
    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g  a graph.
     */
    @Override
    public void init(weighted_graph g) { this.graph = (WGraph_DS)g; }
    /**
     * @return the current working graph.
     */
    @Override
    public weighted_graph getGraph() { return graph; }
    /**
     * <strong>Time Complexity</strong> - less than O(N^2), |Vertex|=N.
     * this method is sent to copyComponents method.
     * @return a new deep-copied graph.
     */
    @Override
    public weighted_graph copy() { return copyComponents(); }
    /**
     * This algorithm is used with the BFS algorithm.
     * The algorithm can take care of the case of a graph with many components.
     * I didn't liked the idea of the O(N^2) time complexity for a copy of a graph.
     * And the biggest problem was, how can I copy a graph with some components
     * without missing any component on the way and without a double-loop?.
     * The solution was to make a simple BFS algorithm, everytime the queue poll a node
     * it adds to the new graph, and every node needs its neighbors ofc, I took care of it too.
     * About copying each component - I wrote the BFS code inside a while loop,
     * each loop represent a different components and may have a huge amount of data.
     * to get to each component, at the first loop I inserted all the node of the old graph inside
     * a HashSet, each node will be deleted from the set when it found in the loop.
     * (I used this set because the delete and contains time complexity of this DS is O(1) which could be essential).
     * If there's more nodes left after the first loop it will loop to the next components till the set is empty.
     * In the end the method returns a new copied graph.
     * This method time complexity is O(|N|+|E|+|C|), but could be a bit more because of the while loops, C=|components|.
     * @return a new deep-copied graph.
     */
    private weighted_graph copyComponents() {
        Set<node_info> visited = new HashSet<>(graph.getV());
        WGraph_DS new_graph = new WGraph_DS();
        while(!visited.isEmpty()) {
            if (visited.iterator().hasNext()) {
                node_info node = visited.iterator().next();
                LinkedList<node_info> queue = new LinkedList<>();
                queue.add(node);
                while (!queue.isEmpty()) {
                    node = queue.poll();
                    new_graph.addNode(node);
                    for (node_info neighbors : graph.getV(node.getKey())) {
                        if (visited.contains(neighbors)) {
                            new_graph.addNode(neighbors);
                            queue.add(neighbors);
                            visited.remove(neighbors);
                            new_graph.connect(node.getKey(), neighbors.getKey(), graph.getEdge(node.getKey(), neighbors.getKey()));
                        } else if (!new_graph.hasEdge(node.getKey(), neighbors.getKey()))
                            new_graph.connect(node.getKey(), neighbors.getKey(), graph.getEdge(node.getKey(), neighbors.getKey()));
                    }
                    visited.remove(node);
                }
            }
        }
        new_graph.setMC(graph.getMC());
        return new_graph;
    }
    /**
     * This method is solved with the BFS algorithm.<br>
     * <strong>Time Complexity</strong> - O(N+E), |Vertex| = N , |Edge| = E.
     * @return true IFF there is a valid path from every node to each
     */
    @Override
    public boolean isConnected() {
        return connection()==graph.nodeSize();
    }
    /**
     * This method is used in every algorithm in this class.
     * It reset all the visited and weighted signs of each node in the graph.
     * <strong>Time Complexity</strong> - O(N), |Vertex| = N.
     */
    private void reset() {
        for(node_info runner : graph) {
            runner.setTag(-1);
            runner.setInfo("");
        }
        parent.clear();
    }
    /**
     * <strong>Time Complexity</strong> - O(1).
     */
    private boolean isVisited(node_info node) { return !node.getInfo().equals("visited");}
    /**
     * marks the node as a visited node.<br>
     * <strong>Time Complexity</strong> - O(1).
     * @param node a given node.
     */
    private void markAsVisited(node_info node) { node.setInfo("visited");}
    /**
     * This method is under the boolean isConnected() method<br>
     * using the BFS algorithm:<br>
     * a queue contains a temp path of unvisited node, <br>
     * I used a counter to count all the unvisited nodes from this connectivity, <br>
     * each node is marked with a tag, 1 - visited, -1 - not visited.<br>
     * <strong>Time Complexity</strong> - O(N+E), |Vertex| = N , |Edge| = E.
     * @return the nodes counted in the current connected graph.
     */
    private int connection() {
        if(!graph.iterator().hasNext()) return 0;
        node_info node = graph.iterator().next();
        LinkedList<node_info> queue = new LinkedList<>();
        reset();
        int counter = 1;
        markAsVisited(node);
        queue.add(node);
        while(!queue.isEmpty()) {
            node = queue.poll();
            for(node_info neighbors : graph.getV(node.getKey()))
                if(isVisited(neighbors)) {
                    counter++;
                    markAsVisited(neighbors);
                    queue.add(neighbors);
                }
        }
        return counter;
    }
    /**
     * return the length between two given key nodes solved with the Dijkstra algorithm.<br>
     * <strong>Time Complexity</strong> - O(E+NlogN), |Vertex| = N , |Edge| = E.
     * @param src start node.
     * @param dest end (target) node.
     * @return the length of the shortest path between src to dest.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        return path(src,dest).isEmpty() ? -1 : graph.getNode(dest).getTag();
    }
    /**
     * Solved with the Dijkstra algorithm. <br>
     * <strong>Time Complexity</strong> -O(E+NlogN), |Vertex| = N , |Edge| = E.
     * @param src - start node
     * @param dest - end (target) node
     * @return the shortest path between src to dest - as an ordered List of nodes
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        return path(src,dest).isEmpty() ? null : path(src,dest);
    }
    /**
     * it checks all the edge cases to optimize the run time
     * before using the Dijkstra method if it's necessary.<br>
     * <strong>Time Complexity</strong> - O(E+NlogN), |Vertex| = N , |Edge| = E.
     * @param src source node
     * @param dest destination node
     * @return an ArrayList that contains all the nodes that are connected from src to dest.
     */
    private List<node_info> path(int src, int dest) {
        if(src == dest) return new ArrayList<>();
        if(graph.getNode(src) != null && graph.getNode(dest) != null) {
            return Dijkstra(graph.getNode(src),graph.getNode(dest));
        }
        return new ArrayList<>();
    }
    /**
     * About the Dijkstra algorithm: <br>
     * I learned about this algorithm via YouTube videos and from the video that boaz shared.
     * I used a Priority-Queue and a Comparator for a specific priority of the weight of each node from the src to dest.
     * The first nodes in the queue is having the less distance from the source.
     * I also used a HashMap to own each node's parents in the case I find the dest so I'll be able to go back
     * to the source and to contain each node on the way inside an ArrayList (the path).
     * Each node that I met on the way is will own its distance to the source.
     * In the end of the process the algo is going to the makeAPath method for the final job.
     * <strong>Time Complexity</strong> - O(E+NlogN), |Vertex| = N , |Edge| = E.
     * @param src the source of this path.
     * @param dest the destination of this path.
     * @return
     */
    private List<node_info> Dijkstra(node_info src,node_info dest){
        reset();
        PriorityQueue<node_info> priorityQueue = new PriorityQueue<>(graph.nodeSize(), new CompareDistance());
        priorityQueue.add(src);
        src.setTag(0.0);
        while(!priorityQueue.isEmpty()){
            node_info current = priorityQueue.poll();
            for(node_info neighbor : graph.getV(current.getKey())){
                if(isVisited(neighbor)) {
                    if(neighbor.getTag()==-1.0)
                        neighbor.setTag(Double.MAX_VALUE);
                    if(newDistance(current,neighbor) < neighbor.getTag()){
                        neighbor.setTag(newDistance(current,neighbor));
                        parent.put(neighbor,current);
                        priorityQueue.add(neighbor);
                    }
                }
            }
            markAsVisited(current);
        }
        return makeAPath(dest);
    }
    /**
     * @param current the current node
     * @param neighbor its neighbor code
     * @return the destination of this neighbor to the source.
     */
    private double newDistance(node_info current, node_info neighbor) {
        return current.getTag()+graph.getEdge(current.getKey(),neighbor.getKey());
    }
    /**
     * filling an ArrayList of node_info, it contains the path from source to destination.
     * I used the parent HashMap (previous node) to go back to the source while adding
     * each node to the list with the addFirst method.
     * If the destination isn't reached, the method returns an empty ArrayList.
     * <strong>Time Complexity</strong> - O(N).
     * @param next from destination.
     * @return an ArrayList of node_info, it contains the path from source to destination.
     */
    private List<node_info> makeAPath(node_info next) {
        if(isVisited(next)) { return new ArrayList<>(); }
        LinkedList<node_info> path = new LinkedList<>();
        while(parent.get(next)!=null) {
            path.addFirst(next);
            next = parent.get(next);
        }
        path.addFirst(next);
        return path;
    }
    /**
     * @param file - the file name (may include a relative path).
     * @return true if it saved successfully.
     */
    @Override
    public boolean save(String file) {
        // Serialization
        try {
            //Saving of object in a file
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));

            // Method for serialization of object
            out.writeObject(graph);
            out.close();
            return true;
        }

        catch(IOException ex) {
            System.out.println("IOException is caught");
        }
        return false;
    }
    /**
     * @param file - file name
     * @return true if it load successfully.
     */
    @Override
    public boolean load(String file) {
        // Serialization
        try {
            // Reading the object from a file
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));

            // Method for deserialization of object
            graph = (WGraph_DS) in.readObject();

            in.close();
            return true;
        }

        catch(IOException ex) {
            System.out.println("IOException is caught");
        }

        catch(ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }

        return false;
    }
    /**
     * @param o an Object (WGraph_Algo in this case).
     * @return true if this graph algo  is equals to another given graph algo- o.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WGraph_Algo that = (WGraph_Algo) o;

        if (!graph.equals(that.graph)) return false;
        return parent.equals(that.parent);
    }
    /**
     * This method could help hash-type data-structures act faster in this class.
     * @return a unique code that made by the values of the graph fields.
     */
    @Override
    public int hashCode() {
        int result = graph.hashCode();
        result = 31 * result + parent.hashCode();
        return result;
    }
}