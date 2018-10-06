public class Vertex {
    private String label;

    public Vertex(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String s) {
        this.label = s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null)
            return false;

        if (getClass() != o.getClass())
            return false;

        Vertex other = (Vertex) o;
        return this.label.equals(other.label);
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }
}