package xml.factories.attribute;

import org.w3c.dom.Element;
import xml.XmlParserException;

/**
 * This interface represents a class which constructs an object of type T from an XML attribute string.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public abstract class XmlFactoryFromAttribute<T> {
	/**
	 * Create an instance of type T from the given attribute string.
	 *
	 * @param attribute The XML attribute string from which to construct the T.
	 * @return An instance of type T whose value is the data in the given string.
	 * @throws XmlParserException if the attribute was in the wrong format.
	 */
	protected abstract T create(String attribute);

	/**
	 * Get and create an object from an XML attribute, if it exists, otherwise throw an {@link XmlParserException}.
	 *
	 * @param element  The XML element containing the required attribute.
	 * @param attrName The name of the attribute string.
	 * @return The object of type T created from the attribute string.
	 * @throws XmlParserException if the attribute was in the wrong format.
	 */
	public T create(Element element, String attrName) {
		String attribute = element.getAttribute(attrName);
		if (attribute.isEmpty()) {
			throw new XmlParserException("Attribute \"" + attrName + "\" is required.");
		}
		return create(attribute);
	}

	/**
	 * Get and create an object from an XML attribute, if it exists, otherwise return the default value.
	 *
	 * @param element      The XML element containing the required attribute.
	 * @param attrName     The name of the attribute string.
	 * @param defaultValue The default value to return if the attribute does not exist.
	 * @return The object of type T created from the attribute string.
	 * @throws XmlParserException if the attribute was in the wrong format.
	 */
	public T create(Element element, String attrName, T defaultValue) {
		String attribute = element.getAttribute(attrName);
		return attribute.isEmpty() ? defaultValue : create(attribute);
	}
}
