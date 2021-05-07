package xml.factories.element;

import java.util.LinkedList;
import java.util.List;
import org.w3c.dom.Element;
import geometries.Polygon;
import primitives.Point;
import xml.factories.attribute.XmlTripleFactory;


/**
 * Constructs a {@link Polygon} from an XML {@link Element}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlPolygonFactory implements XmlGeometryFactory {

	private XmlTripleFactory<Point> FACTORY = new XmlTripleFactory<>(Point::new);

	@Override
	public Polygon create(Element element) {
		List<Point> points = new LinkedList<>();
		int i = 1;
		for (String attr = element.getAttribute("p" + i); attr.length() > 0; ++i) {
			points.add(FACTORY.create(attr));
		}
		return new Polygon(points.toArray(new Point[0]));
	}

}
