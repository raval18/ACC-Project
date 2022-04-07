package com.searchEngine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	
	private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> links = new LinkedList<String>();
    public static List<String> crawledLinks = new LinkedList<String>();
    int numberofCrawledLinks = 0;
    int count = 1;
    private Document htmlDocument;
	
	public List<String> getCrawledLinks() {
		return crawledLinks;
	}

	public void setCrawledLinks(List<String> crawledLinks) {
		this.crawledLinks = crawledLinks;
	}
	
	public static boolean checkUrls(String url) {
		
		if(crawledLinks.contains(url)) {
			System.out.println("already crawled.");
			return false;
		}
		
        if(url.contains("facebook")) {
            System.out.println("Blocked facebook");
            return false;
        }
        if(url.contains("instagram")) {
            System.out.println("Blocked instagram");
            return false;
        }
        if(url.contains("twitter")) {
            System.out.println("Blocked twitter");
            return false;
        }
        if(url.contains("linkedin")) {
            System.out.println("Blocked linkedin");
            return false;
        }
        if(url.contains("ads")) {
            return false;
        }
        
        return true;
	}

	public boolean crawl(String url,int flag)
    {
		if(checkUrls(url) == false) {
			return false;
		}
       
		numberofCrawledLinks++;
		crawledLinks.add(url);
		
        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            if(connection.response().statusCode() == 200) // 200 is the HTTP OK status code
                                                          // indicating that everything is great.
            {
            	if(flag == 0)
            		System.out.println("\n*Visiting* Received web page at " + url);
                try {
        			
    					org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
    					
    					String text = doc.text();
//        					System.out.println(text);
    					File file1 = new File("data/"+"text/"+ "w3schools"+ count +".txt");
//        					System.out.println("creating file: " + file1);
    					file1.createNewFile();
    					PrintWriter writer = new PrintWriter(file1);
    					writer.println(url);
    					writer.println(text);
    					writer.close();
    					
    					String html = doc.html();
    					File file2 = new File("data/"+"html/"+ "w3schools" + count+".html");
    					file2.createNewFile();
    					writer = new PrintWriter(file2);
    					//writer.println("link");
    					writer.println(html);
    					writer.close();
    					if(flag == 0)
    						System.out.println("file created: " + count);
    					count++;
        		} 
        		catch (Exception e) {
        			//e.printStackTrace();
        		}
            }
            if(!connection.response().contentType().contains("text/html"))
            {
            	if(flag == 0)
            		System.out.println("*Failure* Retrieved something other than HTML");
                return false;
            }
            
            Elements linksOnPage = htmlDocument.select("a[href]");
            if(flag == 0)
            	System.out.println("Found (" + linksOnPage.size() + ") links");
            
            for(Element link : linksOnPage)
            {
            	if(checkUrls(link.toString())) {
            		this.links.add(link.absUrl("href"));
            	}
            }
            return true;
        }
        catch(IOException ioe)
        {
            // We were not successful in our HTTP request
            return false;
        }
	}
	
	public List<String> getLinks()
    {
        return this.links;
    }
	
	public void urlSiteMap() {
		Crawler C = new Crawler();
		SiteName site = new SiteName();
		C.crawledLinks.clear();
		System.out.println(C.crawl(site.getSiteName(),1));
		
		List<String> links = C.getLinks();
		try {
			
			File file1 = new File("data/"+"urls/"+ "urls.txt");
			file1.createNewFile();
			PrintWriter writer = new PrintWriter(file1);
		
			for(int i = 0;i<50;i++)
			{
				if(links.get(i) != "" && checkUrls(links.get(i)) && !crawledLinks.contains(links.get(i)))
				{
					crawledLinks.add(links.get(i));
					System.out.println("Link added to sitemap");
					writer.println(links.get(i));
					C.crawl(links.get(i),1);
					ArrayList<String> crawledUrls = new ArrayList<String>(C.getLinks());
					writer.println();
					writer.println(crawledUrls);
					crawledUrls.clear();
				}
				
			}
		
			writer.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		System.out.println(C.getLinks().size());
	}
	public static void main(String[] args) {
		Crawler C = new Crawler();
		
		System.out.println(C.crawl("https://www.w3schools.com/",1));
		
		List<String> links = C.getLinks();
		try {
			File file1 = new File("data/"+"urls/"+ "urls.txt");
//			System.out.println("creating file: " + file1);
		file1.createNewFile();
		PrintWriter writer = new PrintWriter(file1);
		
		for(int i = 0;i<50;i++)
		{
			if(links.get(i) != "" && checkUrls(links.get(i)))
			{
				writer.println(links.get(i));
				C.crawl(links.get(i),1);
				ArrayList<String> crawledUrls = new ArrayList<String>(C.getLinks());
				writer.println();
				writer.println(crawledUrls);
				crawledUrls.clear();
			}
			
		}
		
		writer.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		System.out.println(C.getLinks().size());
	}

}