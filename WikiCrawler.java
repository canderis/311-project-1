import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

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

    public ArrayList<String> extractLinks2(String document) {
        ArrayList<String> links = new ArrayList<>();

//        int firstP = 0;
//        while(firstP < document.length() - 3) {
//        	if(document.charAt(firstP) == '<') {
//        		firstP++;
//        		if(document.charAt(firstP) == 'p') {
//        			firstP++;
//        			if(document.charAt(firstP) == '>') {
//            			firstP++;
//            			break;
//            		}
//        		}
//        	}
//        	else {
//        		firstP++;
//        	}
//        }

        int firstP = document.indexOf("<p>");

        int i = firstP;


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
            PriorityString v = pq.extractMax();
            pageCount++;
            List<String> links = extractLinks(getDocument(v.getStr()));
            for (String link : links) {
                String page = getDocument(link);
                if (!discovered.contains(link) && pageContainsKeywords(page)) {
                    pq.add(link, computeRelevance(page));
                    discovered.add(link);
                    edges.add(new Edge(v.getStr(), link));
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
        // do unfocused crawl
        int i = 0;
        //HashMap<String, Boolean> discovered = new HashMap<String, Boolean>();
        String document = this.getDocument(this.seed);
        if(!this.pageContainsKeywords(document) ) {
        	return;
        }

        ArrayList<String> output = new ArrayList<String>();
        HashSet<String> irrelevantLinks = new HashSet<String>();

        LinkedHashMap<String, ArrayList<String>> links = new LinkedHashMap<String, ArrayList<String>>();
        links.put(this.seed, this.extractLinks2(document));

        int currentKey = 0;
        ArrayList<String> keys = new ArrayList<String>();
        keys.add(this.seed);

        while(currentKey < keys.size()){
        	String key = keys.get(currentKey);
        	i++;
        	System.out.println("iteration" + i);
//        	System.out.println("max" + this.max);

        	for (String link : links.get(key)) {

        		if(irrelevantLinks.contains(link)) {
        			continue;
        		}

        		if(links.containsKey(link)) {
        			//output parent -> this link
        			output.add(key + ' ' + link);
        			continue;
        		}


        		document = this.getDocument(link);
        		if(this.pageContainsKeywords(document) ) {
//        			System.out.println("here " + link);
        			links.put(link, this.extractLinks2(document));
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
