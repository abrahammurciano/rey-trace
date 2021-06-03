package xml.factories.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;
import lighting.LightSource;
import xml.Util;
import xml.XmlParserException;
import static java.util.Map.entry;

/**
 * This class is able to create a list of {@link LightSource} instances from an XML {@link Element}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlLightSourcesFactory extends XmlFactoryFromElement<List<LightSource>> {
	//@formatter:off
	private static final Map<String, XmlLightFactory> FACTORIES = Map.ofEntries(
		entry("spotlight", new XmlSpotlightFactory()),
		entry("narrow-spotlight", new XmlNarrowSpotlightFactory()),
		entry("point-light", new XmlPointLightFactory()),
		entry("directional-light", new XmlDirectionalLightFactory())
	);
	//@formatter:on
	@Override
	public List<LightSource> createHelper(Element element) {
		List<Element> lightElements = Util.getChildren(element);
		List<LightSource> lights = new ArrayList<>(lightElements.size());
		for (Element light : lightElements) {
			try {
				lights.add(FACTORIES.get(light.getNodeName()).create(light));
			} catch (NullPointerException e) {
				throw new XmlParserException("Unknown light source " + light.getNodeName() + "\" in <lights>.", e);
			}
		}
		return lights;
	}

}
