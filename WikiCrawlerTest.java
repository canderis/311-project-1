import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WikiCrawlerTest {

//    @Test
//    public void extractLinksTest1() {
//        String seed = "/wiki/Stubb_Place";
//        int max = 5;
//        String[] topics = {"england", "town"};
//        String output = "web_graph_stubb_place.txt";
//        WikiCrawler wc = new WikiCrawler(seed, max, topics, output);
//
//        List<String> actual = wc.extractLinks2(wc.getDocument(wc.getSeed()));
//        List<String> expected = Arrays.asList(
//                "/wiki/Cumbria",
//                "/wiki/Lake_District",
//                "/wiki/National_parks_of_England_and_Wales",
//                "/wiki/Stubb_Place",
//                "/wiki/Main_Page"
//        );
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void extractLinksTest2() {
//        String seed = "/wiki/Chomphu,_Lampang";
//        int max = 10;
//        String[] topics = {"place"};
//        String output = "web_graph.txt";
//        WikiCrawler wc = new WikiCrawler(seed, max, topics, output);
//
//        List<String> actual = wc.extractLinks2(wc.getDocument(wc.getSeed()));
//        List<String> expected = Arrays.asList(
//                "/wiki/Thai_language",
//                "/wiki/Tambon",
//                "/wiki/Mueang_Lampang_District",
//                "/wiki/Lampang_Province",
//                "/wiki/Thailand",
//                "/wiki/Chomphu,_Lampang",
//                "/wiki/Main_Page"
//        );
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void focusedCrawlTest1() {
//        String seed = "/wiki/Chomphu,_Lampang";
//        int max = 5;
//        String[] topics = {"thailand", "food"};
//        String output = "web_graph_chomphu.txt";
//        WikiCrawler wc = new WikiCrawler(seed, max, topics, output);
//
//        wc.crawl(true);
//    }
//
//    @Test
//    public void focusedCrawlTest2() {
//        String seed = "/wiki/Complexity_theory";
//        int max = 3;
//        String[] topics = {};
//        String output = "web_graph_wikiCC.txt";
//        WikiCrawler wc = new WikiCrawler(seed, max, topics, output);
//
//        wc.crawl(true);
//    }
//
//    @Test
//    public void focusedCrawlTest3() {
//        String seed = "/wiki/A.html"; // must change base URL
//        int max = 6;
//        String[] topics = {};
//        String output = "web_graph_pavan_sample.txt";
//        WikiCrawler wc = new WikiCrawler(seed, max, topics, output);
//
////        wc.crawl(true);
//    }

    @Test
    public void unfocusedCrawlTest1() {
        String seed = "/wiki/Complexity_theory"; // must change base URL
        int max = 3;
        String[] topics = {"information", "theory", "science", "data"};
        String output = "web_graph_wikiCC.txt";
        WikiCrawler wc = new WikiCrawler(seed, max, topics, output);

        wc.crawl(true);
    }

    @Test
    public void focusedCrawlTest3() {
        String seed = "/wiki/A.html"; // must change base URL
        int max = 6;
        String[] topics = {};
        String output = "web_graph_pavan_sample.txt";
        WikiCrawler wc = new WikiCrawler(seed, max, topics, output);

        wc.crawl(false);
    }
}
