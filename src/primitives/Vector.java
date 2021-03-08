package primitives;

/**
 * The {@link Vector} class represents a {@link Vector} with it's base at the origin and it's head
 * at the {@link Point} 'head'.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Vector {
	protected Point head;

	/**
	 * This constructor accepts 3 doubles and returns the appropriate {@link Vector}
	 *
	 * @param x The value of the x {@link Coordinate}.
	 * @param y The value of the y {@link Coordinate}.
	 * @param z The value of the z {@link Coordinate}.
	 * @throws IllegalArgumentException if the {@link Vector} is the zero vector.
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
	 * @throws IllegalArgumentException if this {@link Vector} is the zero vector.
	 */
	public Vector(Coordinate x, Coordinate y, Coordinate z) {
		this(new Point(x, y, z));
	}

	/**
	 * This constructor accepts a {@link Point} and returns the appropriate {@link Vector}
	 *
	 * @param head The {@link Point} which this {@link Vector} would point to if its base was at the
	 *             origin.
	 * @throws IllegalArgumentException if this {@link Vector} is the zero vector.
	 */
	public Vector(Point head) {
		if (head.equals(Point.ORIGIN)) {
			throw new IllegalArgumentException("Error: Zero vector is not allowed.");
		}
		this.head = head;
	}

	/**
	 * Gets the head of the {@link Vector}.
	 */
	public Point head() {
		return head;
	}

	/**
	 * Creates a new {@link Vector} which is a transformation of this {@link Vector} by applying the
	 * given transformation to each of the {@link Coordinate}s.
	 *
	 * @param transformation A function which receives two {@link Coordinate}s and returns another
	 *                       {@link Coordinate}.
	 * @param auxiliary      An auxiliary {@link {@link Vector}} whose corresponding
	 *                       {@link Coordinate} may (or may not) be used in the transformation
	 *                       function in order to calculate each of the new {@link Coordinate}s.
	 * @return The {@link Vector} made up of applying the transformation to each of the three
	 *         {@link Coordinate}s.
	 */
	public Vector transform(CoordinateTransformation transformation, Vector auxiliary) {
		return new Vector(head().transform(transformation, auxiliary.head()));
	}

	/**
	 * Similar to {@link #transform} but does not require an auxiliary {@link Vector}, since the
	 * transformation when called in this way is not supposed to depend on a second
	 * {@link Coordinate}.
	 *
	 * @param transformation A function which receives two {@link Coordinate}s and returns another
	 *                       {@link Coordinate}.
	 * @return The {@link Vector} made up of applying the transformation to each of the three
	 *         {@link Coordinate}s.
	 */
	public Vector transform(CoordinateTransformation transformation) {
		return new Vector(head().transform(transformation));
	}

	/**
	 * Adds two {@link Vector}s and returns a new {@link Vector}.
	 *
	 * @param vector The {@link Vector} which is to be added to this {@link Vector}.
	 * @return The sum of the two {@link Vector}s.
	 */
	public Vector add(Vector vector) {
		return new Vector(head().add(vector));
	}

	/**
	 * Subtracts two {@link Vector}s and returns a new {@link Vector}.
	 *
	 * @param vector The {@link Vector} to be subtracted from this {@link Vector}.
	 * @return The sum of this {@link Vector} and the negation of the given {@link Vector}.
	 */
	public Vector subtract(Vector vector) {
		return add(vector.scale(-1));
	}

	/**
	 * Constructs a new {@link Vector} which is a scalar multiplication of this {@link Vector} by a
	 * scalar.
	 *
	 * @param factor The scalar by which to multiply this {@link Vector}
	 * @return New scaled {@link Vector}
	 */
	public Vector scale(double factor) {
		return transform((c, __) -> c.multiply(factor));
	}

	/**
	 * An alias for {@link #scale(-1)}.
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
	 * @throws IllegalArgumentException if the result vector is the zero vector.
	 */
	public Vector cross(Vector vector) {
		Coordinate[] coordinates = new Coordinate[3];
		for (int i = 0; i < coordinates.length; ++i) {
			coordinates[i] = head().coordinate((i + 1) % coordinates.length)
					.multiply(vector.head().coordinate((i + 2) % coordinates.length))
					.subtract(head().coordinate((i + 2) % coordinates.length)
							.multiply(vector.head().coordinate((i + 1) % coordinates.length)));
		}
		return new Vector(coordinates[0], coordinates[1], coordinates[2]);
	}

	/**
	 * Calculates the dot product of two {@link Vector}s
	 *
	 * @param vector The {@link Vector} to dot product with this {@link Vector}
	 * @return The dot product of the two {@link Vector}s
	 */
	public double dot(Vector vector) {
		// Construct a vector whose coordinates are the product of the coordinates of the other two.
		Vector v = transform((base, aux) -> base.multiply(aux), vector);
		return v.head().sum();
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
		return new NormalizedVector(head);
	}

	/**
	 * Calculates the angle in radians between this vector and the given vector. The angle is
	 * normalized between zero and Pi.
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
	public boolean equals(Object v) {
		if (this == v) {
			return true;
		}
		if (v == null || getClass() != v.getClass()) {
			return false;
		}
		return head.equals(((Vector) v).head);
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
