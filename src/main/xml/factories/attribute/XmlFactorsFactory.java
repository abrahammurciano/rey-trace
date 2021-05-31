package xml.factories.attribute;

import java.util.NoSuchElementException;
import java.util.Scanner;
import primitives.Factors;
import xml.XmlParserException;

/**
 * Constructs {@link Factors}s from XML attributes.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlFactorsFactory extends XmlFactoryFromAttribute<Factors> {
	private static final XmlDoubleFactory DOUBLE_FACTORY = new XmlDoubleFactory();

	/**
	 * Constructs a new {@link Factors} from an XML attribute string.
	 *
	 * @param attribute The XML attribute, in the form "r g b" where r, g, and b are integers between 0 and 255
	 *                  representing the red, green, and blue values.
	 * @return A new colour with the given values.
	 * @throws XmlParserException if the input string was malformed.
	 */
	protected Factors create(String attribute) {
		try (Scanner scanner = new Scanner(attribute)) {
			return new Factors(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble());
		} catch (NoSuchElementException e) {
			try {
				return new Factors(DOUBLE_FACTORY.create(attribute));
			} catch (XmlParserException __) {
				throw new XmlParserException("Expected a string with one or three numbers but saw \"" + attribute + '"',
					e);
			}
		}
	}
}
