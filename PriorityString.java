public class PriorityString {

    private String str;
    private int priority;

    public PriorityString(String str, int priority) {
        this.str = str;
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null)
            return false;

        if (getClass() != o.getClass())
            return false;

        PriorityString other = (PriorityString) o;
        return this.str.equals(other.str) && this.priority == other.priority;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
