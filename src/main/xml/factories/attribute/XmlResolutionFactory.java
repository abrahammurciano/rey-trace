package xml.factories.attribute;

import rendering.Resolution;

public class XmlResolutionFactory extends XmlFactoryFromAttribute<Resolution> {

	@Override
	protected Resolution create(String attribute) {
		return new Resolution(attribute);
	}

}
