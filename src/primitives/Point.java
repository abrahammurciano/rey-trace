package primitives;

/**
 * The {@link Point} class represents a {@link Point} in three dimensional space.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Point {
	Coordinate x;
	Coordinate y;
	Coordinate z;

	/**
	 * Represents the {@link Point} (0, 0, 0)
	 */
	public static final Point ORIGIN = new Point(0, 0, 0);

	/**
	 * Constructs a {@link Point} from three {@link Coordinate}s.
	 *
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @param z The z-coordinate.
	 */
	public Point(Coordinate x, Coordinate y, Coordinate z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Constructs a {@link Point} from three {@link Coordinate}s' values.
	 *
	 * @param x The x-coordinate value.
	 * @param y The y-coordinate value.
	 * @param z The z-coordinate value.
	 */
	public Point(double x, double y, double z) {
		this(new Coordinate(x), new Coordinate(y), new Coordinate(z));
	}

	/**
	 * Creates a new {@link Point} which is a transformation of this {@link Point} by applying the given transformation to
	 * each of the {@link Coordinate}s.
	 *
	 * @param transformation A function which receives two {@link Coordinate}s and returns another {@link Coordinate}.
	 * @param auxiliary      An auxiliary {@link Point} whose corresponding {@link Coordinate} may (or may not) be used in
	 *                       the transformation function in order to calculate each of the new {@link Coordinate}s.
	 * @return The {@link Point} made up of applying the transformation to each of the three {@link Coordinate}s.
	 */
	public Point transform(CoordinateTransformation transformation, Point auxiliary) {
		return new Point(transformation.transform(x, auxiliary.x),
				transformation.transform(y, auxiliary.y), transformation.transform(z, auxiliary.z));
	}

	/**
	 * Similar to {@link #transform(CoordinateTransformation, Point)} but does not require an auxiliary {@link Point}, since
	 * the transformation when called in this way is not supposed to depend on a second {@link Coordinate}.
	 *
	 * @param transformation A function which receives two {@link Coordinate}s and returns another {@link Coordinate}.
	 * @return The {@link Point} made up of applying the transformation to each of the three {@link Coordinate}s.
	 */
	public Point transform(CoordinateTransformation transformation) {
		return transform(transformation, ORIGIN);
	}

	/**
	 * Adds a {@link Vector} to this {@link Point} and returns the resulting {@link Point}.
	 *
	 * @param v The {@link Vector} to add to this {@link Point}.
	 * @return The {@link Point} resulting from adding the {@link Vector} to this {@link Point}.
	 */
	public Point add(Vector v) {
		return transform((base, aux) -> base.add(aux), v.head);
	}

	/**
	 * Constructs a {@link Vector} from this {@link Point} to the given {@link Point}.
	 *
	 * @param target The {@link Coordinate} where the {@link Vector} is to end, if it were to start from this {@link Point}.
	 * @return The {@link Vector} from this {@link Point} to the given {@link Point}.
	 * @throws ZeroVectorException if the target is equal to this {@link Point}.
	 */
	public Vector vectorTo(Point target) {
		return new Vector(transform((base, aux) -> aux.subtract(base), target));
	}

	/**
	 * Constructs a {@link Vector} from the given {@link Point} to this {@link Point}.
	 *
	 * @param source The {@link Coordinate} where the {@link Vector} is to start, if it were to end at this {@link Point}.
	 * @return The {@link Vector} to this {@link Point} from the given {@link Point}.
	 * @throws ZeroVectorException if the source is equal to this {@link Point}.
	 */
	public Vector vectorFrom(Point source) {
		return source.vectorTo(this);
	}

	/**
	 * Calculates the distance between this {@link Point} and the target {@link Point}.
	 *
	 * @param target The {@link Point} to calculate the distance to.
	 * @return The distance between this {@link Point} and the target {@link Point}.
	 */
	public double distance(Point target) {
		return Math.sqrt(squareDistance(target));
	}

	/**
	 * The square of the distance between this {@link Point} and the target {@link Point}. This number is much more
	 * efficient to compute than {@link distance} since it does not involve a square root.
	 *
	 * @param target The {@link Point} to calculate the distance to.
	 * @return The square of the distance between this {@link Point} and the target {@link Point}.
	 */
	public double squareDistance(Point target) {
		// Construct a point whose coordinates are the squares of the differences of the coordinates
		// of the two points.
		Point squarePoint = transform((base, aux) -> {
			Coordinate diff = aux.subtract(base);
			return diff.multiply(diff);
		}, target);
		// Sum the coordinates of the square point.
		return squarePoint.sum();
	}

	/**
	 * Calculates the sum of the three {@link Coordinate}s this {@link Point} is made up of.
	 *
	 * @return The sum of the three {@link Coordinate}s this {@link Point} is made up of.
	 */
	public double sum() {
		return x.add(y).add(z).value;
	}

	/**
	 * Checks if the two {@link Point}s are in the same three dimensional space.
	 */

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Point)) {
			return false;
		}
		Point point = (Point) o;
		return x.equals(point.x) && y.equals(point.y) && z.equals(point.z);
	}

	/**
	 * Computes the hash function based on that of the array of {@link Coordinate}s.
	 */
	@Override
	public int hashCode() {
		return x.hashCode() ^ y.hashCode() ^ z.hashCode();
	}

	/**
	 * Returns the {@link Point} as a string in the cartesian representation, e.g. "(0, 0, 0)"
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
