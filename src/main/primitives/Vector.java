package primitives;

/**
 * The {@link Vector} class represents a {@link Vector} with it's base at the
 * origin and it's head at the {@link Point} 'head'.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Vector extends VectorBase {

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
	 * Create a new {@link Vector} from the given coordinates.
	 *
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @param z The z-coordinate
	 * @return The {@link Vector} with the given coordinates.
	 */
	public static Vector create(double x, double y, double z) {
		return new Vector(x, y, z);
	}

	/**
	 * Adds two {@link Vector}s and returns a new {@link Vector}.
	 *
	 * @param triple The {@link Triple} which is to be added to this {@link Vector}.
	 * @return The sum of the two {@link Vector}s.
	 * @throws ZeroVectorException when adding a {@link Vector} with its reverse.
	 */
	@Override
	public Vector add(Triple triple) {
		return (Vector) add(triple, Vector::create);
	}

	/**
	 * Subtracts two {@link Vector}s and returns a new {@link Vector}.
	 *
	 * @param vector The {@link VectorBase} to be subtracted from this {@link Vector}.
	 * @return The sum of this {@link Vector} and the negation of the given {@link VectorBase}.
	 * @throws ZeroVectorException if a {@link Vector} is subtracted from itself.
	 */
	@Override
	public Vector subtract(VectorBase vector) {
		return (Vector) subtract(vector, Vector::create);
	}

	/**
	 * Constructs a new {@link Vector} which is a scalar multiplication of this {@link Vector} by a scalar.
	 *
	 * @param factor The scalar by which to multiply this {@link Vector}
	 * @return New scaled {@link Vector}
	 * @throws ZeroVectorException if the scale factor is zero.
	 */
	@Override
	public Vector scale(double factor) {
		return (Vector) scale(factor, Vector::create);
	}

	/**
	 * Calculates the cross product of two {@link Vector}s.
	 *
	 * @param vector The {@link Vector} by which to multiply this {@link Vector}
	 * @return The resulting {@link Vector} which is the cross product of the two {@link Vector}s
	 * @throws ZeroVectorException if the result vector is the zero vector.
	 */
	public Vector cross(Vector vector) {
		return (Vector) cross(vector, Vector::create);
	}

	/**
	 * Creates a new {@link Vector} with the same direction as this one but with a
	 * magnitude of one.
	 *
	 * @return new {@link Vector}
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
	public double angle(Vector v) {
		double dot = normalized().dot(v.normalized());
		return Math.acos(dot);
	}

	@Override
	public Vector reversed() {
		return (Vector) super.reversed();
	}
}
