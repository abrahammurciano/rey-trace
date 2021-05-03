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

	public static VectorBase create(double x, double y, double z) {
		return new VectorBase(x, y, z);
	}

	/**
	 * Adds a {@link Triple} to this {@link VectorBase} and returns a new {@link Triple} of the same type returned by
	 * {@code creator}.
	 *
	 * @param triple  The {@link VectorBase} which is to be added to this {@link VectorBase}.
	 * @param creator A function which receives three doubles and returns a new {@link Triple}.
	 * @return The sum of the two {@link VectorBase}s.
	 */
	public Triple add(Triple triple, TripleCreator creator) {
		return transform(Double::sum, triple, creator);
	}

	/**
	 * Adds two {@link VectorBase}s and returns a new {@link VectorBase}.
	 *
	 * @param triple The {@link Triple} which is to be added to this {@link VectorBase}.
	 * @return The sum of the two {@link VectorBase}s.
	 */
	public VectorBase add(Triple triple) {
		return (VectorBase) add(triple, VectorBase::create);
	}

	/**
	 * Subtracts two {@link VectorBase}s and returns a new {@link VectorBase} of the type returned by {@code creator}.
	 *
	 * @param vector  The {@link VectorBase} to be subtracted from this {@link VectorBase}.
	 * @param creator A function which receives three doubles and returns a new {@link VectorBase}.
	 * @return The sum of this {@link VectorBase} and the negation of the given {@link VectorBase}.
	 */
	public VectorBase subtract(VectorBase vector, VectorBaseCreator creator) {
		return (VectorBase) add(vector.reversed(), creator);
	}

	/**
	 * Subtracts two {@link VectorBase}s and returns a new {@link VectorBase}.
	 *
	 * @param vector The {@link VectorBase} to be subtracted from this {@link VectorBase}.
	 * @return The sum of this {@link VectorBase} and the negation of the given {@link VectorBase}.
	 */
	public VectorBase subtract(VectorBase vector) {
		return subtract(vector, VectorBase::create);
	}

	/**
	 * Constructs a new {@link VectorBase} which is a scalar multiplication of this {@link VectorBase} by a scalar.
	 *
	 * @param factor  The scalar by which to multiply this {@link VectorBase}
	 * @param creator A function which receives three doubles and returns a new {@link VectorBase}.
	 * @return New scaled {@link VectorBase}, of the concrete type as returned by {@code creator}.
	 */
	public VectorBase scale(double factor, VectorBaseCreator creator) {
		return (VectorBase) transform(c -> c * factor, creator);
	}

	/**
	 * Constructs a new {@link VectorBase} which is a scalar multiplication of this {@link VectorBase} by a scalar.
	 *
	 * @param factor The scalar by which to multiply this {@link VectorBase}
	 * @return New scaled {@link VectorBase}
	 * @throws ZeroVectorException if the scale factor is zero.
	 */
	public VectorBase scale(double factor) {
		return scale(factor, VectorBase::create);
	}

	/**
	 * An alias for {@link #scale(double)} with factor -1.
	 *
	 * @return New reversed {@link VectorBase}
	 */
	public VectorBase reversed() {
		return scale(-1);
	}

	/**
	 * Calculates the cross product of two {@link VectorBase}s.
	 *
	 * @param v       The {@link VectorBase} by which to multiply this {@link VectorBase}
	 * @param creator A function which receives three doubles and returns a new {@link VectorBase}
	 * @return The resulting {@link VectorBase} which is the cross product of the two {@link VectorBase}s, but of the
	 *         concrete type as returned by {@code creator}
	 */
	public VectorBase cross(VectorBase v, VectorBaseCreator creator) {
		return creator.create(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
	}

	/**
	 * Calculates the cross product of two {@link VectorBase}s.
	 *
	 * @param v The {@link VectorBase} by which to multiply this {@link VectorBase}
	 * @return The resulting {@link VectorBase} which is the cross product of the two {@link VectorBase}s
	 * @throws ZeroVectorException if the result vector is the zero vector.
	 */
	public VectorBase cross(VectorBase v) {
		return cross(v, VectorBase::create);
	}

	/**
	 * Calculates the dot product of two {@link VectorBase}s
	 *
	 * @param v The {@link VectorBase} to dot product with this {@link VectorBase}
	 * @return The dot product of the two {@link VectorBase}s
	 */
	public double dot(Triple v) {
		return transform((base, aux) -> base * aux, v, VectorBase::create).sum();
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

	@Override
	public String toString() {
		return "[" + super.toString() + "]";
	}
}
