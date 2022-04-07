package com.searchEngine;

import java.util.Scanner;

import com.searchEngine.Data;
//import com.searchEngine.Function;
import com.searchEngine.StdOut;
import com.searchEngine.*;

public class Choices {
	
	public static void options() {
		String menuOptions = "" +
				 "\nSearch Engine" + 
				 "\n1 - Get the data from the site (www.w3school.com)" + 
				 "\n2 - Search the pattern in URL list" +
				 "\n3 - Search Keywords using String matching"  + 
				 "\n4 - The Web search in that you will pass the URL" +
				 "\n0 - Exit" + 
				 "\nSelect an Option from Menu: ";

				StdOut.println(menuOptions);
	}
	
	

	public static void functions(String dataPath) {
		boolean displayMenu = true;
		Scanner input = new Scanner(System.in);
		Scanner sc = new Scanner(System.in);
		while(displayMenu) {
			
			options();
	        
		    try {
		    	
		        int option = input.nextInt();
		        switch (option) {
		        case 1:  
		        		System.out.println("Fetching data...");
		        		Data.createFiles();
		        		System.out.println("Data fetched.");
		                break;
		        case 2: 
			        	FindUrls.findUrlPattern();
		        		break;
		        case 3:
		        		WebSearchEngine.websearch();
		                break;
		        case 4:
		        		
	        			WebSearchUrl.findUrl();
	        			break;		
		        case 0:
		        		displayMenu = false;
		        		System.out.println("Exiting Program!");
		        		System.exit(0);
		        		break;
		        default:
			        	System.out.print("The entered value is unrecognized!\n\n");
			        	break;
		       }
		    } catch (Exception e) { 
		    	StdOut.println("Enter input in correct format");
		    	input.next();
		    }
	    }

	}

}
