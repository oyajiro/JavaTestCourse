/**
 * 
 */
package kouta;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * @author user
 *
 */
public class ConverterTest {

	/**
	 * Test method for {@link kouta.Converter#convert(java.lang.String[])}.
	 */
	@Test
	public void testConvert() {
		Converter converter = new Converter();
		converter.inputFile = "./txt/test_input.txt";
		converter.dictFile = "./txt/test_dict.txt";
		assertEquals("<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"UTF-8\" />\n<title>Test task</title>\n</head>\n<body><p>A <b><i>a</i></b> abc a_a<br /></p></body>", converter.convert());
	}

	/**
	 * Test method for {@link kouta.Converter#generateHtmlFile(java.lang.String)}.
	 */
	@Test
	public void testGenerateHtmlFile() {
		Converter converter = new Converter();
		assertEquals("<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"UTF-8\" />\n<title>Test task</title>\n</head>\n<body><p>test</p></body>", converter.generateHtmlFile("test"));
	}
	
	/**
	 * Test method for {@link kouta.Converter#convertToCursiveBold(java.lang.String)}.
	 */
	@Test
	public void testConvertToCursiveBold() {
		Converter converter = new Converter();
		Set<String> dict = new HashSet<String>();
		dict.add("a");
		assertEquals("A <b><i>a</i></b> abc a_a<br />", converter.convertToCursiveBold("A a abc a_a", dict));
	}

}
