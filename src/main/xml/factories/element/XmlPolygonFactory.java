package xml.factories.element;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
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
public class XmlPolygonFactory extends XmlGeometryFactory {

	private XmlTripleFactory<Point> FACTORY = new XmlTripleFactory<>(Point::new);

	@Override
	public Polygon create(Element element) {
		List<Point> points = new LinkedList<>();
		Integer i = 0;
        String attr, name;
		while(true) {
            name = "p" + i.toString();
            attr = element.getAttribute(name);
            try {
                points.add(FACTORY.create(attr));
            } catch(NoSuchElementException e) { // wrong exception?
                break;
            }
            ++i;
		}
		return new Polygon(material(element), points.toArray(new Point[0]));
	}

}
