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
public class XmlPlaneFactory extends XmlGeometryFactory {

	private static final XmlTripleFactory<Point> FACTORY = new XmlTripleFactory<>(Point::new);

	@Override
	public Plane createHelper(Element element) {
		Point p0 = FACTORY.create(element, "p0");
		Point p1 = FACTORY.create(element, "p1");
		Point p2 = FACTORY.create(element, "p2");
		return new Plane(material(element), p0, p1, p2);
	}

}
