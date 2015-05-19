package kouta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Converter {
	public static String inputFile;
	public static String dictFile;
	
	public static void main(String[] args) {
		Converter.inputFile = "./txt/input.txt";
		Converter.dictFile = "./txt/dict.txt";
		
		if (args.length > 0 && args[0] != "") {
			Converter.inputFile = args[0];
		}
		
		if (args.length > 1 && args[1] != "") {
			Converter.dictFile = args[1];
		}
		
		Converter.convert();
	}

	public static void convert() {
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(Converter.inputFile)))
		{
			try (BufferedReader dr = new BufferedReader(new FileReader(Converter.dictFile))) {
 
				String sCurrentLine;
				String resultLine;
				Set<String> dict = new HashSet<String>();
				
				while ((sCurrentLine = dr.readLine()) != null) {
					dict.add(sCurrentLine);
				}
				
				sCurrentLine = "";
				resultLine = "";
						
				while ((sCurrentLine = br.readLine()) != null) {
					
					resultLine += Converter.convertToCursiveBold(sCurrentLine, dict);
				}
				System.out.println(Converter.generateHtmlFile(resultLine));
			
			} catch (IOException e) {
				e.printStackTrace();
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		}
 
	}
	
	public static String generateHtmlFile(String text) {
		String header = "<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"UTF-8\" />\n<title>Test task</title>\n</head>\n<body><p>";
		String footer = "</p></body>";
		return header + text + footer;
	}
	
	public static String convertToCursiveBold(String line, Set<String> dict) {
		String tempResult = line;
		for (String word : dict) {
			tempResult = tempResult.replaceAll("\\b" + word + "\\b", "<b><i>" + word + "</i></b>");
		}
		
		return tempResult + "\n";
	}
	
}
