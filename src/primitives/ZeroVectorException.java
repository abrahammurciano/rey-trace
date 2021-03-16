package primitives;

/**
 * This exception is thrown when an attempt is made to create a zero vector.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class ZeroVectorException extends RuntimeException {

	private static final long serialVersionUID = 7375307161172505856L;

	public ZeroVectorException() {
		super("Error: You cannot create a zero vector.");
	}

	public ZeroVectorException(String message) {
		super(message);
	}

	public ZeroVectorException(Throwable cause) {
		super(cause);
	}

	public ZeroVectorException(String message, Throwable cause) {
		super(message, cause);
	}
}
