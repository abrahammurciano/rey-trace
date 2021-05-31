package xml.factories.element;

import org.w3c.dom.Element;
import xml.XmlParserException;

/**
 * This interface represents a class which is able to create instances of type T from an XML {@link Element}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public abstract class XmlFactoryFromElement<T> {
	/**
	 * Construct a new instance of type T from an XML {@link Element}. This method is intended to be called by
	 * {@link #create(Element)}.
	 *
	 * @param element The XML {@link Element} representing the object to be created.
	 * @return A new instance of type T created from the XML {@link Element}.
	 */
	protected abstract T createHelper(Element element);

	/**
	 * Construct a new instance of type T from an XML {@link Element}.
	 *
	 * @param element The XML {@link Element} representing the object to be created.
	 * @return A new instance of type T created from the XML {@link Element}.
	 * @throws XmlParserException if the XML {@link Element} is unable to be parsed into an instance of type T.
	 */
	public T create(Element element) {
		try {
			return createHelper(element);
		} catch (XmlParserException e) {
			e.addToTrace(element);
			throw e;
		}
	}
}
