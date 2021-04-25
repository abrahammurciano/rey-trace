package primitives;

/**
 * The {@link Vector} class represents a {@link Vector} with it's base at the origin and it's head at the {@link Point}
 * 'head'.
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
		if (this.equals(0, 0, 0)) {
			throw new ZeroVectorException();
		}
	}

	/**
	 * This constructor accepts a {@link Triple} and constructs the appropriate {@link Vector} with those values.
	 *
	 * @param triple The {@link Triple} with the coordinates which this {@link Vector} would point to if its base was at the
	 *        origin.
	 * @throws ZeroVectorException if this {@link Vector} is the zero vector.
	 */
	public Vector(Triple triple) {
		this(triple.x, triple.y, triple.z);
	}

	/**
	 * Adds two {@link Vector}s and returns a new {@link Vector}.
	 *
	 * @param v The {@link Vector} which is to be added to this {@link Vector}.
	 * @return The sum of the two {@link Vector}s.
	 * @throws ZeroVectorException when adding a {@link Vector} with its reverse.
	 */
	public Vector add(Vector v) {
		return new Vector(transform(Double::sum, v));
	}

	/**
	 * Subtracts two {@link Vector}s and returns a new {@link Vector}.
	 *
	 * @param vector The {@link Vector} to be subtracted from this {@link Vector}.
	 * @return The sum of this {@link Vector} and the negation of the given {@link Vector}.
	 * @throws ZeroVectorException if a {@link Vector} is subtracted from itself.
	 */
	public Vector subtract(Vector vector) {
		return add(vector.reversed());
	}

	/**
	 * Constructs a new {@link Vector} which is a scalar multiplication of this {@link Vector} by a scalar.
	 *
	 * @param factor The scalar by which to multiply this {@link Vector}
	 * @return New scaled {@link Vector}
	 * @throws ZeroVectorException if the scale factor is zero.
	 */
	public Vector scale(double factor) {
		return new Vector(transform(c -> c * factor));
	}

	/**
	 * An alias for {@link #scale} with factor -1.
	 *
	 * @return New reversed {@link Vector}
	 */
	public Vector reversed() {
		return scale(-1);
	}

	/**
	 * Calculates the cross product of two {@link Vector}s.
	 *
	 * @param v The {@link Vector} by which to multiply this {@link Vector}
	 * @return The resulting {@link Vector} which is the cross product of the two {@link Vector}s
	 * @throws ZeroVectorException if the result vector is the zero vector.
	 */
	public Vector cross(Vector v) {
		return new Vector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
	}

	/**
	 * Calculates the dot product of two {@link Vector}s
	 *
	 * @param v The {@link Vector} to dot product with this {@link Vector}
	 * @return The dot product of the two {@link Vector}s
	 */
	public double dot(Vector v) {
		return transform((base, aux) -> base * aux, v).sum();
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
		return transform(coord -> coord * coord).sum();
	}

	/**
	 * Creates a new {@link Vector} with the same direction as this one but with a magnitude of one.
	 *
	 * @return new {@link Vector}
	 */
	public NormalizedVector normalized() {
		return new NormalizedVector(this);
	}

	/**
	 * Calculates the angle in radians between this vector and the given vector. The angle is normalized between zero and
	 * Pi.
	 *
	 * @param v The other vector to be used to calculate the angle.
	 * @return The angle in radians between the vectors between zero and Pi.
	 */
	public double angle(Vector v) {
		double dot = normalized().dot(v.normalized());
		return Math.acos(dot);
	}
}
