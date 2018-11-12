/**
 * A priority queue.
 *
 * @author Scott Huffman
 * @author John Jago
 */
public class PriorityQ {

    private PriorityString[] data;
    private int size;

    public PriorityQ() {
        data = new PriorityString[100];
        size = 0;
    }

    public void add(String s, int p) {
        PriorityString newString = new PriorityString(s, p);
        data[size] = newString;
        size++;

        if (size > data.length - 1) {
            PriorityString[] newData = new PriorityString[data.length * 2];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }

        int i = size - 1;
        while (i != 0 && newString.getPriority() > data[(i - 1) / 2].getPriority()) {
            data[i] = data[(i - 1) / 2];
            i = (i - 1) / 2;
        }

        data[i] = newString;
    }

    public String returnMax() {
        return data[0].getStr();
    }

    public String extractMax() {
        String max = returnMax();
        data[0] = data[size - 1];
        size--;
        percolateDown(data, size, 0);

        return max;
    }

    public void remove(int i) {
        swap(i, size - 1);
        size--;
        percolateDown(data, size, i);
    }

    public void decrementPriority(int i, int o) {
        data[i].setPriority(data[i].getPriority() - o);
        percolateDown(data, size, i);
    }

    public int[] priorityArray() {
        int[] temp = new int[size];

        for (int i = 0; i < size; i++) {
            temp[i] = data[i].getPriority();
        }

        return temp;
    }

    public int getKey(int i) {
        return data[i].getPriority();
    }

    public String getValue(int i) {
        return data[i].getStr();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public PriorityString[] getData() {
        return data;
    }

    public int getSize() {
        return size;
    }

    private void percolateDown(PriorityString[] a, int size, int i) {
        int left = (2 * i) + 1;
        int right = (2 * i) + 2;

        int largest = i;

        if (left < size && a[left].getPriority() > a[largest].getPriority()) {
            largest = left;
        }

        if (right < size && a[right].getPriority() > a[largest].getPriority()) {
            largest = right;
        }

        if (largest != i) {
            swap(largest, i);
            percolateDown(a, size, largest);
        }
    }

    private void swap(int a, int b) {
        PriorityString temp = data[a];
        data[a] = data[b];
        data[b] = temp;
    }
}
