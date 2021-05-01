package math.equations;

public abstract class Polynomial {

	protected final double[] EMPTY_ARRAY = new double[0];

	/**
	 * Calculates the solutions of the equation.
	 *
	 * @return An array of doubles containing the solutions, if any.
	 */
	public abstract double[] solutions();
}
