package math.equations;

/**
 * Abstract class for different types of polynomials to extend.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public abstract class Polynomial {
	/** An empty array of doubles to return when there are no solutions. */
	protected final double[] EMPTY_ARRAY = new double[0];

	/**
	 * Calculates the solutions of the equation.
	 *
	 * @return An array of doubles containing the solutions, if any.
	 */
	public abstract double[] solutions();
}
