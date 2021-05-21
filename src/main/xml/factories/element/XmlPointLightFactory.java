package xml.factories.element;

import org.w3c.dom.Element;
import primitives.Point;
import xml.factories.attribute.XmlDoubleFactory;
import xml.factories.attribute.XmlTripleFactory;

public class XmlPointLightFactory extends XmlLightFactory {

	private static final XmlTripleFactory<Point> POINT_FACTORY = new XmlTripleFactory<>(Point::new);
	private static final XmlDoubleFactory DOUBLE_FACTORY = new XmlDoubleFactory();

	@Override
	public PointLight create(Element element) {
		Point position = POINT_FACTORY.create(element.getAttribute("position"));

		double quadratic = DOUBLE_FACTORY.create(element.getAttribute("quadratic"), 0d);
		double linear = DOUBLE_FACTORY.create(element.getAttribute("linear"), 0d);
		double constant = DOUBLE_FACTORY.create(element.getAttribute("constant"), 0d);
		return new PointLight(colour(element), position, quadratic, linear, constant);
	}

}
