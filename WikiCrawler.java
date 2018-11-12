import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * A class for crawling wiki pages.
 *
 * @author Scott Huffman
 * @author John Jago
 */
public class WikiCrawler {

    private static final String BASE_URL = "http://web.cs.iastate.edu/~pavan";

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
        int firstP = document.indexOf("<p>");
        int i = document.indexOf("\"/wiki/", firstP);

        while (i != -1) {
            String s = document.substring(i + 1, document.indexOf('"', i + 1));
            i += s.length();
            i = document.indexOf("\"/wiki", i);
            if (s.contains("#") || s.contains(":")) {
                continue;
            }
            if (!links.contains(s)) {
                links.add(s);
            }
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
        PriorityQ pq = new PriorityQ();
        List<String> discovered = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        int pageCount = 1;

        String seed = getDocument(this.seed);
        if (!pageContainsKeywords(seed)) {
            return;
        }
        pq.add(this.seed, computeRelevance(seed));
        discovered.add(this.seed);

        while (!pq.isEmpty() && pageCount <= this.max) {
            String v = pq.extractMax();
            pageCount++;
            List<String> links = extractLinks(getDocument(v));
            for (String link : links) {
                String page = getDocument(link);
                if (!discovered.contains(link) && pageContainsKeywords(page)) {
                    pq.add(link, computeRelevance(page));
                    discovered.add(link);
                    edges.add(new Edge(v, link));
                }
            }
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(this.output), "utf-8"))) {
            writer.write(this.max + "\n");
            for (Edge e : edges) {
                writer.write( e.get(0).getLabel() + " " + e.get(1).getLabel() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean pageContainsKeywords(String document) {

    	for (String topic : this.topics) {
            if (document.indexOf(topic) == -1 ) {
            	return false;
            }
        }

    	return true;
    }

    private void notFocusedCrawl() {
        int i = 0;
        String document = this.getDocument(this.seed);
        if(!this.pageContainsKeywords(document) ) {
        	return;
        }

        ArrayList<String> output = new ArrayList<String>();
        HashSet<String> irrelevantLinks = new HashSet<String>();

        LinkedHashMap<String, ArrayList<String>> links = new LinkedHashMap<String, ArrayList<String>>();
        links.put(this.seed, this.extractLinks(document));

        int currentKey = 0;
        ArrayList<String> keys = new ArrayList<String>();
        keys.add(this.seed);

        while(currentKey < keys.size()){
        	String key = keys.get(currentKey);
        	i++;

        	for (String link : links.get(key)) {
        	    if(key.equals(link)){
        	        continue;
                }

        		if(irrelevantLinks.contains(link)) {
        			continue;
        		}

        		if(links.containsKey(link)) {
        			output.add(key + ' ' + link);
        			continue;
        		}

        		document = this.getDocument(link);
        		if(this.pageContainsKeywords(document) ) {
        			links.put(link, this.extractLinks(document));
        			output.add(key + ' ' + link);
        			keys.add(link);
        		}
        		else {
        			irrelevantLinks.add(link);
        		}
        	}
        	currentKey++;

        	if(i >= this.max) {
    			break;
    		}
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(this.output), "utf-8"))) {
            writer.write(this.max + "\n");
            for (String s : output) {
                writer.write( s + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
