package xml.factories.element;

import org.w3c.dom.Element;
import geometries.Cylinder;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;
import xml.factories.attribute.XmlDoubleFactory;
import xml.factories.attribute.XmlTripleFactory;


/**
 * Constructs a {@link Cylinder} from an XML {@link Element}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlCylinderFactory extends XmlGeometryFactory {

	private static final XmlTripleFactory<NormalizedVector> NORMALIZED_VECTOR_FACTORY =
		new XmlTripleFactory<>(NormalizedVector::new);
	private static final XmlTripleFactory<Point> POINT_FACTORY = new XmlTripleFactory<>(Point::new);
	private static final XmlDoubleFactory DOUBLE_FACTORY = new XmlDoubleFactory();

	@Override
	public Cylinder create(Element element) {
		Point source = POINT_FACTORY.create(element.getAttribute("source"));
		NormalizedVector direction = NORMALIZED_VECTOR_FACTORY.create(element.getAttribute("direction"));
		Ray ray = new Ray(source, direction);
		double radius = DOUBLE_FACTORY.create(element.getAttribute("radius"));
		double height = DOUBLE_FACTORY.create(element.getAttribute("height"));
		return new Cylinder(material(element), ray, radius, height);
	}

}
