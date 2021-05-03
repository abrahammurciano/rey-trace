package primitives;

/**
 * The {@link VectorBase} class represents a {@link Vector} with the single difference that the VectorBase can be the
 * zero vector. Let me repeat that. This class can have values of (0,0,0).
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class VectorBase extends Triple {

	/**
	 * This constructor accepts 3 doubles and returns the appropriate {@link VectorBase}
	 *
	 * @param x The value of the x coordinate.
	 * @param y The value of the y coordinate.
	 * @param z The value of the z coordinate.
	 * @throws ZeroVectorException if the {@link VectorBase} is the zero vector.
	 */
	public VectorBase(double x, double y, double z) {
		super(x, y, z);
	}

	/**
	 * This constructor accepts a {@link Triple} and constructs the appropriate {@link VectorBase} with those values.
	 *
	 * @param triple The {@link Triple} with the coordinates which this {@link VectorBase} would point to if its base was at the
	 *        origin.
	 * @throws ZeroVectorException if this {@link VectorBase} is the zero vector.
	 */
	public VectorBase(Triple triple) {
		this(triple.x, triple.y, triple.z);
	}

	/**
	 * Adds two {@link VectorBase}s and returns a new {@link VectorBase}.
	 *
	 * @param v The {@link VectorBase} which is to be added to this {@link VectorBase}.
	 * @return The sum of the two {@link VectorBase}s.
	 * @throws ZeroVectorException when adding a {@link VectorBase} with its reverse.
	 */
	public VectorBase add(VectorBase v) {
		return transform(Double::sum, v, VectorBase.class);
	}

	/**
	 * Subtracts two {@link VectorBase}s and returns a new {@link VectorBase}.
	 *
	 * @param vector The {@link VectorBase} to be subtracted from this {@link VectorBase}.
	 * @return The sum of this {@link VectorBase} and the negation of the given {@link VectorBase}.
	 * @throws ZeroVectorException if a {@link VectorBase} is subtracted from itself.
	 */
	public VectorBase subtract(VectorBase vector) {
		return add(vector.reversed());
	}

	/**
	 * Constructs a new {@link VectorBase} which is a scalar multiplication of this {@link VectorBase} by a scalar.
	 *
	 * @param factor The scalar by which to multiply this {@link VectorBase}
	 * @return New scaled {@link VectorBase}
	 * @throws ZeroVectorException if the scale factor is zero.
	 */
	public VectorBase scale(double factor) {
		return transform(c -> c * factor, VectorBase.class);
	}

	/**
	 * An alias for {@link #scale} with factor -1.
	 *
	 * @return New reversed {@link VectorBase}
	 */
	public VectorBase reversed() {
		return scale(-1);
	}

	/**
	 * Calculates the cross product of two {@link VectorBase}s.
	 *
	 * @param v The {@link VectorBase} by which to multiply this {@link VectorBase}
	 * @return The resulting {@link VectorBase} which is the cross product of the two {@link VectorBase}s
	 * @throws ZeroVectorException if the result vector is the zero vector.
	 */
	public VectorBase cross(VectorBase v) {
		return new VectorBase(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
	}

	/**
	 * Calculates the dot product of two {@link VectorBase}s
	 *
	 * @param v The {@link VectorBase} to dot product with this {@link VectorBase}
	 * @return The dot product of the two {@link VectorBase}s
	 */
	public double dot(Triple v) {
		return transform((base, aux) -> base * aux, v, Point.class).sum();
	}

	/**
	 * Calculates the length of this {@link VectorBase}.
	 *
	 * @return The length of this {@link VectorBase}.
	 */
	public double length() {
		return Math.sqrt(squareLength());
	}

	/**
	 * Calculates the square of the length of this {@link VectorBase}.
	 *
	 * @return The square of the length of this {@link VectorBase}.
	 */
	public double squareLength() {
		return this.dot(this);
	}

}



