package xml.factories.element;

import org.w3c.dom.Element;
import geometries.Triangle;
import primitives.Point;
import xml.factories.attribute.XmlTripleFactory;


/**
 * Constructs a {@link Triangle} from an XML {@link Element}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlTriangleFactory extends XmlGeometryFactory {

	@Override
	public Triangle create(Element element) {
		XmlTripleFactory<Point> factory = new XmlTripleFactory<>(Point::new);
		Point p0 = factory.create(element.getAttribute("p0"));
		Point p1 = factory.create(element.getAttribute("p1"));
		Point p2 = factory.create(element.getAttribute("p2"));
		return new Triangle(material(element), p0, p1, p2);
	}

}
