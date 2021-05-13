package xml.factories.element;

import org.w3c.dom.Element;
import geometries.Sphere;
import primitives.Point;
import xml.factories.attribute.XmlDoubleFactory;
import xml.factories.attribute.XmlTripleFactory;


/**
 * Constructs a {@link Sphere} from an XML {@link Element}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlSphereFactory extends XmlGeometryFactory {
	@Override
	public Sphere create(Element element) {
		Point center = new XmlTripleFactory<Point>(Point::new).create(element.getAttribute("center"));
		double radius = new XmlDoubleFactory().create(element.getAttribute("radius"));
		return new Sphere(material(element), center, radius);
	}

}
