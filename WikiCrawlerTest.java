import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WikiCrawlerTest {

    @Test
    public void extractLinksTest1() {
        String seed = "/wiki/Stubb_Place";
        int max = 10;
        String[] topics = {"ball", "gravity"};
        String output = "web_graph.txt";

        WikiCrawler wc = new WikiCrawler(seed, max, topics, output);

        List<String> actual = wc.extractLinks2(wc.getDocument(wc.getSeed()));
        List<String> expected = Arrays.asList(
                "/wiki/Cumbria",
                "/wiki/Lake_District",
                "/wiki/National_parks_of_England_and_Wales",
                "/wiki/Stubb_Place",
                "/wiki/Main_Page"
        );

        assertEquals(expected, actual);
    }

    @Test
    public void extractLinksTest2() {
        String seed = "/wiki/Chomphu,_Lampang";
        int max = 10;
        String[] topics = {"place"};
        String output = "web_graph.txt";

        WikiCrawler wc = new WikiCrawler(seed, max, topics, output);

        List<String> actual = wc.extractLinks2(wc.getDocument(wc.getSeed()));
        List<String> expected = Arrays.asList(
                "/wiki/Thai_language",
                "/wiki/Tambon",
                "/wiki/Mueang_Lampang_District",
                "/wiki/Lampang_Province",
                "/wiki/Thailand",
                "/wiki/Chomphu,_Lampang",
                "/wiki/Main_Page"
        );

        assertEquals(expected, actual);
    }
}