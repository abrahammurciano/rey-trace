package xml.factories.element;

import org.w3c.dom.Element;
import lighting.PointLight;
import primitives.Point;
import xml.factories.attribute.XmlDoubleFactory;
import xml.factories.attribute.XmlTripleFactory;

public class XmlPointLightFactory extends XmlLightFactory {

	private static final XmlTripleFactory<Point> POINT_FACTORY = new XmlTripleFactory<>(Point::new);
	private static final XmlDoubleFactory DOUBLE_FACTORY = new XmlDoubleFactory();

	@Override
	public PointLight createHelper(Element element) {
		Point position = POINT_FACTORY.create(element, "position");

		double quadratic = DOUBLE_FACTORY.create(element, "quadratic", 0d);
		double linear = DOUBLE_FACTORY.create(element, "linear", 0d);
		double constant = DOUBLE_FACTORY.create(element, "constant", 1d);
		return new PointLight(colour(element), position, quadratic, linear, constant);
	}

}
