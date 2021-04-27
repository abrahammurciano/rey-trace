package util;

/**
 * Represents a linear polynomial and provides a way to solve it.
 */
public class Linear extends Polynomial {
	/**
	 * The coefficient of the linear term.
	 */
	public final double a;

	/**
	 * The constant.
	 */
	public final double b;

	/**
	 * Constructor of linear equations.
	 *
	 * @param a The coefficient of the linear term. This may not be 0, else the equation has solutions
	 * @param b The constant
	 */
	public Linear(double a, double b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public double[] solutions() {
		return new double[] {b / a};
	}
}
