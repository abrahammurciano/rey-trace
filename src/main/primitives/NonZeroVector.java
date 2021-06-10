package primitives;

/**
 * The {@link NonZeroVector} class represents a {@link NonZeroVector} with it's base at the
 * origin and it's head at the {@link Point} 'head'.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class NonZeroVector extends Vector {

	/**
	 * This constructor accepts 3 doubles and returns the appropriate {@link NonZeroVector}
	 *
	 * @param x The value of the x coordinate.
	 * @param y The value of the y coordinate.
	 * @param z The value of the z coordinate.
	 * @throws ZeroVectorException if the {@link NonZeroVector} is the zero vector.
	 */
	public NonZeroVector(double x, double y, double z) {
		super(x, y, z);
		if (this.equals(0, 0, 0)) {
			throw new ZeroVectorException();
		}
	}

	/**
	 * Adds two {@link NonZeroVector}s and returns a new {@link NonZeroVector}.
	 *
	 * @param triple The {@link Triple} which is to be added to this {@link NonZeroVector}.
	 * @return The sum of the two {@link NonZeroVector}s.
	 * @throws ZeroVectorException when adding a {@link NonZeroVector} with its reverse.
	 */
	@Override
	public NonZeroVector add(Triple triple) {
		return add(triple, NonZeroVector::new);
	}

	/**
	 * Subtracts two {@link NonZeroVector}s and returns a new {@link NonZeroVector}.
	 *
	 * @param vector The {@link Vector} to be subtracted from this {@link NonZeroVector}.
	 * @return The sum of this {@link NonZeroVector} and the negation of the given {@link Vector}.
	 * @throws ZeroVectorException if a {@link NonZeroVector} is subtracted from itself.
	 */
	@Override
	public NonZeroVector subtract(Vector vector) {
		return subtract(vector, NonZeroVector::new);
	}

	/**
	 * Constructs a new {@link NonZeroVector} which is a scalar multiplication of this {@link NonZeroVector} by a
	 * scalar.
	 *
	 * @param factor The scalar by which to multiply this {@link NonZeroVector}
	 * @return New scaled {@link NonZeroVector}
	 * @throws ZeroVectorException if the scale factor is zero.
	 */
	@Override
	public NonZeroVector scale(double factor) {
		return scale(factor, NonZeroVector::new);
	}

	/**
	 * Calculates the cross product of two {@link NonZeroVector}s.
	 *
	 * @param vector The {@link NonZeroVector} by which to multiply this {@link NonZeroVector}
	 * @return The resulting {@link NonZeroVector} which is the cross product of the two {@link NonZeroVector}s
	 * @throws ZeroVectorException if the result vector is the zero vector.
	 */
	public NonZeroVector cross(NonZeroVector vector) {
		return cross(vector, NonZeroVector::new);
	}

	/**
	 * Creates a new {@link NonZeroVector} with the same direction as this one but with a
	 * magnitude of one.
	 *
	 * @return new {@link NonZeroVector}
	 */
	public NormalizedVector normalized() {
		return new NormalizedVector(this);
	}

	/**
	 * Calculates the angle in radians between this vector and the given vector. The
	 * angle is normalized between zero and Pi.
	 *
	 * @param v The other vector to be used to calculate the angle.
	 * @return The angle in radians between the vectors between zero and Pi.
	 */
	public double angle(NonZeroVector v) {
		double dot = normalized().dot(v.normalized());
		return Math.acos(dot);
	}

	@Override
	public NonZeroVector reversed() {
		return scale(-1);
	}
}
