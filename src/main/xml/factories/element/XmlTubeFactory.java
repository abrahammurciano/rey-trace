package xml.factories.element;

import org.w3c.dom.Element;
import geometries.Tube;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;
import xml.factories.attribute.XmlDoubleFactory;
import xml.factories.attribute.XmlTripleFactory;


/**
 * Constructs a {@link Tube} from an XML {@link Element}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlTubeFactory implements XmlGeometryFactory {

	@Override
	public Tube create(Element element) {
		Point source = new XmlTripleFactory<Point>(Point::new).create(element.getAttribute("source"));
		NormalizedVector direction =
			new XmlTripleFactory<NormalizedVector>(NormalizedVector::new).create(element.getAttribute("direction"));
		Ray axis = new Ray(source, direction);
		double radius = new XmlDoubleFactory().create(element.getAttribute("radius"));
		return new Tube(axis, radius);
	}

}
