package primitives;

import util.DoubleTriFunction;

/**
 * The {@link Vector} class represents a {@link NonZeroVector} with the single difference that the VectorBase can be the
 * zero vector. Let me repeat that. This class can have values of (0,0,0).
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Vector extends Triple {

	/**
	 * This constructor accepts 3 doubles and returns the appropriate {@link Vector}
	 *
	 * @param x The value of the x coordinate.
	 * @param y The value of the y coordinate.
	 * @param z The value of the z coordinate.
	 * @throws ZeroVectorException if the {@link Vector} is the zero vector.
	 */
	public Vector(double x, double y, double z) {
		super(x, y, z);
	}

	/**
	 * Adds a {@link Triple} to this {@link Vector} and returns a new {@link Triple} of the same type returned by
	 * {@code creator}.
	 *
	 * @param <T>     The type of Triple to return.
	 * @param triple  The {@link Vector} which is to be added to this {@link Vector}.
	 * @param creator A function which receives three doubles and returns a new {@link Triple}.
	 * @return The sum of the two {@link Vector}s.
	 */
	public <T extends Triple> T add(Triple triple, DoubleTriFunction<T> creator) {
		return transform(Double::sum, triple, creator);
	}

	/**
	 * Adds two {@link Vector}s and returns a new {@link Vector}.
	 *
	 * @param triple The {@link Triple} which is to be added to this {@link Vector}.
	 * @return The sum of the two {@link Vector}s.
	 */
	public Vector add(Triple triple) {
		return add(triple, Vector::new);
	}

	/**
	 * Subtracts two {@link Vector}s and returns a new {@link Vector} of the type returned by {@code creator}.
	 *
	 * @param <T>     The type of Triple to return.
	 * @param vector  The {@link Vector} to be subtracted from this {@link Vector}.
	 * @param creator A function which receives three doubles and returns a new {@link Vector}.
	 * @return The sum of this {@link Vector} and the negation of the given {@link Vector}.
	 */
	public <T extends Vector> T subtract(Vector vector, DoubleTriFunction<T> creator) {
		return add(vector.reversed(), creator);
	}

	/**
	 * Subtracts two {@link Vector}s and returns a new {@link Vector}.
	 *
	 * @param vector The {@link Vector} to be subtracted from this {@link Vector}.
	 * @return The sum of this {@link Vector} and the negation of the given {@link Vector}.
	 */
	public Vector subtract(Vector vector) {
		return subtract(vector, Vector::new);
	}

	/**
	 * Constructs a new {@link Vector} which is a scalar multiplication of this {@link Vector} by a scalar.
	 *
	 * @param <T>     The type of Triple to return.
	 * @param factor  The scalar by which to multiply this {@link Vector}
	 * @param creator A function which receives three doubles and returns a new {@link Vector}.
	 * @return New scaled {@link Vector}, of the concrete type as returned by {@code creator}.
	 */
	public <T extends Vector> T scale(double factor, DoubleTriFunction<T> creator) {
		return transform(c -> c * factor, creator);
	}

	/**
	 * Constructs a new {@link Vector} which is a scalar multiplication of this {@link Vector} by a scalar.
	 *
	 * @param factor The scalar by which to multiply this {@link Vector}
	 * @return New scaled {@link Vector}
	 * @throws ZeroVectorException if the scale factor is zero.
	 */
	public Vector scale(double factor) {
		return scale(factor, Vector::new);
	}

	/**
	 * An alias for {@link #scale(double)} with factor -1.
	 *
	 * @return New reversed {@link Vector}
	 */
	public Vector reversed() {
		return scale(-1);
	}

	/**
	 * Calculates the cross product of two {@link Vector}s.
	 *
	 * @param <T>     The type of Triple to return.
	 * @param v       The {@link Vector} by which to multiply this {@link Vector}
	 * @param creator A function which receives three doubles and returns a new {@link Vector}
	 * @return The resulting {@link Vector} which is the cross product of the two {@link Vector}s, but of the
	 *         concrete type as returned by {@code creator}
	 */
	public <T extends Vector> T cross(Vector v, DoubleTriFunction<T> creator) {
		return creator.apply(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
	}

	/**
	 * Calculates the cross product of two {@link Vector}s.
	 *
	 * @param v The {@link Vector} by which to multiply this {@link Vector}
	 * @return The resulting {@link Vector} which is the cross product of the two {@link Vector}s
	 * @throws ZeroVectorException if the result vector is the zero vector.
	 */
	public Vector cross(Vector v) {
		return cross(v, Vector::new);
	}

	/**
	 * Calculates the dot product of two {@link Vector}s
	 *
	 * @param v The {@link Vector} to dot product with this {@link Vector}
	 * @return The dot product of the two {@link Vector}s
	 */
	public double dot(Triple v) {
		return transform((base, aux) -> base * aux, v, Vector::new).sum();
	}

	/**
	 * Calculates the length of this {@link Vector}.
	 *
	 * @return The length of this {@link Vector}.
	 */
	public double length() {
		return Math.sqrt(squareLength());
	}

	/**
	 * Calculates the square of the length of this {@link Vector}.
	 *
	 * @return The square of the length of this {@link Vector}.
	 */
	public double squareLength() {
		return this.dot(this);
	}

	@Override
	public String toString() {
		return "[" + super.toString() + "]";
	}
}
