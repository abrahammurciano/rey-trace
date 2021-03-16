package primitives;

import java.util.Objects;

/**
 * The {@link Vector} class represents a {@link Vector} with it's base at the origin and it's head at the {@link Point}
 * 'head'.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Vector {
	final Point head;

	/**
	 * This constructor accepts 3 doubles and returns the appropriate {@link Vector}
	 *
	 * @param x The value of the x {@link Coordinate}.
	 * @param y The value of the y {@link Coordinate}.
	 * @param z The value of the z {@link Coordinate}.
	 * @throws ZeroVectorException if the {@link Vector} is the zero vector.
	 */
	public Vector(double x, double y, double z) {
		this(new Point(x, y, z));
	}

	/**
	 * This constructor accepts 3 {@link Coordinate}s and returns the appropriate {@link Vector}
	 *
	 * @param x The x {@link Coordinate}.
	 * @param y The y {@link Coordinate}.
	 * @param z The z {@link Coordinate}.
	 * @throws ZeroVectorException if this {@link Vector} is the zero vector.
	 */
	public Vector(Coordinate x, Coordinate y, Coordinate z) {
		this(new Point(x, y, z));
	}

	/**
	 * This constructor accepts a {@link Point} and returns the appropriate {@link Vector}
	 *
	 * @param head The {@link Point} which this {@link Vector} would point to if its base was at the origin.
	 * @throws ZeroVectorException if this {@link Vector} is the zero vector.
	 */
	public Vector(Point head) {
		if (head.equals(Point.ORIGIN)) {
			throw new ZeroVectorException();
		}
		this.head = head;
	}

	/**
	 * Creates a new {@link Vector} which is a transformation of this {@link Vector} by applying the given transformation to
	 * each of the {@link Coordinate}s.
	 *
	 * @param transformation A function which receives two {@link Coordinate}s and returns another {@link Coordinate}.
	 * @param auxiliary      An auxiliary {@link Vector} whose corresponding {@link Coordinate} may (or may not) be used in
	 *                       the transformation function in order to calculate each of the new {@link Coordinate}s.
	 * @return The {@link Vector} made up of applying the transformation to each of the three {@link Coordinate}s.
	 * @throws ZeroVectorException if the transformation results in the zero vector.
	 */
	public Vector transform(CoordinateTransformation transformation, Vector auxiliary) {
		return new Vector(head.transform(transformation, auxiliary.head));
	}

	/**
	 * Similar to {@link #transform} but does not require an auxiliary {@link Vector}, since the transformation when called
	 * in this way is not supposed to depend on a second {@link Coordinate}.
	 *
	 * @param transformation A function which receives two {@link Coordinate}s and returns another {@link Coordinate}.
	 * @return The {@link Vector} made up of applying the transformation to each of the three {@link Coordinate}s.
	 * @throws ZeroVectorException if the transformation results in the zero vector.
	 */
	public Vector transform(CoordinateTransformation transformation) {
		return new Vector(head.transform(transformation));
	}

	/**
	 * Adds two {@link Vector}s and returns a new {@link Vector}.
	 *
	 * @param vector The {@link Vector} which is to be added to this {@link Vector}.
	 * @return The sum of the two {@link Vector}s.
	 * @throws ZeroVectorException when adding a {@link Vector} with its reverse.
	 */
	public Vector add(Vector vector) {
		return new Vector(head.add(vector));
	}

	/**
	 * Subtracts two {@link Vector}s and returns a new {@link Vector}.
	 *
	 * @param vector The {@link Vector} to be subtracted from this {@link Vector}.
	 * @return The sum of this {@link Vector} and the negation of the given {@link Vector}.
	 * @throws ZeroVectorException if a {@link Vector} is subtracted from itself.
	 */
	public Vector subtract(Vector vector) {
		return add(vector.scale(-1));
	}

	/**
	 * Constructs a new {@link Vector} which is a scalar multiplication of this {@link Vector} by a scalar.
	 *
	 * @param factor The scalar by which to multiply this {@link Vector}
	 * @return New scaled {@link Vector}
	 * @throws ZeroVectorException if the scale factor is zero.
	 */
	public Vector scale(double factor) {
		return transform((c, __) -> c.multiply(factor));
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
	 * @param vector The {@link Vector} by which to multiply this {@link Vector}
	 * @return The resulting {@link Vector} which is the cross product of the two {@link Vector}s
	 * @throws ZeroVectorException if the result vector is the zero vector.
	 */
	public Vector cross(Vector vector) {
		Coordinate x = head.y.multiply(vector.head.z).subtract(head.z.multiply(vector.head.y));
		Coordinate y = head.z.multiply(vector.head.x).subtract(head.x.multiply(vector.head.z));
		Coordinate z = head.x.multiply(vector.head.y).subtract(head.y.multiply(vector.head.x));
		return new Vector(x, y, z);
	}

	/**
	 * Calculates the dot product of two {@link Vector}s
	 *
	 * @param vector The {@link Vector} to dot product with this {@link Vector}
	 * @return The dot product of the two {@link Vector}s
	 */
	public double dot(Vector vector) {
		// Construct a point whose coordinates are the product of the coordinates of the other two.
		Point p = head.transform((base, aux) -> base.multiply(aux), vector.head);
		return p.sum();
	}

	/**
	 * Calculates the length of this {@link Vector}.
	 *
	 * @return The length of this {@link Vector}.
	 */
	public double length() {
		return head.distance(Point.ORIGIN);
	}

	/**
	 * Calculates the square of the length of this {@link Vector}.
	 *
	 * @return The square of the length of this {@link Vector}.
	 */
	public double squareLength() {
		return head.squareDistance(Point.ORIGIN);
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
		return Math.acos(normalized().dot(v.normalized()));
	}

	/**
	 * Checks if the two {@link Vector}s have the same magnitude and direction.
	 */

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Vector)) {
			return false;
		}
		Vector vector = (Vector) o;
		return Objects.equals(head, vector.head);
	}

	/**
	 * Computes the hash function based on that of {@link Point}.
	 */
	@Override
	public int hashCode() {
		return head.hashCode();
	}

	/**
	 * Returns the head of the {@link Vector} as a string.
	 */
	@Override
	public String toString() {
		return head.toString();
	}
}
