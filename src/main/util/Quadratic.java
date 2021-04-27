package util;

/**
 * This class represents a quadratic equation and provides functions to help solve them.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Quadratic {

	public final double a;
	public final double b;
	public final double c;

	private final double[] EMPTY_ARRAY = new double[0];

	/**
	 * Constructor of quadratic equations.
	 *
	 * @param a The coefficient of the quadratic term
	 * @param b The coefficient of the linear term
	 * @param c The constant
	 */
	public Quadratic(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	/**
	 * Calculates the discriminant of the equation. That is, b^2-4ac.
	 *
	 * @return The discriminant.
	 */
	public double discriminant() {
		return b * b - 4 * a * c;
	}

	/**
	 * Calculates the solutions of the equation.
	 *
	 * @return An array of doubles containing the solutions, if any.
	 */
	public double[] solutions() {
		return solutions(discriminant());
	}

	/**
	 * Calculates the solutions of the equation.
	 *
	 * @param discriminant The precomputed discriminant may be provided to improve efficiency. If incorrect, the
	 *        behaviour of this function is undefined.
	 * @return An array of doubles containing the solutions, if any.
	 */
	public double[] solutions(double discriminant) {
		if (DoubleCompare.lt(discriminant, 0)) {
			return EMPTY_ARRAY;
		}
		double minusB = -b;
		double twoA = 2 * a;
		if (DoubleCompare.eq(discriminant, 0)) {
			return new double[] {minusB / twoA};
		}
		double sqrt = Math.sqrt(discriminant);
		return new double[] {(minusB + sqrt) / twoA, (minusB - sqrt) / twoA};
	}
}
