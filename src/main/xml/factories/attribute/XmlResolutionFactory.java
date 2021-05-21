package xml.factories.attribute;

import rendering.Resolution;

public class XmlResolutionFactory implements XmlFactoryFromAttribute<Resolution> {

	@Override
	public Resolution create(String attribute) {
		return new Resolution(attribute);
	}

}
