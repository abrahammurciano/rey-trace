package xml;

import java.util.LinkedList;
import java.util.List;
import org.w3c.dom.Element;

/**
 * This exception is thrown when something goes wrong during the parsing of the XML file.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlParserException extends RuntimeException {
	/** The list of elements leading to the element that caused this exception. */
	private final List<Element> trace = new LinkedList<>();

	/**
	 * Construct this exception with an inner exception.
	 *
	 * @param cause The inner exception.
	 */
	public XmlParserException(Throwable cause) {
		super(cause);
	}

	/**
	 * Construct this exception with a message.
	 *
	 * @param message The error message.
	 */
	public XmlParserException(String message) {
		super(message);
	}

	/**
	 * Construct this exception with a message and an inner exception.
	 *
	 * @param message The error message.
	 * @param cause   The inner exception.
	 */
	public XmlParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Add an XML element to the trace of where the error occurred. This element should be an ancestor of the last
	 * element to be added to the trace.
	 *
	 * @param element The XML element to add to the trace.
	 */
	public void addToTrace(Element element) {
		trace.add(element);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getMessage());
		sb.append("\nThis error was directly caused by the following error.\n");
		sb.append(getCause().getMessage());
		sb.append("\nThis error occurred");
		for (Element element : trace) {
			sb.append(" in ");
			sb.append(element.getTagName());
			sb.append('\n');
		}
		return sb.toString();
	}
}
