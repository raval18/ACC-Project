package com.searchEngine;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.net.HttpURLConnection;
import java.net.URL;

import com.webSearchEngine.Crawler1;
import com.webSearchEngine.Path;
import com.webSearchEngine.SearchWord;

public class WebSearchUrl {
	private static Scanner sc = new Scanner(System.in);
	public static void findUrl() {
			System.out.println("Enter URL...");
			String url = sc.next();
			System.out.println("Crawling Started...");
			Crawler1.startCrawler(url, 0);
			System.out.println("Crawling Compelted...");
	
			Hashtable<String, Integer> listOfFiles = new Hashtable<String, Integer>();
	
			String choice = "y";
			do {
				System.out.println("---------------------------------------------------");
				System.out.println("\n Enter the word to search ");
				String wordToSearch = sc.next();
				System.out.println("---------------------------------------------------");
				int frequency = 0;
				int noOfFiles = 0;
				
				try {
					System.out.println("\nSearching...");
					File files = new File(Path.txtDirectoryPath);
	
					File[] fileArray = files.listFiles();
	
					for (int i = 0; i < fileArray.length; i++) {
	
						In data = new In(fileArray[i].getAbsolutePath());
	
						String txt = data.readAll();
						Pattern p = Pattern.compile("::");
						String[] file_name = p.split(txt);
						frequency = SearchWord.wordSearch(txt, wordToSearch, file_name[0]);
	
						listOfFiles.put(file_name[0], frequency);
	
						if (frequency != 0) {
							noOfFiles++;
						}
	
					}
	
					if(noOfFiles>0) {
					System.out.println("\nTotal Number of Files containing word : " + wordToSearch + " is : " + noOfFiles);
					}else {
						System.out.println("\n File not found! containing word : "+ wordToSearch);
						SearchWord.suggestAltWord(wordToSearch);
	
					}
	
					SearchWord.rankFiles(listOfFiles, noOfFiles);
	
				} catch (Exception e) {
					System.out.println("Exception:" + e);
				}
				System.out.println("\n Do you want return to search another word(y/n)?");
				choice = sc.next();
			} while (choice.equals("y"));
			System.out.println("\n Do you want return to main menu(y/n)?");
			//return sc.next();
		}


}
