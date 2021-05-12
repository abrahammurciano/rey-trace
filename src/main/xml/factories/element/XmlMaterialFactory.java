package xml.factories.element;

import org.w3c.dom.Element;
import primitives.Colour;
import primitives.Material;
import xml.factories.attribute.XmlColourFactory;
import xml.factories.attribute.XmlDoubleFactory;
import xml.factories.attribute.XmlIntegerFactory;

public class XmlMaterialFactory implements XmlFactoryFromElement<Material> {

	private static final XmlDoubleFactory DOUBLE_FACTORY = new XmlDoubleFactory();
	private static final XmlIntegerFactory INTEGER_FACTORY = new XmlIntegerFactory();
	private static final XmlColourFactory COLOUR_FACTORY = new XmlColourFactory();

	@Override
	public Material create(Element element) {
		Colour emission = COLOUR_FACTORY.create(element.getAttribute("emission"));
		int shine = INTEGER_FACTORY.create(element.getAttribute("shine"));
		double diffuse = DOUBLE_FACTORY.create(element.getAttribute("diffuse"));
		double specular = DOUBLE_FACTORY.create(element.getAttribute("specular"));
		return new Material(emission, shine, diffuse, specular);
	}

}
