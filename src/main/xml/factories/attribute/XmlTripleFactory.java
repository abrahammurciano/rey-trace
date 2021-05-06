package xml.factories.attribute;

import primitives.Triple;
import primitives.TripleCreator;
import xml.XmlParserException;

/**
 * Constructs {@link Triple}s from XML attributes, according to the given {@link TripleCreator}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlTripleFactory<T extends Triple> implements XmlFactoryFromAttribute<T> {
	private TripleCreator<T> creator;

	/**
	 * Construct a triple factory which will create instances of the type returned by {@code creator}.
	 *
	 * @param creator A function which receives three doubles and returns a new {@link Triple}.
	 */
	public XmlTripleFactory(TripleCreator<T> creator) {
		this.creator = creator;
	}

	/**
	 * Constructs a new {@link Triple} from an XML attribute string.
	 *
	 * @param attribute The XML attribute, in the form "x y z" where x, y, and z are doubles.
	 * @return A new triple with the given coordinates.
	 * @throws XmlParserException if the input string was malformed.
	 */
	public T create(String attribute) {
		try {
			double[] values = AttributeParser.threeDoubles(attribute);
			return creator.create(values[0], values[1], values[2]);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new XmlParserException("Expected a string with three numbers but saw \"" + attribute + '"', e);
		}
	}
}
