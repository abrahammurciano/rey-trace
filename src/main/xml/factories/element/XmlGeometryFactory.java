package xml.factories.element;

import org.w3c.dom.Element;
import geometries.Geometry;
import primitives.Material;
import xml.Util;


/**
 * This interface represents a class which is able to create {@link Geometry} instances from XML {@link Element}s.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public abstract class XmlGeometryFactory implements XmlFactoryFromElement<Geometry> {
	private static final XmlMaterialFactory MATERIAL_FACTORY = new XmlMaterialFactory();

	/**
	 * Given an XML element for a geometry, it will extract and return the material of it.
	 *
	 * @param element The XML element representing the geometry.
	 * @return The material of the geometry according to the XML.
	 */
	protected Material material(Element element) {
		return MATERIAL_FACTORY.create(Util.getChild(element, "material"));
	}
}
