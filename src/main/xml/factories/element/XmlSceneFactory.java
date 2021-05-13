package xml.factories.element;

import java.util.List;
import org.w3c.dom.Element;
import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Colour;
import scene.Scene;
import xml.Util;
import xml.XmlParserException;
import xml.factories.attribute.XmlColourFactory;

/**
 * Constructs a {@link Scene} from XML {@link Element}s.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlSceneFactory implements XmlFactoryFromElement<Scene> {
	/**
	 * Constructs a new {@link Scene} from an XML {@link Element}.
	 *
	 * @param element The XML {@link Element} representing the scene.
	 * @return A scene with the data specified by the XML.
	 * @throws XmlParserException if the XML was malformed.
	 */
	public Scene create(Element element) {
		Colour background = new XmlColourFactory().create(element.getAttribute("background-colour"));

		AmbientLight ambient;
		try {
			ambient = new XmlAmbientLightFactory().create(Util.getChild(element, "ambient-light"));
		} catch (XmlParserException __) {
			ambient = new AmbientLight(Colour.BLACK);
		}

		Geometries geometries = new XmlGeometriesFactory().create(Util.getChild(element, "geometries"));

		List<LightSource> lights = new XmlLightSourcesFactory().create(Util.getChild(element, "lights"));

		return new Scene(background, ambient, geometries, lights);
	}
}
