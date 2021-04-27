package primitives;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import util.DoubleCompare;

/**
 * A class that has three values, (x, y, z)
 */
abstract class Triple {

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
	 * @param returnType A derived class of {@link Triple} which is to be instanciated and returned by the
	 *        transformation.
	 * @return The {@link Triple} made up of applying the transformation to each of the three coordinates.
	 * @throws ZeroVectorException if the transformation results in the zero Vector.
	 */
	protected <T extends Triple> T transform(DoubleBinaryOperator transformation, Triple aux,
		Class<T> returnType) {
		try {
			return returnType.getConstructor(double.class, double.class, double.class).newInstance(
				transformation.applyAsDouble(x, aux.x), transformation.applyAsDouble(y, aux.y),
				transformation.applyAsDouble(z, aux.z));
		}
		// MUST. APPEASE. COMPILER!
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException
			| NoSuchMethodException | SecurityException __) {
			throw new IllegalStateException();
		} catch (InvocationTargetException e) {
			Throwable throwable = e.getCause();
			if (throwable instanceof ZeroVectorException) {
				throw (ZeroVectorException) throwable;
			} else {
				throw new IllegalStateException();
			}
		}
	}

	/**
	 * Similar to {@link #transform(DoubleBinaryOperator, Triple)} but does not require an auxiliary {@link Triple},
	 * since the transformation when called in this way does not depend on a second coordinate.
	 *
	 * @param transformation A function which receives a dingle double and returns another double.
	 * @param returnType A derived class of {@link Triple} which is to be instanciated and returned by the
	 *        transformation.
	 * @return The {@link Triple} made up of applying the transformation to each of the three coordinates.
	 * @throws ZeroTripleException if the transformation results in the zero Triple.
	 */
	protected <T extends Triple> T transform(DoubleUnaryOperator transformation,
		Class<T> returnType) {
		try {
			return returnType.getConstructor(double.class, double.class, double.class).newInstance(
				transformation.applyAsDouble(x), transformation.applyAsDouble(y),
				transformation.applyAsDouble(z));
		}
		// MUST. APPEASE. COMPILER!
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException
			| NoSuchMethodException | SecurityException __) {
			throw new IllegalStateException();
		} catch (InvocationTargetException e) {
			Throwable throwable = e.getCause();
			if (throwable instanceof ZeroVectorException) {
				throw (ZeroVectorException) throwable;
			} else {
				throw new IllegalStateException();
			}
		}
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
