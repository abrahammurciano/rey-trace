package xml.factories.element;

import org.w3c.dom.Element;
import geometries.Geometry;


/**
 * This interface represents a class which is able to create {@link Geometry} instances from XML {@link Element}s.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public interface XmlGeometryFactory extends XmlFactoryFromElement<Geometry> {
	/**
	 * Construct a new {@link Geometry} from an XML {@link Element}.
	 *
	 * @param element The XML {@link Element} representing the object to be created.
	 * @return A new {@link Geometry} created from the XML {@link Element}.
	 */
	Geometry create(Element element);
}
