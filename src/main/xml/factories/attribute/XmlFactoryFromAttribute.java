package xml.factories.attribute;

/**
 * This interface represents a class which constructs an object of type T from an XML attribute string.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public interface XmlFactoryFromAttribute<T> {
	/**
	 * Create an instance of type T from the given attribute string.
	 *
	 * @param attribute The XML attribute string from which to construct the T.
	 * @return An instance of type T whose value is the data in the given string.
	 * @throws XmlParserException if the attribute was in the wrong format.
	 */
	public T create(String attribute);

	/**
	 * Get and create an object from an XML attribute, if it exists, otherwise return the default value.
	 *
	 * @param attribute    The name of the attribute string.
	 * @param defaultValue The default value to return if the attribute does not exist.
	 * @return The object of type T created from the attribute string.
	 * @throws XmlParserException if the attribute was in the wrong format.
	 */
	public default T create(String attribute, T defaultValue) {
		return attribute.isEmpty() ? defaultValue : create(attribute);
	}
}
