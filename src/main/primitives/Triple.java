package primitives;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import math.compare.DoubleCompare;
import util.DoubleTriFunction;

/**
 * A class that has three values, (x, y, z)
 */
public abstract class Triple {
	/** The x coordinate. */
	public final double x;
	/** The y coordinate. */
	public final double y;
	/** The z coordinate. */
	public final double z;

	/**
	 * Construct a triple with the given coordinates.
	 *
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param z The z coordinate.
	 */
	protected Triple(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Creates a new {@link Triple} of the subtype returned by {@code creator} which is a transformation of this
	 * {@link Triple} by applying the given transformation to each of the coordinates.
	 *
	 * @param <T>            The type of Triple to return.
	 * @param transformation A function which receives two doubles and returns another double.
	 * @param aux            An auxiliary {@link Triple} whose corresponding coordinate may (or may not) be used in
	 *                       the transformation function in order to calculate each of the new coordinates.
	 * @param creator        A function which receives three doubles and returns a new {@link Triple}
	 * @return The {@link Triple} made up of applying the transformation to each of the three coordinates.
	 */
	public <T extends Triple> T transform(DoubleBinaryOperator transformation, Triple aux,
		DoubleTriFunction<T> creator) {
		return creator.apply(transformation.applyAsDouble(x, aux.x), transformation.applyAsDouble(y, aux.y),
			transformation.applyAsDouble(z, aux.z));
	}

	/**
	 * Similar to {@link #transform(DoubleBinaryOperator, Triple, DoubleTriFunction)} but does not require an auxiliary
	 * {@link Triple}, since the transformation when called in this way does not depend on a second coordinate.
	 *
	 * @param <T>            The type of Triple to return.
	 * @param transformation A function which receives a dingle double and returns another double.
	 * @param creator        A function which receives three doubles and returns a new {@link Triple}
	 * @return The {@link Triple} made up of applying the transformation to each of the three coordinates.
	 */
	public <T extends Triple> T transform(DoubleUnaryOperator transformation, DoubleTriFunction<T> creator) {
		return creator.apply(transformation.applyAsDouble(x), transformation.applyAsDouble(y),
			transformation.applyAsDouble(z));
	}

	/**
	 * Calculates the sum of the three values this {@link Triple} is made up of.
	 *
	 * @return The sum of the three values this {@link Triple} is made up of.
	 */
	double sum() {
		return x + y + z;
	}

	/**
	 * Checks if this {@link Triple}'s values are equal to the given values.
	 *
	 * @param x The x value to compare.
	 * @param y The y value to compare.
	 * @param z The z value to compare.
	 * @return True if this {@link Triple}'s values are equal to the given values.
	 */
	public boolean equals(double x, double y, double z) {
		return DoubleCompare.eq(this.x, x) && DoubleCompare.eq(this.y, y) && DoubleCompare.eq(this.z, z);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Triple triple = (Triple) o;
		return equals(triple.x, triple.y, triple.z);
	}

	@Override
	public int hashCode() {
		return 0;
	}

	/**
	 * Returns the {@link Triple} as a string in the cartesian representation, e.g. "(0, 0, 0)"
	 */
	@Override
	public String toString() {
		return "" + x + ", " + y + ", " + z;
	}

}
