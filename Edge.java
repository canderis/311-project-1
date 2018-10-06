public class Edge {
    private Vertex v1;
    private Vertex v2;

    public Edge(Vertex v1, Vertex v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Vertex getVertex(int i) {
        if (i == 1) return v1;
        return v2;
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
        return this.v1.equals(other.v1) && this.v2.equals(other.v2);
    }

    @Override
    public int hashCode() {
        return v1.hashCode() + v2.hashCode();
    }
}