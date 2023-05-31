import java.util.*;

public class Main {
    public static void main(String[] args) {
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");

        graph.addEdge("A", "B", 2.0);
        graph.addEdge("A", "C", 1.0);
        graph.addEdge("B", "D", 3.0);
        graph.addEdge("C", "D", 2.0);
        graph.addEdge("D", "E", 1.5);

        BreadthFirstSearch<String> bfs = new BreadthFirstSearch<>();
        List<Vertex<String>> bfsPath = bfs.search(graph.getVertices().get("A"), graph.getVertices().get("E"));
        System.out.println("BFS Path: " + getPathString(bfsPath));

        DijkstraSearch<String> dijkstra = new DijkstraSearch<>();
        List<Vertex<String>> dijkstraPath = dijkstra.search(graph.getVertices().get("A"), graph.getVertices().get("E"));
        System.out.println("Dijkstra Path: " + getPathString(dijkstraPath));
    }

    private static String getPathString(List<Vertex<String>> path) {
        StringBuilder sb = new StringBuilder();
        for (Vertex<String> vertex : path) {
            sb.append(vertex.getData()).append(" -> ");
        }
        if (sb.length() > 3) {
            sb.setLength(sb.length() - 4);
        }
        return sb.toString();
    }
}
