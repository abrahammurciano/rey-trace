package xml.factories.element;

import java.util.Map;
import org.w3c.dom.Element;
import geometries.GeometryList;
import geometries.Geometry;
import xml.Util;
import xml.XmlParserException;
import static java.util.Map.entry;


/**
 * Constructs a {@link GeometryList} object from an XML {@link Element}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlGeometriesFactory extends XmlFactoryFromElement<GeometryList> {
	//@formatter:off
	private static final Map<String, XmlFactoryFromElement<? extends Geometry>> FACTORIES = Map.ofEntries(
		entry("cylinder", new XmlCylinderFactory()),
		entry("plane", new XmlPlaneFactory()),
		entry("polygon", new XmlPolygonFactory()),
		entry("sphere", new XmlSphereFactory()),
		entry("triangle", new XmlTriangleFactory()),
		entry("tube", new XmlTubeFactory())
	);
	//@formatter:on

	@Override
	protected GeometryList createHelper(Element element) {
		GeometryList geometries = new GeometryList();
		for (Element child : Util.getChildren(element)) {
			try {
				if (child.getNodeName().equals("geometries")) {
					geometries.add(create(element));
				} else {
					geometries.add(FACTORIES.get(child.getNodeName()).create(child));
				}
			} catch (NullPointerException e) {
				throw new XmlParserException("Unknown geometry \"" + child.getNodeName() + "\" in <geometries>.", e);
			}
		}
		return geometries;
	}

}
