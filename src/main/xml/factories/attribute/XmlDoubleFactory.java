package xml.factories.attribute;

import xml.XmlParserException;

/**
 * Constructs doubles from XML attributes.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlDoubleFactory implements XmlFactoryFromAttribute<Double> {

	@Override
	public Double create(String attribute) {
		try {
			return Double.parseDouble(attribute);
		} catch (Exception e) {
			throw new XmlParserException("Expected a double but found \"" + attribute + '"', e);
		}
	}

}
