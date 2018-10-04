import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WikiCrawler {

    private static final String BASE_URL = "https://en.wikipedia.org";

    private String seed;
    private int max;
    private String[] topics;
    private String output;

    public WikiCrawler(String seed, int max, String[] topics, String output) {
        this.seed = seed;
        this.max = max;
        this.topics = topics;
        this.output = output;
    }

    public ArrayList<String> extractLinks(String document) {
        ArrayList<String> links = new ArrayList<>();

        int i;
        for (i = 0; i < document.length() - 2; i++) {
            boolean firstParagraphTag = document.substring(i, i + 3).equals("<p>");
            if (firstParagraphTag) {
                document = document.substring(i);
                break;
            }
        }

        Pattern p = Pattern.compile("\\/wiki\\/[^#:]+?(?=\")");
        Matcher m = p.matcher(document);

        while (m.find()) {
            links.add(m.group());
        }

        return links;
    }

    public void crawl(boolean focused) {

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
}
