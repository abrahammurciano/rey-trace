package math.equations;

import math.compare.DoubleCompare;

/**
 * This class represents a quadratic equation and provides functions to help
 * solve them.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Quadratic extends Polynomial {

	/** The coefficient of the quadratic term. */
	public final double a;
	/** The coefficient of the linear term. */
	public final double b;
	/** The constant. */
	public final double c;
	/** The discriminant of the quadratic equation. */
	public final double discriminant;

	/**
	 * Constructor of quadratic equations.
	 *
	 * @param a The coefficient of the quadratic term. This may not be 0, else it
	 *          would not be quadratic.
	 * @param b The coefficient of the linear term
	 * @param c The constant
	 */
	public Quadratic(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.discriminant = discriminant();
	}

	/**
	 * Calculates the discriminant of the equation. That is, b^2-4ac.
	 *
	 * @return The discriminant.
	 */
	private double discriminant() {
		return b * b - 4 * a * c;
	}

	@Override
	public double[] solutions() {
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

	/**
	 * Get the value of the quadratic for a given x value.
	 *
	 * @param x The x value.
	 * @return the value of the polynomial.
	 */
	public double substitute(double x) {
		return a * x * x + b * x + c;
	}
}
