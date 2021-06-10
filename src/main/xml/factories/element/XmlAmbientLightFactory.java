package xml.factories.element;

import org.w3c.dom.Element;
import lighting.AmbientLight;
import xml.factories.attribute.XmlColourFactory;
import primitives.Colour;
import xml.XmlParserException;

/**
 * Constructs an {@link AmbientLight} from an XML {@link Element}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlAmbientLightFactory extends XmlFactoryFromElement<AmbientLight> {
	private static final XmlColourFactory COLOUR_FACTORY = new XmlColourFactory();

	/**
	 * Constructs a new {@link AmbientLight} from an XML {@link Element}.
	 *
	 * @param element The XML element, containing an attribute "colour" whose value can be parsed into a {@link Colour}.
	 * @return A new AmbientLight with the given values.
	 * @throws XmlParserException if the XML element did not have a valid colour attribute.
	 */
	protected AmbientLight createHelper(Element element) {
		return new AmbientLight(COLOUR_FACTORY.create(element, "colour"));
	}

}
