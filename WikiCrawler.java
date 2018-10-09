import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A class for crawling wiki pages.
 *
 * @author Scott Huffman
 * @author John Jago
 */
public class WikiCrawler {

    private static final String BASE_URL = "https://en.wikipedia.org";

    private String seed;
    private int max;
    private String[] topics;
    private String output;

    private int crawlCounter = 0;

    public WikiCrawler(String seed, int max, String[] topics, String output) {
        this.seed = seed;
        this.max = max;
        this.topics = topics;
        this.output = output;
    }

    public ArrayList<String> extractLinks(String document) {
        ArrayList<String> links = new ArrayList<>();

        for (int i = 0; i < document.length() - 2; i++) {
            boolean firstParagraphTag = document.substring(i, i + 3).equals("<p>");
            if (firstParagraphTag) {
                document = document.substring(i);
                break;
            }
        }

        int i = 0;
        while (i < document.length() - 6) {
            String sequence = document.substring(i, i + 7);
            boolean isLinkSequence = sequence.equals("\"/wiki/");
            if (isLinkSequence) {
                i += 6;
                char currentCharacter = document.charAt(++i);
                String link = sequence.substring(1) + currentCharacter;
                currentCharacter = document.charAt(++i);
                boolean hasSpecialCharacter = false;
                while (currentCharacter != '"') {
                    hasSpecialCharacter = currentCharacter == '#' || currentCharacter == ':';
                    if (hasSpecialCharacter) break;
                    link += currentCharacter;
                    currentCharacter = document.charAt(++i);
                }
                if (!hasSpecialCharacter && !links.contains(link)) {
                    links.add(link);
                }
            }
            i++;
        }

        return links;
    }

    public void crawl(boolean focused) {
        if (focused) {
            this.focusedCrawl();
        } else {
            this.notFocusedCrawl();
        }
    }

    public String getSeed() {
        return seed;
    }

    public int getMax() {
        return max;
    }

    public String[] getTopics() {
        return topics;
    }

    public String getOutput() {
        return output;
    }

    public String getDocument(String pageUrl) {
        URL url;
        this.crawlCounter++;
        if (this.crawlCounter % 20 == 0) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            url = new URL(BASE_URL + pageUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "Malformed URL";
        }

        InputStream is;
        BufferedReader br;

        try {
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));
            StringBuilder document = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                document.append(line);
            }
            return document.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "An error occurred";
        }
    }

    private int getCount(String substring, String page) {
        String temp = page.replaceAll(substring, "");
        return (page.length() - temp.length()) / substring.length();
    }

    private int computeRelevance(String page) {
        int sum = 0;
        for (String topic : this.topics) {
            sum += this.getCount(topic, page);
        }
        return sum;
    }

    private void focusedCrawl() {
        // for every page a during bfs search
            // compute relevance(t, a)
            // add to pq, extract with extractMax
        // write to output file
    }

    private void notFocusedCrawl() {
        // do unfocused crawl
    }
}
