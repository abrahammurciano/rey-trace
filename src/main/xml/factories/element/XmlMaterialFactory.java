package xml.factories.element;

import org.w3c.dom.Element;
import primitives.Colour;
import primitives.Factors;
import primitives.Material;
import xml.factories.attribute.XmlColourFactory;
import xml.factories.attribute.XmlDoubleFactory;
import xml.factories.attribute.XmlFactorsFactory;

public class XmlMaterialFactory implements XmlFactoryFromElement<Material> {

	private static final XmlDoubleFactory DOUBLE_FACTORY = new XmlDoubleFactory();
	private static final XmlColourFactory COLOUR_FACTORY = new XmlColourFactory();
	private static final XmlFactorsFactory FACTORS_FACTORY = new XmlFactorsFactory();

	@Override
	public Material create(Element element) {
		Colour emission = COLOUR_FACTORY.create(element.getAttribute("emission"), Colour.BLACK);
		double shine = DOUBLE_FACTORY.create(element.getAttribute("shine"));
		Factors diffuse = FACTORS_FACTORY.create(element.getAttribute("diffuse"));
		Factors specular = FACTORS_FACTORY.create(element.getAttribute("specular"));
		Factors ambient = FACTORS_FACTORY.create(element.getAttribute("ambient"), Factors.ONE);
		Factors reflectivity = FACTORS_FACTORY.create(element.getAttribute("reflectivity"), Factors.ZERO);
		Factors transparency = FACTORS_FACTORY.create(element.getAttribute("transparency"), Factors.ZERO);
		return new Material(emission, shine, ambient, diffuse, specular, reflectivity, transparency);
	}

}
