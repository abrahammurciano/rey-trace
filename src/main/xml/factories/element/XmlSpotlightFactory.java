package xml.factories.element;

import org.w3c.dom.Element;
import lighting.Spotlight;
import primitives.NormalizedVector;
import primitives.Point;
import xml.factories.attribute.XmlDoubleFactory;
import xml.factories.attribute.XmlTripleFactory;

public class XmlSpotlightFactory extends XmlLightFactory {

	private static final XmlTripleFactory<Point> POINT_FACTORY = new XmlTripleFactory<>(Point::new);
	private static final XmlTripleFactory<NormalizedVector> DIRECTION_FACTORY =
		new XmlTripleFactory<>(NormalizedVector::new);
	private static final XmlDoubleFactory DOUBLE_FACTORY = new XmlDoubleFactory();

	@Override
	public Spotlight createHelper(Element element) {
		Point position = POINT_FACTORY.create(element, "position");
		NormalizedVector direction = DIRECTION_FACTORY.create(element, "direction");
		double quadratic = DOUBLE_FACTORY.create(element, "quadratic", 0d);
		double linear = DOUBLE_FACTORY.create(element, "linear", 0d);
		double constant = DOUBLE_FACTORY.create(element, "constant", 1d);
		return new Spotlight(colour(element), position, direction, quadratic, linear, constant);
	}

}
