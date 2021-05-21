package xml.factories.element;

import org.w3c.dom.Element;
import lighting.DirectionalLight;
import primitives.NormalizedVector;
import xml.factories.attribute.XmlTripleFactory;

/**
 * This class can create a {@link DirectionalLight} from an XML {@link Element}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlDirectionalLightFactory extends XmlLightFactory {
	private static final XmlTripleFactory<NormalizedVector> DIRECTION_FACTORY =
		new XmlTripleFactory<>(NormalizedVector::new);

	@Override
	public DirectionalLight create(Element element) {
		NormalizedVector direction = DIRECTION_FACTORY.create(element.getAttribute("direction"));
		return new DirectionalLight(colour(element), direction);
	}

}
