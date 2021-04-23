package primitives;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import util.DoubleCompare;

/**
 * A class that has three values, (x, y, z)
 */
public class Triple {

	public final double x;
	public final double y;
	public final double z;

	protected Triple(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Creates a new {@link Triple} which is a transformation of this {@link Triple} by applying the given
	 * transformation to each of the coordinates.
	 *
	 * @param transformation A function which receives two doubles and returns another double.
	 * @param aux An auxiliary {@link Triple} whose corresponding coordinate may (or may not) be used in the
	 *        transformation function in order to calculate each of the new coordinates.
	 * @return The {@link Triple} made up of applying the transformation to each of the three coordinates.
	 * @throws ZeroTripleException if the transformation results in the zero Triple.
	 */
	protected Triple transform(DoubleBinaryOperator transformation, Triple aux) {
		return new Triple(transformation.applyAsDouble(x, aux.x),
			transformation.applyAsDouble(y, aux.y), transformation.applyAsDouble(z, aux.z));
	}

	/**
	 * Similar to {@link #transform(DoubleBinaryOperator, Triple)} but does not require an auxiliary {@link Triple},
	 * since the transformation when called in this way does not depend on a second coordinate.
	 *
	 * @param transformation A function which receives a dingle double and returns another double.
	 * @return The {@link Triple} made up of applying the transformation to each of the three coordinates.
	 * @throws ZeroTripleException if the transformation results in the zero Triple.
	 */
	protected Triple transform(DoubleUnaryOperator transformation) {
		return new Triple(transformation.applyAsDouble(x), transformation.applyAsDouble(y),
			transformation.applyAsDouble(z));
	}

	/**
	 * Calculates the sum of the three values this {@link Triple} is made up of.
	 *
	 * @return The sum of the three values this {@link Triple} is made up of.
	 */
	protected double sum() {
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
		return DoubleCompare.eq(this.x, x) && DoubleCompare.eq(this.y, y)
			&& DoubleCompare.eq(this.z, z);
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
		return Objects.hash(x, y, z);
	}


	/**
	 * Returns the {@link Triple} as a string in the cartesian representation, e.g. "(0, 0, 0)"
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

}