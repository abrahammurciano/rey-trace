package xml.factories.element;

import org.w3c.dom.Element;
import primitives.Colour;
import primitives.Factors;
import primitives.Material;
import xml.factories.attribute.XmlColourFactory;
import xml.factories.attribute.XmlDoubleFactory;
import xml.factories.attribute.XmlFactorsFactory;

public class XmlMaterialFactory extends XmlFactoryFromElement<Material> {

	private static final XmlDoubleFactory DOUBLE_FACTORY = new XmlDoubleFactory();
	private static final XmlColourFactory COLOUR_FACTORY = new XmlColourFactory();
	private static final XmlFactorsFactory FACTORS_FACTORY = new XmlFactorsFactory();

	@Override
	public Material createHelper(Element element) {
		Colour emission = COLOUR_FACTORY.create(element, "emission", Colour.BLACK);
		double shine = DOUBLE_FACTORY.create(element, "shine");
		Factors diffuse = FACTORS_FACTORY.create(element, "diffuse");
		Factors specular = FACTORS_FACTORY.create(element, "specular");
		Factors ambient = FACTORS_FACTORY.create(element, "ambient", Factors.ONE);
		Factors reflectivity = FACTORS_FACTORY.create(element, "reflectivity", Factors.ZERO);
		Factors transparency = FACTORS_FACTORY.create(element, "transparency", Factors.ZERO);
		return new Material(emission, shine, ambient, diffuse, specular, reflectivity, transparency);
	}

}
