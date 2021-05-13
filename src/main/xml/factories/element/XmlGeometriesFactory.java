package xml.factories.element;

import java.util.Map;
import org.w3c.dom.Element;
import geometries.Geometries;
import xml.Util;
import static java.util.Map.entry;


/**
 * Constructs a {@link Geometries} object from an XML {@link Element}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlGeometriesFactory implements XmlFactoryFromElement<Geometries> {
	//@formatter:off
	private static final Map<String, XmlGeometryFactory> FACTORIES = Map.ofEntries(
		entry("cylinder", new XmlCylinderFactory()),
		entry("plane", new XmlPlaneFactory()),
		entry("polygon", new XmlPolygonFactory()),
		entry("sphere", new XmlSphereFactory()),
		entry("triangle", new XmlTriangleFactory()),
		entry("tube", new XmlTubeFactory())
	);
	//@formatter:on

	@Override
	public Geometries create(Element element) {
		Geometries geometries = new Geometries();
		for (Element child : Util.getChildren(element)) {
			geometries.add(FACTORIES.get(child.getNodeName()).create(child));
		}
		return geometries;
	}

}
