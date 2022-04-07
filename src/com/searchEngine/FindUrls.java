package com.searchEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindUrls {
	public static void findURL(String text,String urlPattern) {
		int flag = 0;
		String pattern = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(text);
	    while (m.find( )) {
	    	if(m.group().contains(urlPattern)) {
	    		System.out.println("URL: " + m.group());
	    		flag = 1;}
	    } 
	    if(flag == 0)
	    	System.out.println("Not found");
	    
	}
	static void findURLsInFile(String filePath,String urlPattern) {
		In inputFile = new In(filePath);
		String textFromFile = inputFile.readAll();
		findURL(textFromFile,urlPattern);
	}
	
	static void findUrlPattern() {
		System.out.println("Enter the pattern you want to search in urls");
		Scanner sc = new Scanner(System.in);
		String urlPattern = sc.nextLine();
		findURLsInFile("data/urls/urls.txt",urlPattern);
		
		
	}
}
