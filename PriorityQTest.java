import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PriorityQTest {

    private PriorityQ pq;

    @BeforeEach
    public void setUpPriorityQ() {
        pq = new PriorityQ();
        pq.add("test1", 2);
        pq.add("test2", 4);
        pq.add("test3", 1);
        pq.add("test4", 5);
    }

    @Test
    public void addTest() {
        PriorityString[] expected = new PriorityString[100];
        expected[0] = new PriorityString("test4", 5);
        expected[1] = new PriorityString("test2", 4);
        expected[2] = new PriorityString("test3", 1);
        expected[3] = new PriorityString("test1", 2);

        for (int i = 0; i < pq.getData().length; i++) {
            assertEquals(pq.getData()[i], expected[i]);
        }
    }

    @Test
    public void returnMaxTest() {
        PriorityString actual = pq.returnMax();
        PriorityString expected = new PriorityString("test4", 5);

        assertEquals(expected, actual);
    }

    @Test
    public void extractMaxTest() {
        PriorityString max = pq.extractMax();
        PriorityString expectedMax = new PriorityString("test4", 5);

        PriorityString[] expectedPriorityQArr = new PriorityString[100];
        expectedPriorityQArr[0] = new PriorityString("test2", 4);
        expectedPriorityQArr[1] = new PriorityString("test1", 2);
        expectedPriorityQArr[2] = new PriorityString("test3", 1);

        assertEquals(expectedMax, max);

        for (int i = 0; i < pq.getSize(); i++) {
            assertEquals(expectedPriorityQArr[i], pq.getData()[i]);
        }
    }

    @Test
    public void removeTest() {
        pq.add("test5", 1);
        pq.remove(1);

        PriorityString[] expected = new PriorityString[100];
        expected[0] = new PriorityString("test4", 5);
        expected[1] = new PriorityString("test1", 2);
        expected[2] = new PriorityString("test3", 1);
        expected[3] = new PriorityString("test5", 1);

        for (int i = 0; i < pq.getSize(); i++) {
            assertEquals(expected[i], pq.getData()[i]);
        }
    }

    @Test
    public void decrementPriorityTest() {
        pq.decrementPriority(1, 3);

        PriorityString[] expected = new PriorityString[100];
        expected[0] = new PriorityString("test4", 5);
        expected[1] = new PriorityString("test1", 2);
        expected[2] = new PriorityString("test3", 1);
        expected[3] = new PriorityString("test2", 1);

        for (int i = 0; i < pq.getSize(); i++) {
            assertEquals(expected[i], pq.getData()[i]);
        }
    }

    @Test
    public void priorityArrayTest() {
        int[] expected = {5, 4, 1, 2};
        assertArrayEquals(expected, pq.priorityArray());
    }

    @Test
    public void getKeyTest() {
        int expected = 5;
        int actual = pq.getKey(0);
        assertEquals(expected, actual);
    }

    @Test
    public void getValueTest() {
        String expected = "test4";
        String actual = pq.getValue(0);
        assertEquals(expected, actual);
    }

    @Test
    public void isEmptyTest() {
        PriorityQ emptyPq = new PriorityQ();
        assertTrue(emptyPq.isEmpty());
        assertFalse(pq.isEmpty());
    }
}