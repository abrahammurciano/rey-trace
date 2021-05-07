package xml.factories.element;

import org.w3c.dom.Element;

/**
 * This interface represents a class which is able to create instances of type T from an XML {@link Element}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public interface XmlFactoryFromElement<T> {
	/**
	 * Construct a new instance of type T from an XML {@link Element}.
	 *
	 * @param element The XML {@link Element} representing the object to be created.
	 * @return A new instance of type T created from the XML {@link Element}.
	 */
	T create(Element element);
}
