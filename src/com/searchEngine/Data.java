package com.searchEngine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

public class Data {
	static void deleteFiles(File file) {
	    if (file.isDirectory())
	        for (File f : file.listFiles())
	            deleteFiles(f);
	    else
	        file.delete();
	}
	public static void createFiles() {
		Crawler C = new Crawler();
		SiteName site = new SiteName();
		deleteFiles(new File("data/text"));
		deleteFiles(new File("data/html"));
		C.crawl(site.getSiteName(),0);
		
		List<String> initialLinks = C.getLinks();
				
		int fileCount = 2;
		for(int i = 0; i<C.getLinks().size(); i++)
		{
			if(initialLinks.get(i) != "")
			{
				C.crawl(initialLinks.get(i),0);
				System.out.println("crawledLinks: " + C.numberofCrawledLinks);
				fileCount++;
			}
			
			if(C.numberofCrawledLinks > site.getNumberOfUrls())
			{
				System.out.println("crawledLinks: " + C.numberofCrawledLinks);
				break;
			}
		}
		deleteFiles(new File("data/urls"));
		C.urlSiteMap();
	}

	public static void main(String[] args) {
		Data D = new Data();
		
		D.createFiles();
	}

}
