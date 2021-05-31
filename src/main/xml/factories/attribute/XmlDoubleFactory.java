package xml.factories.attribute;

import xml.XmlParserException;

/**
 * Constructs doubles from XML attributes.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlDoubleFactory extends XmlFactoryFromAttribute<Double> {

	@Override
	protected Double create(String attribute) {
		try {
			return Double.parseDouble(attribute);
		} catch (NumberFormatException e) {
			throw new XmlParserException("Expected a double but found \"" + attribute + '"', e);
		}
	}

}
