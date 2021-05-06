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
	 */
	T create(String attribute);
}
