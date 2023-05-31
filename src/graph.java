import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

class Edge<Vertex> {
    private Vertex source;
    private Vertex dest;
    private Double weight;

    public Edge(Vertex source, Vertex dest, Double weight) {
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }
}

class Vertex<V> {
    private V data;
    private Map<Vertex<V>, Double> adjacentVertices;

    public Vertex(V data) {
        this.data = data;
        this.adjacentVertices = new HashMap<>();
    }

    public void addAdjacentVertex(Vertex<V> destination, double weight) {
        adjacentVertices.put(destination, weight);
    }
    public Map<Vertex<V>, Double> getAdjacentVertices() {
        return adjacentVertices;
    }

    public char[] getData() {
        return (char[]) data;
    }
}
class WeightedGraph<T> {
    private Map<T, Vertex<T>> vertices;

    public WeightedGraph() {
        this.vertices = new HashMap<>();
    }
    public void addVertex(T data) {
        if (!vertices.containsKey(data)) {
            vertices.put(data, new Vertex<>(data));
        }
    }

    public void addEdge(T sourceData, T destData, double weight) {
        Vertex<T> source = vertices.get(sourceData);
        Vertex<T> dest = vertices.get(destData);

        if (source != null && dest != null) {
            source.addAdjacentVertex(dest, weight);
        }
    }

    public ThreadLocal<Object> getVertices() {
        return (ThreadLocal<Object>) vertices;
    }
}
interface Search<T> {
    List<Vertex<T>> search(Vertex<T> start, Vertex<T> goal);
}
class BreadthFirstSearch<T> implements Search<T> {
    @Override
    public List<Vertex<T>> search(Vertex<T> start, Vertex<T> goal) {
        List<Vertex<T>> path = new ArrayList<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Map<Vertex<T>, Vertex<T>> parentMap = new HashMap<>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Vertex<T> current = queue.poll();

            if (current == goal) {
                buildPath(path, parentMap, goal);
                break;
            }

            for (Vertex<T> neighbor : current.getAdjacentVertices().keySet()) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }

        return path;
    }

    private void buildPath(List<Vertex<T>> path, Map<Vertex<T>, Vertex<T>> parentMap, Vertex<T> current) {
        if (parentMap.containsKey(current)) {
            buildPath(path, parentMap, parentMap.get(current));
        }
        path.add(current);
    }
}
class DijkstraSearch<T> implements Search<T> {
    @Override
    public List<Vertex<T>> search(Vertex<T> start, Vertex<T> goal) {
        List<Vertex<T>> path = new ArrayList<>();
        Map<Vertex<T>, Double> distances = new HashMap<>();
        Map<Vertex<T>, Vertex<T>> parentMap = new HashMap<>();
        PriorityQueue<Vertex<T>> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        Set<Vertex<T>> visited = new HashSet<>();

        distances.put(start, 0.0);
        queue.offer(start);

        while (!queue.isEmpty()) {
            Vertex<T> current = queue.poll();
            if (current == goal) {
                buildPath(path, parentMap, goal);
                break;
            }
            visited.add(current);

            for (Map.Entry<Vertex<T>, Double> entry : current.getAdjacentVertices().entrySet()) {
                Vertex<T> neighbor = entry.getKey();
                double weight = entry.getValue();

                double newDistance = distances.get(current) + weight;
                if (!distances.containsKey(neighbor) || newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    parentMap.put(neighbor, current);
                }

                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                }
            }
        }

        return path;
    }

    private void buildPath(List<Vertex<T>> path, Map<Vertex<T>, Vertex<T>> parentMap, Vertex<T> current) {
        if (parentMap.containsKey(current)) {
            buildPath(path, parentMap, parentMap.get(current));
        }
        path.add(current);
    }
}
