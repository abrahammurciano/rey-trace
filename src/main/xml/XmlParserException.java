package xml;

/**
 * This exception is thrown when something goes wrong during the parsing of the XML file.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class XmlParserException extends RuntimeException {
	/**
	 * Construct this exception with an inner exception.
	 *
	 * @param cause The inner exception.
	 */
	public XmlParserException(Throwable cause) {
		super(cause);
	}

	/**
	 * Construct this exception with a message and an inner exception.
	 *
	 * @param string The error message.
	 * @param cause  The inner exception.
	 */
	public XmlParserException(String string, Throwable cause) {
		super(string, cause);
	}
}
