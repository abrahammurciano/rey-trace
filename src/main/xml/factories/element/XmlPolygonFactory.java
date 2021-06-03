package xml.factories.element;

import java.util.LinkedList;
import java.util.List;
import org.w3c.dom.Element;
import geometries.Polygon;
import primitives.Point;
import xml.XmlParserException;
import xml.factories.attribute.XmlTripleFactory;


/**
 * Constructs a {@link Polygon} from an XML {@link Element}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlPolygonFactory extends XmlGeometryFactory {

	private XmlTripleFactory<Point> FACTORY = new XmlTripleFactory<>(Point::new);

	@Override
	public Polygon createHelper(Element element) {
		List<Point> points = new LinkedList<>();
		for (int i = 1; !element.getAttribute("p" + i).isEmpty(); ++i) {
			points.add(FACTORY.create(element, "p" + i));
		}
		try {
			return new Polygon(material(element), points.toArray(new Point[0]));
		} catch (IllegalArgumentException e) {
			throw new XmlParserException("The given points cannot form a valid convex polygon.", e);
		}
	}

}
