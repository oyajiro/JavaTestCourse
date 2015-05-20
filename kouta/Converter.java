package kouta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.FilenameUtils;

public class Converter {
	public String inputFile;
	public String dictFile;
	public String resultFile;
	public String header = "<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"UTF-8\" />\n<title>Test task</title>\n</head>\n<body><p>";
	public String footer = "</p></body>";
	public int lineCount;

	private int LINE_COUNT = 11;
	
	public static void main(String[] args) {
		Converter converter = new Converter();
		
		if (args.length > 0 && args[0] != "") {
			converter.inputFile = args[0];
		}
		
		if (args.length > 1 && args[1] != "") {
			converter.dictFile = args[1];
		}
		
		if (args.length > 2 && args[2] != "") {
			converter.resultFile = args[2];
		}
		
		if (args.length > 3) {
			try {
				converter.lineCount = Integer.parseInt(args[3]);
		    } catch (NumberFormatException e) {
		        System.err.println("Argument" + args[3] + " must be an integer.");
		    }
		}
		
		converter.convert();
	}

	public Converter() {
		super();
		this.inputFile = "./txt/input.txt";
		this.dictFile = "./txt/dict.txt";
		this.resultFile = "./html/index.html";
		this.lineCount = this.LINE_COUNT;
	}

	public void convert() {
		String sCurrentLine;
		
		try (BufferedReader br = new BufferedReader(new FileReader(this.inputFile)))
		{
			try (BufferedReader dr = new BufferedReader(new FileReader(this.dictFile))) {
 
				String resultLines;
				Set<String> dict = new HashSet<String>();
				
				
				while ((sCurrentLine = dr.readLine()) != null) {
					dict.add(sCurrentLine);
				}
				
				sCurrentLine = "";
				resultLines = "";
				int counter = 1;
				int i = 0;
				int headersLineCount = this.header.split("\r\n|\r|\n").length + this.footer.split("\r\n|\r|\n").length;
				List<String> sentence;

				while ((sCurrentLine = br.readLine()) != null) {
					
					if (headersLineCount + counter >= this.lineCount && sCurrentLine.contains(".")) {
						sentence = Arrays.asList(sCurrentLine.split("\\.[\r\n|\r|\n|\\s|<br />]"));
						
						if (sentence.size() > 1) {
							resultLines += this.convertToCursiveBold(sentence.get(0) + ".", dict);
						} else {
							resultLines += this.convertToCursiveBold(sentence.get(0), dict);
						}
						
						sCurrentLine = StringUtils.join(sentence.subList(1, sentence.size()),".");
						if (this.writeHtml(resultLines, i++)) {
							System.out.println("Done file " + i);
							counter = 1;
							resultLines = "";
						} else {
							System.out.println("Fail");
						}
							
					}
					
					resultLines += this.convertToCursiveBold(sCurrentLine, dict);
					counter++;
				}

				if (this.writeHtml(resultLines, i)) {
					System.out.println("Done file " + (i+1));
				} else {
					System.out.println("Fail");
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String generateHtmlFiles(String text) {
		String header = "<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"UTF-8\" />\n<title>Test task</title>\n</head>\n<body><p>";
		String footer = "</p></body>";
		
		String[] headerLines = header.split("\r\n|\r|\n");
		List<String> textLines = Arrays.asList(text.split("\n"));
		String[] footerLines = footer.split("\r\n|\r|\n");
		ArrayList<String> result = new ArrayList<String>();
		int TotalLength = headerLines.length +  textLines.size() + footerLines.length;

		if (TotalLength > this.lineCount) {
			int step = headerLines.length + footerLines.length - this.lineCount;
			int end;
			int i = 0;
			String beforeDot = "";
			String afterDot = "";
			while (i < textLines.size()) {
				end = this.getLineWithDot(i + step, textLines);
				
				if (end > textLines.size() - 1) {
					end = textLines.size() - 1;
				}

				List<String> sentence = Arrays.asList(textLines.get(end).split("\\.\\s|\\.<br />"));
				beforeDot = sentence.get(0);
				
				result.add(header + afterDot + StringUtils.join(textLines.subList(i, end), "\n") + beforeDot + "." + footer);
				if (sentence.size() > 1) {
					textLines.set(end, StringUtils.join(sentence.subList(1, sentence.size()),"."));
				}
				
				if (end > 0) {
					i += end;
				} else {
					i++;
				}
			}
			
		} else {
			result.add(header + text + footer);
		}
		return "";
	}
	
	public String convertToCursiveBold(String line, Set<String> dict) {
		String tempResult = line;
		for (String word : dict) {
			tempResult = tempResult.replaceAll("\\b" + word + "\\b", "<b><i>" + word + "</i></b>");
		}
		
		return tempResult + "<br />\n";
	}
	
	public boolean writeHtml(String text, int counter) {
		boolean result = false;
		String filename = this.resultFile;
		if (counter > 0) {
			filename = FilenameUtils.removeExtension(this.resultFile) + counter + "." + FilenameUtils.getExtension(this.resultFile);
		}
		try {
			 
			File file = new File(filename);
 
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(this.header + text + this.footer);
			bw.close();
 
			result = true;
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int getLineWithDot(int position, List<String> list) {
		int result = 0;
		if (position >= list.size() -1) {
			result = list.size() -1;
		} else {
			String line = list.get(position);
			
			if (line.contains(". ")) {
				result = position;
			} else {
				result = this.getLineWithDot(position + 1, list);
			}
		}
		return result;
	}
	
}
