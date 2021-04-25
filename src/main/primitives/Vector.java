package primitives;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * The {@link Vector} class represents a {@link Vector} with it's base at the origin and it's head at the {@link Point}
 * 'head'.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Vector {
	/**
	 * The point where the vector ends if it were to start at the origin.
	 */
	public final Point head;

	/**
	 * This constructor accepts 3 doubles and returns the appropriate {@link Vector}
	 *
	 * @param x The value of the x coordinate.
	 * @param y The value of the y coordinate.
	 * @param z The value of the z coordinate.
	 * @throws ZeroVectorException if the {@link Vector} is the zero vector.
	 */
	public Vector(double x, double y, double z) {
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
	 * each of the coordinates.
	 *
	 * @param transformation A function which receives two doubles and returns another double.
	 * @param aux An auxiliary {@link Vector} whose corresponding coordinate may (or may not) be used in the transformation
	 *        function in order to calculate each of the new coordinates.
	 * @return The {@link Vector} made up of applying the transformation to each of the three coordinates.
	 * @throws ZeroVectorException if the transformation results in the zero vector.
	 */
	public Vector transform(DoubleBinaryOperator transformation, Vector aux) {
		return new Vector(head.transform(transformation, aux.head));
	}

	/**
	 * Similar to {@link #transform(DoubleBinaryOperator, Vector)} but does not require an auxiliary {@link Vector}, since
	 * the transformation when called in this way does not depend on a second coordinate.
	 *
	 * @param transformation A function which receives a dingle double and returns another double.
	 * @return The {@link Vector} made up of applying the transformation to each of the three coordinates.
	 * @throws ZeroVectorException if the transformation results in the zero vector.
	 */
	public Vector transform(DoubleUnaryOperator transformation) {
		return new Vector(head.transform(transformation));
	}

	/**
	 * Adds two {@link Vector}s and returns a new {@link Vector}.
	 *
	 * @param v The {@link Vector} which is to be added to this {@link Vector}.
	 * @return The sum of the two {@link Vector}s.
	 * @throws ZeroVectorException when adding a {@link Vector} with its reverse.
	 */
	public Vector add(Vector v) {
		return new Vector(head.add(v));
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
		return transform(c -> c * factor);
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
		double x = head.y * v.head.z - head.z * v.head.y;
		double y = head.z * v.head.x - head.x * v.head.z;
		double z = head.x * v.head.y - head.y * v.head.x;
		return new Vector(x, y, z);
	}

	/**
	 * Calculates the dot product of two {@link Vector}s
	 *
	 * @param v The {@link Vector} to dot product with this {@link Vector}
	 * @return The dot product of the two {@link Vector}s
	 */
	public double dot(Vector v) {
		// Construct a point whose coordinates are the product of the coordinates of the
		// other two.
		Point p = head.transform((base, aux) -> base * aux, v.head);
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
		double dot = normalized().dot(v.normalized());
		return Math.acos(dot);
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
		return head.equals(vector.head);
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
