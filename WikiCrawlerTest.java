import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WikiCrawlerTest {

    @Test
    public void extractLinksTest() {
        String seed = "/wiki/Stubb_Place";
        int max = 10;
        String[] topics = {"ball", "gravity"};
        String output = "web_graph.txt";

        WikiCrawler wc = new WikiCrawler(seed, max, topics, output);

        List<String> actual = wc.extractLinks(wc.getDocument(wc.getSeed()));
        List<String> expected = Arrays.asList(
                "/wiki/Cumbria",
                "/wiki/Lake_District",
                "/wiki/National_parks_of_England_and_Wales",
                "/wiki/Cumbria",
                "/wiki/Stubb_Place",
                "/wiki/Stubb_Place",
                "/wiki/Main_Page",
                "/wiki/Main_Page",
                "/wiki/Terms_of_Use",
                "/wiki/Privacy_policy",
                "/wiki/Privacy_policy",
                "/wiki/Cookie_statement"
        );

        assertEquals(expected, actual);
    }
}