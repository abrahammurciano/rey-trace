package primitives;

/**
 * This exception is thrown when an attempt is made to create a zero vector.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class ZeroVectorException extends RuntimeException {

	private static final long serialVersionUID = 7375307161172505856L;

	/**
	 * Construct a default zero vector exception with a default message.
	 */
	public ZeroVectorException() {
		super("Error: You cannot create a zero vector.");
	}

	/**
	 * Construct a zero vector exception with a custom message.
	 *
	 * @param message The message which describes the cause of the exception.
	 */
	public ZeroVectorException(String message) {
		super(message);
	}
}
