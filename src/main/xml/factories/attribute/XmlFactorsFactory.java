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
			double first = scanner.nextDouble();
			Factors result = new Factors(first);
			if (scanner.hasNextDouble()) {
				double second = scanner.nextDouble();
				double third = scanner.nextDouble();
				result = new Factors(first, second, third);
			}
			return result;
		} catch (NoSuchElementException e) {
			throw new XmlParserException("Expected a string with one or three numbers but saw \"" + attribute + '"', e);
		}
	}
}
