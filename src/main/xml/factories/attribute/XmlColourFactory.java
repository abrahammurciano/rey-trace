package xml.factories.attribute;

import primitives.Colour;
import xml.XmlParserException;

/**
 * Constructs {@link Colour}s from XML attributes.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlColourFactory implements XmlFactoryFromAttribute<Colour> {
	/**
	 * Constructs a new {@link Colour} from an XML attribute string.
	 *
	 * @param attribute The XML attribute, in the form "r g b" where r, g, and b are integers between 0 and 255
	 *                  representing the red, green, and blue values.
	 * @return A new colour with the given values.
	 * @throws XmlParserException if the input string was malformed.
	 */
	public Colour create(String attribute) {
		try {
			int[] values = AttributeParser.threeInts(attribute);
			return new Colour(values[0], values[1], values[2]);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new XmlParserException("Expected a string with three numbers but saw \"" + attribute + '"', e);
		}
	}
}
