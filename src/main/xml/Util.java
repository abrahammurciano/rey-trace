package xml;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A collection of functions to help parse the XML document.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Util {
	private Util() {}

	/**
	 * Returns whether or not the given XML element has a child of the given name.
	 *
	 * @param parent The element whose children to find.
	 * @param name   The name of the element to find.
	 * @return whether or not the given XML element has a child of the given name.
	 */
	public static boolean hasChild(Element parent, String name) {
		return !getChildren(parent, name).isEmpty();
	}

	/**
	 * Get a the first direct children with a given name of the given XML element.
	 *
	 * @param parent The element whose children to find.
	 * @param name   The name of the element to find.
	 * @return The list of direct children of the given XML element.
	 * @throws XmlParserException if the parent does not contain a direct child with the given name.
	 */
	public static Element getChild(Element parent, String name) {
		try {
			return getChildren(parent, name).get(0);
		} catch (IndexOutOfBoundsException e) {
			throw new XmlParserException("Child element \"" + name + "\" is missing", e);
		}
	}

	/**
	 * Get a list of direct children with a given name of the given XML element.
	 *
	 * @param parent The element whose children to find.
	 * @param name   The name of the elements to find.
	 * @return The list of direct children with the given name.
	 */
	public static List<Element> getChildren(Element parent, String name) {
		return getChildren(parent, node -> name.equals(node.getNodeName()));
	}

	/**
	 * Get a list of direct children of the given XML element.
	 *
	 * @param parent The element whose children to find.
	 * @return The list of direct children of the given XML element.
	 */
	public static List<Element> getChildren(Element parent) {
		return getChildren(parent, node -> true);
	}

	private static List<Element> getChildren(Element parent, Predicate<Node> predicate) {
		List<Element> nodeList = new ArrayList<>();
		NodeList children = parent.getChildNodes();
		for (int i = 0; i < children.getLength(); ++i) {
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE && predicate.test(child)) {
				nodeList.add((Element) child);
			}
		}
		return nodeList;
	}
}
