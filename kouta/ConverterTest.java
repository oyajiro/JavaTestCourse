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
	 * Test method for {@link kouta.Converter#convertToCursiveBold(java.lang.String)}.
	 */
	@Test
	public void testConvertToCursiveBold() {
		Converter converter = new Converter();
		Set<String> dict = new HashSet<String>();
		dict.add("a");
		assertEquals("A <b><i>a</i></b> abc a_a<br />\n", converter.convertToCursiveBold("A a abc a_a", dict));
	}

}
