package primitives;

import java.util.Arrays;

/**
 * The {@link Point} class represents a {@link Point} in three dimensional space.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Point {
	private Coordinate[] coordinates;

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
		coordinates = new Coordinate[] {x, y, z};
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
	 * Gets the x, y, or z {@link Coordinate} given the index (0, 1, or 2).
	 *
	 * @param index The index of the {@link Coordinate} to get.
	 * @throws IllegalArgumentException if index is not between 0 and 2.
	 * @return The x, y, or z {@link Coordinate}.
	 */
	public Coordinate coordinate(int index) {
		try {
			return coordinates[index];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException(e);
		}
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
		return new Point(transformation.transform(coordinate(0), auxiliary.coordinate(0)),
				transformation.transform(coordinate(1), auxiliary.coordinate(1)),
				transformation.transform(coordinate(2), auxiliary.coordinate(2)));
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
		return transform((base, aux) -> base.add(aux), v.head());
	}

	/**
	 * Constructs a {@link Vector} from this {@link Point} to the given {@link Point}.
	 *
	 * @param target The {@link Coordinate} where the {@link Vector} is to end, if it were to start from this {@link Point}.
	 * @return The {@link Vector} from this {@link Point} to the given {@link Point}.
	 */
	public Vector vectorTo(Point target) {
		return new Vector(transform((base, aux) -> aux.subtract(base), target));
	}

	/**
	 * Constructs a {@link Vector} from the given {@link Point} to this {@link Point}.
	 *
	 * @param source The {@link Coordinate} where the {@link Vector} is to start, if it were to end at this {@link Point}.
	 * @return The {@link Vector} to this {@link Point} from the given {@link Point}.
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
		return Arrays.stream(coordinates).reduce(Coordinate::add).get().value();
	}

	/**
	 * Checks if the two {@link Point}s are in the same three dimensional space.
	 */
	@Override
	public boolean equals(Object p) {
		if (this == p) {
			return true;
		}
		if (p == null || getClass() != p.getClass()) {
			return false;
		}
		return Arrays.deepEquals(this.coordinates, ((Point) p).coordinates);
	}

	/**
	 * Computes the hash function based on that of the array of {@link Coordinate}s.
	 */
	@Override
	public int hashCode() {
		return Arrays.hashCode(coordinates);
	}

	/**
	 * Returns the {@link Point} as a string in the cartesian representation, e.g. "(0, 0, 0)"
	 */
	@Override
	public String toString() {
		return "(" + coordinate(0) + ", " + coordinate(1) + ", " + coordinate(2) + ")";
	}
}
