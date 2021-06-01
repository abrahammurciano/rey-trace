package xml.factories.attribute;

import xml.XmlParserException;

/**
 * Constructs doubles from XML attributes.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlIntegerFactory extends XmlFactoryFromAttribute<Integer> {

	@Override
	protected Integer create(String attribute) {
		try {
			return Integer.parseInt(attribute);
		} catch (NumberFormatException e) {
			throw new XmlParserException("Expected a double but found \"" + attribute + '"', e);
		}
	}

}
