package xml;

import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import scene.Scene;
import xml.factories.element.XmlSceneFactory;

/**
 * A class which parses an XML file and constructs the {@link Scene}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlSceneParser {
	private static final XmlSceneFactory SCENE_FACTORY = new XmlSceneFactory();
	private static final DocumentBuilderFactory DOC_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
	private DocumentBuilder docBuilder;

	/**
	 * Default constructor for the XML scene parser.
	 */
	public XmlSceneParser() {
		// Security stuff
		DOC_BUILDER_FACTORY.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		DOC_BUILDER_FACTORY.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

		try {
			docBuilder = DOC_BUILDER_FACTORY.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new XmlParserException(e);
		}
	}

	/**
	 * Create a {@link Scene} from the given XML file.
	 *
	 * @param xmlFile The filename of the XML file containing the {@link Scene} information.
	 * @return A new {@link Scene} reflecting the information in the given XML file.
	 * @throws IOException        If the file could not be opened.
	 * @throws XmlParserException if the XML file is unparsable.
	 */
	public Scene parse(String xmlFile) throws IOException {

		Element root;
		try {
			root = docBuilder.parse(new File(xmlFile)).getDocumentElement();
		} catch (SAXException e) {
			throw new XmlParserException(e);
		}

		return SCENE_FACTORY.create(root);
	}
}
