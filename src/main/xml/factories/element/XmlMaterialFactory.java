package xml.factories.element;

import org.w3c.dom.Element;
import primitives.Colour;
import primitives.Material;
import xml.factories.attribute.XmlColourFactory;
import xml.factories.attribute.XmlDoubleFactory;

public class XmlMaterialFactory implements XmlFactoryFromElement<Material> {

	private static final XmlDoubleFactory DOUBLE_FACTORY = new XmlDoubleFactory();
	private static final XmlColourFactory COLOUR_FACTORY = new XmlColourFactory();

	@Override
	public Material create(Element element) {
		double shine = DOUBLE_FACTORY.create(element.getAttribute("shine"));
		double diffuse = DOUBLE_FACTORY.create(element.getAttribute("diffuse"));
		double specular = DOUBLE_FACTORY.create(element.getAttribute("specular"));
		double ambient = DOUBLE_FACTORY.create(element.getAttribute("ambient"), 1d);
		Colour colour = COLOUR_FACTORY.create(element.getAttribute("colour"), Colour.BLACK);
		Colour emission = COLOUR_FACTORY.create(element.getAttribute("emission"), Colour.BLACK);
		return new Material(colour, emission, shine, ambient, diffuse, specular);
	}

}
