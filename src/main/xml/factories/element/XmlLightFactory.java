package xml.factories.element;

import org.w3c.dom.Element;
import lighting.LightSource;
import primitives.Colour;
import xml.factories.attribute.XmlColourFactory;


/**
 * This abstract class represents a class which is able to create {@link LightSource} instances from XML
 * {@link Element}s.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public abstract class XmlLightFactory implements XmlFactoryFromElement<LightSource> {
	private static final XmlColourFactory COLOUR_FACTORY = new XmlColourFactory();

	/**
	 * Given an XML element for a light source, it will extract and return the colour of it.
	 *
	 * @param element The XML element representing the light source.
	 * @return The colour of the light source according to the XML.
	 */
	protected Colour colour(Element element) {
		return COLOUR_FACTORY.create(element.getAttribute("colour"));
	}
}
