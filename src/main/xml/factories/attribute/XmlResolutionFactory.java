package xml.factories.attribute;

import rendering.Resolution;

/**
 * Constructs a new {@link Resolution} from an XML attribute string in a form similar to "1920x1080".
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlResolutionFactory extends XmlFactoryFromAttribute<Resolution> {

	@Override
	protected Resolution create(String attribute) {
		return new Resolution(attribute);
	}

}
