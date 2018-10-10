/**
 * Represents an edge in a graph.
 *
 * @author Scott Huffman
 * @author John Jago
 */
public class Edge {
    private Vertex v0;
    private Vertex v1;

    public Edge(Vertex v0, Vertex v1) {
        this.v0 = v0;
        this.v1 = v1;
    }

    public Edge(String v0, String v1) {
        this.v0 = new Vertex(v0);
        this.v1 = new Vertex(v1);
    }

    public Vertex get(int i) {
        if (i == 0) {
            return v0;
        }
        return v1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null)
            return false;

        if (getClass() != o.getClass())
            return false;

        Edge other = (Edge) o;
        return this.v0.equals(other.v0) && this.v1.equals(other.v1);
    }

    @Override
    public int hashCode() {
        return v0.hashCode() + v1.hashCode();
    }
}