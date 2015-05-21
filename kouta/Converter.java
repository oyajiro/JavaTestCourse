package kouta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * Converter is a public final class for convert from text to html.
 * It required input text information and use dictionary of words.
 * As result we receive list of html files divided by Lines count.
 * @author kouta
 *
 */
public final class Converter {
	/**
	 * File with inputed plain text.
	 */
	private String inputFile;
	/**
	 * File with words which needed to be replaced.
	 */
	private String dictFile;
	/**
	 * Html file contains text with replaced words.
	 */
	private String resultFile;
	/**
	 * Automatically added header for html files.
	 */
	private String header = "<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"UTF-8\" />\n<title>Test task</title>\n</head>\n<body><p>";
	/**
	 * Automatically added footer for html files.
	 */
	private String footer = "</p></body>";
	/**
	 * Lines limit per html file.
	 */
	private int lineCount;

	/**
	 * Default soft limit for html file.
	 */
	static final int LINE_COUNT = 100;
	/**
	 * Hard limit for html file.
	 */
	static final int HARD_LIMIT = 105;

	/**
	 * Take args as input, invoke constructor and convert method.
	 * @param args InputFile name, dictFile name, resultFile name and lineCount value
	 */
	public static void main(final String[] args) {
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
				System.err.println("Argument" + args[3]
						+ " must be an integer.");
			}
		}

		converter.convert();
	}

	/** Default Constructor.
	 *  The default behaviour of this object is
	 *  <ul>
	 *  <li>inputFile is "./txt/input.txt"</li>
	 *  <li>dictFile is "./txt/dict.txt"</li>
	 *  <li>resultFile is "./html/index.html"</li>
	 *  <li>lineCount from constant LINE_COUNT</li>
	 *  </ul>
	 */
	public Converter() {
		super();
		this.inputFile = "./txt/input.txt";
		this.dictFile = "./txt/dict.txt";
		this.resultFile = "./html/index.html";
		this.lineCount = Converter.LINE_COUNT;
	}

	/**
	 * Main method for converting plain text to Html.
	 * Open inputFile and line by line replace words from dict to bold italic word
	 * If lines count with headers more than lineCount, new file created
	 * If last line was sentence with dot, it divide line by dot.
	 */
	public void convert() {
		String sCurrentLine;

		try (BufferedReader br = new BufferedReader(new FileReader(
				this.inputFile))) {
			try (BufferedReader dr = new BufferedReader(new FileReader(
					this.dictFile))) {

				String resultLines;
				Set<String> dict = new HashSet<String>();

				while ((sCurrentLine = dr.readLine()) != null) {
					dict.add(sCurrentLine);
				}

				sCurrentLine = "";
				resultLines = "";
				int counter = 1;
				int i = 0;
				int headersLineCount = this.header.split("\r\n|\r|\n").length
						+ this.footer.split("\r\n|\r|\n").length;
				List<String> sentence;

				while ((sCurrentLine = br.readLine()) != null) {

					if ((headersLineCount + counter >= this.lineCount && sCurrentLine.matches("\\.[\r\n|\r|\n|\\s]"))
							|| headersLineCount + counter > Converter.HARD_LIMIT) {
						sentence = Arrays.asList(sCurrentLine.split("\\.[\r\n|\r|\n|\\s|<br />]"));

						if (sentence.size() > 1) {
							resultLines += this.convertToCursiveBold(
									sentence.get(0) + ".", dict);
						} else {
							resultLines += this.convertToCursiveBold(
									sentence.get(0), dict);
						}

						sCurrentLine = StringUtils.join(
								sentence.subList(1, sentence.size()), ".");
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
					System.out.println("Done file " + (i + 1));
				} else {
					System.out.println("Fail");
				}

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Replace word to bold italic word in text line.
	 * @param line input text
	 * @param dict Dict of words which needed to replace
	 * @return return line with replaced words
	 */
	public String convertToCursiveBold(final String line, final Set<String> dict) {
		String tempResult = line;
		for (String word : dict) {
			tempResult = tempResult.replaceAll("\\b" + word + "\\b", "<b><i>"
					+ word + "</i></b>");
		}

		return tempResult + "<br />\n";
	}

	/**
	 * Write file with counter after file name.
	 * @param text Content of file
	 * @param counter If provided add to end of filename
	 * @return boolean result of writing operation
	 */
	public boolean writeHtml(final String text, final int counter) {
		boolean result = false;
		String filename = this.resultFile;
		if (counter > 0) {
			filename = FilenameUtils.removeExtension(this.resultFile) + counter
					+ "." + FilenameUtils.getExtension(this.resultFile);
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
			System.out.println(e.getMessage());
		}

		return result;
	}

}
