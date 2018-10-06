import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GraphTest {
    @Test
    public void addVertexTest() {
        Graph g = new Graph();
        g.addVertex("link1");

        Vertex v = new Vertex("link1");
        Set<Vertex> expected = new HashSet<>();
        expected.add(v);

        assertEquals(expected, g.getVertices());
    }

    @Test
    public void removeVertexTest() {
        Graph g = new Graph();
        g.addVertex("link1");
        g.removeVertex("link1");

        Set<Vertex> expected = new HashSet<>();

        assertEquals(expected, g.getVertices());
    }

    @Test
    public void addEdgeTest() {
        Graph g = new Graph();

        Vertex v1 = new Vertex("link1");
        Vertex v2 = new Vertex("link2");
        g.addVertex(v1);
        g.addVertex(v2);

        Edge e = new Edge(v1, v2);
        g.addEdge(e);

        Set<Edge> expectedEdges = new HashSet<>();
        expectedEdges.add(new Edge(v1, v2));
        Set<Edge> actualEdges = g.getEdges();
        assertEquals(expectedEdges, actualEdges);
    }
}