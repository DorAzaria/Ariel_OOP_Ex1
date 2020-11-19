<!DOCTYPE html>
<html>

<body class="stackedit">
  <div class="stackedit__html"><h1 id="a-weighted-undirected-graphs-algorithm-library">A weighted Undirected Graphs Algorithm Library</h1>
<p>This repository represent a collection of algorithms on weighted undirected graphs.<br>
<img src="https://i.ibb.co/7KNDbcD/weighted.jpg" alt="enter image description here"><br>
a data structure infrastructure, algorithms and display system. There are algorithms like duplication capability, connectivity test, shortest path calculation (minimum number of edges) and finding the shortest path.</p>
<h2 id="file-list">File List</h2>

<table>
<thead>
<tr>
<th>Name</th>
<th>Description</th>
</tr>
</thead>
<tbody>
<tr>
<td>weighted_graph.java</td>
<td>This interface represents an undirectional uweighted graph.</td>
</tr>
<tr>
<td>weighted_graph_algorithms.java</td>
<td>This interface represents the “regular” Graph Theory algorithms.</td>
</tr>
<tr>
<td>node_info.java</td>
<td>This interface represents the set of operations applicable on a node in a graph.</td>
</tr>
<tr>
<td>WGraph_DS.java</td>
<td>Graph_DS is basically a HashMap contains all the nodes in the graph + implements graph.java methods.</td>
</tr>
<tr>
<td>CompareDistance.java</td>
<td>A comparator.</td>
</tr>
<tr>
<td>WGraph_Algo.java</td>
<td>Graph_Algo implements graph_algorithms.java.</td>
</tr>
</tbody>
</table><h2 id="wgraph_algo-methods">WGraph_Algo methods</h2>
<p>activating algorithms methods for a given graph.</p>
<h3 id="copycomponents"><code>copyComponents()</code></h3>
<p>This algorithm is used with the BFS algorithm.<br>
The algorithm can take care of the case of a graph with many components. I didn’t liked the idea of the O(N^2) time complexity for a copy of a graph. And the biggest problem was, how can I copy a graph with some components without missing any component on the way and without a double-loop?<br>
The solution was to make a simple BFS algorithm, everytime the queue poll a node  it adds to the new graph, and every node needs its neighbors ofc, I took care of it too.<br>
About copying each component - I wrote the BFS code inside a while loop,   each loop represent a different components and may have a huge amount of data.   to get to each component, at the first loop I inserted all the node of the old graph inside  a HashSet, each node will be deleted from the set when it found in the loop.   (I used this set because the delete and contains time complexity of this DS is O(1) which could be essential).<br>
If there’s more nodes left after the first loop it will loop to the next components till the set is empty.<br>
In the end the method returns a new copied graph.<br>
This method <strong><strong>time complexity</strong> is O(|N|+|E|+|C|),</strong> but could be a bit more because of the while loops, C=|components|.</p>
<h3 id="connection"><code>connection()</code></h3>
<p>This method is under the boolean isConnected() method using the BFS algorithm:<br>
a queue contains a temp path of unvisited node, I used a counter to count all the unvisited nodes from this connectivity,<br>
each node is marked with a tag, 1 - visited, -1 - not visited.<br>
This method <strong>time complexity is - O(N+E)</strong>, |Vertex| = N , |Edge| = E.</p>
<h3 id="dijkstranode_info-srcnode_info-dest"><code>Dijkstra(node_info src,node_info dest)</code></h3>
<p>About the Dijkstra algorithm:<br>
I learned about this algorithm via YouTube videos and from the video that boaz shared.<br>
I used a Priority-Queue and a Comparator for a specific priority of the weight of each node from the src to dest.<br>
The first nodes in the queue is having the less distance from the source.<br>
I also used a HashMap to own each node’s parents in the case I find the dest so I’ll be able to go back<br>
to the source and to contain each node on the way inside an ArrayList (the path).<br>
Each node that I met on the way is will own its distance to the source.<br>
In the end of the process the algo is going to the makeAPath method for the final job.<br>
This method <strong>time complexity is  - O(E+NlogN)</strong>, |Vertex| = N , |Edge| = E.</p>
<h3 id="makeapathnode_info-next"><code>makeAPath(node_info next)</code></h3>
<p>filling an ArrayList of node_info, it contains the path from source to destination.<br>
I used the parent HashMap (previous node) to go back to the source while adding<br>
each node to the list with the addFirst method.<br>
If the destination isn't reachable, the method returns an empty ArrayList.<br>
This method <strong>time complexity is  - O(D)</strong> ,D = |the number of nodes of this path|.</p>
<p><img src="https://media.geeksforgeeks.org/wp-content/uploads/dijikstra.png" alt="enter image description here"></p>
<h2 id="junit-5-tests">JUnit 5 Tests</h2>
<ul>
<li>CompareDistanceTest</li>
<li>WGraph_AlgoTest</li>
<li>WGraph_DSTest</li>
</ul>
<h2 id="sources">Sources</h2>
<ul>
<li>Dijkstra’s Algorithm- <a href="https://www.coursera.org/lecture/advanced-data-structures/core-dijkstras-algorithm-2ctyF">https://www.coursera.org/lecture/advanced-data-structures/core-dijkstras-algorithm-2ctyF</a></li>
<li>BFS Algorithm - <a href="https://youtu.be/oDqjPvD54Ss">https://youtu.be/oDqjPvD54Ss</a></li>
<li>Introduction to Java Programming by Daniel Liang, 10th Edition, Chapter 28 - Graphs and Applications.</li>
<li>JUnit 5 User Guide - <a href="https://junit.org/junit5/docs/5.0.2/user-guide/index.pdf">https://junit.org/junit5/docs/5.0.2/user-guide/index.pdf</a></li>
<li>Runtime Complexity of Java Collections - <a href="https://gist.github.com/psayre23/c30a821239f4818b0709">https://gist.github.com/psayre23/c30a821239f4818b0709</a></li>
</ul>
</div>
</body>

</html>
