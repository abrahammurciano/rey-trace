package xml.factories.attribute;

import java.util.NoSuchElementException;
import java.util.Scanner;
import primitives.Colour;
import xml.XmlParserException;

/**
 * Constructs {@link Colour}s from XML attributes.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlColourFactory extends XmlFactoryFromAttribute<Colour> {
	/**
	 * Constructs a new {@link Colour} from an XML attribute string.
	 *
	 * @param attribute The XML attribute, in the form "r g b" where r, g, and b are integers between 0 and 255
	 *                  representing the red, green, and blue values.
	 * @return A new colour with the given values.
	 * @throws XmlParserException if the input string was malformed.
	 */
	protected Colour create(String attribute) {
		try (Scanner scanner = new Scanner(attribute)) {
			return new Colour(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble());
		} catch (NoSuchElementException e) {
			throw new XmlParserException("Expected a string with three numbers but saw \"" + attribute + '"', e);
		}
	}
}
