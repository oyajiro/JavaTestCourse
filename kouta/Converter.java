package kouta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Converter {
	public String inputFile;
	public String dictFile;
	
	public static void main(String[] args) {
		Converter converter = new Converter();
		
		if (args.length > 0 && args[0] != "") {
			converter.inputFile = args[0];
		}
		
		if (args.length > 1 && args[1] != "") {
			converter.dictFile = args[1];
		}
		
		String result = converter.convert();
		System.out.println(result);
	}

	public Converter() {
		super();
		this.inputFile = "./txt/input.txt";
		this.dictFile = "./txt/dict.txt";
	}

	public String convert() {
		String result = "";
		
		try (BufferedReader br = new BufferedReader(new FileReader(this.inputFile)))
		{
			try (BufferedReader dr = new BufferedReader(new FileReader(this.dictFile))) {
 
				String sCurrentLine;
				String resultLine;
				Set<String> dict = new HashSet<String>();
				
				while ((sCurrentLine = dr.readLine()) != null) {
					dict.add(sCurrentLine);
				}
				
				sCurrentLine = "";
				resultLine = "";
						
				while ((sCurrentLine = br.readLine()) != null) {
					
					resultLine += this.convertToCursiveBold(sCurrentLine, dict);
				}
				
				result = this.generateHtmlFile(resultLine);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
 
	}
	
	public String generateHtmlFile(String text) {
		String header = "<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"UTF-8\" />\n<title>Test task</title>\n</head>\n<body><p>";
		String footer = "</p></body>";
		return header + text + footer;
	}
	
	public String convertToCursiveBold(String line, Set<String> dict) {
		String tempResult = line;
		for (String word : dict) {
			tempResult = tempResult.replaceAll("\\b" + word + "\\b", "<b><i>" + word + "</i></b>");
		}
		
		return tempResult + "<br />";
	}
	
}
