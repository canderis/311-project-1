import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    private Set<Vertex> vertices;
    private Set<Edge> edges;
    private Map<Vertex, Set<Vertex>> adjacencyList;

    public Graph() {
        vertices = new HashSet<>();
        edges = new HashSet<>();
        adjacencyList = new HashMap<>();
    }

    public void addVertex(String label) {
        vertices.add(new Vertex(label));
    }

    public void removeVertex(String label) {
        vertices.remove(new Vertex(label));
    }

    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    public void addEdge(Edge e) {
        edges.add(e);
        adjacencyList.putIfAbsent(e.getVertex(1), new HashSet<>());
        adjacencyList.putIfAbsent(e.getVertex(2), new HashSet<>());
        adjacencyList.get(e.getVertex(1)).add(e.getVertex(2));
        adjacencyList.get(e.getVertex(2)).add(e.getVertex(1));
    }

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public Map<Vertex, Set<Vertex>> getAdjacencyList() {
        return adjacencyList;
    }
}
