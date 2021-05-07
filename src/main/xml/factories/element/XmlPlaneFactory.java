package xml.factories.element;

import org.w3c.dom.Element;
import geometries.Plane;
import primitives.Point;
import xml.factories.attribute.XmlTripleFactory;

/**
 * Constructs a {@link Plane} from an XML {@link Element}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlPlaneFactory implements XmlGeometryFactory {

	private static final XmlTripleFactory<Point> FACTORY = new XmlTripleFactory<>(Point::new);

	@Override
	public Plane create(Element element) {
		Point p0 = FACTORY.create(element.getAttribute("p0"));
		Point p1 = FACTORY.create(element.getAttribute("p1"));
		Point p2 = FACTORY.create(element.getAttribute("p2"));
		return new Plane(p0, p1, p2);
	}

}
