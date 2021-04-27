package util;

/**
 * This class represents a quadratic equation and provides functions to help solve them.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Quadratic extends Polynomial {

	public final double a;
	public final double b;
	public final double c;
	private final double determinant;

	/**
	 * Constructor of quadratic equations.
	 *
	 * @param a The coefficient of the quadratic term. This may not be 0, else it would not be quadratic.
	 * @param b The coefficient of the linear term
	 * @param c The constant
	 */
	public Quadratic(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.determinant = determinant();
	}

	/**
	 * Calculates the determinant of the equation. That is, b^2-4ac.
	 *
	 * @return The determinant.
	 */
	public double determinant() {
		return determinant;
	}

	@Override
	public double[] solutions() {
		if (DoubleCompare.lt(determinant, 0)) {
			return EMPTY_ARRAY;
		}
		double minusB = -b;
		double twoA = 2 * a;
		if (DoubleCompare.eq(determinant, 0)) {
			return new double[] {minusB / twoA};
		}
		double sqrt = Math.sqrt(determinant);
		return new double[] {(minusB + sqrt) / twoA, (minusB - sqrt) / twoA};
	}
}
