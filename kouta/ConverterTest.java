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
//		assertEquals("", Converter.main(new String[] { "./txt/input.txt", "./txt/dict.txt"});
	}

	/**
	 * Test method for {@link kouta.Converter#generateHtmlFile(java.lang.String)}.
	 */
	@Test
	public void testGenerateHtmlFile() {
		assertEquals("<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"UTF-8\" />\n<title>Test task</title>\n</head>\n<body><p>test</p></body>", Converter.generateHtmlFile("test"));
	}
	
	/**
	 * Test method for {@link kouta.Converter#convertToCursiveBold(java.lang.String)}.
	 */
	@Test
	public void testConvertToCursiveBold() {
		Set<String> dict = new HashSet<String>();
		dict.add("a");
		assertEquals("A <b><i>a</i></b> abc a_a\n", Converter.convertToCursiveBold("A a abc a_a", dict));
	}

}
