package primitives;

import java.util.Arrays;

/**
 * The Point class represents a point in three dimensional space.
 *
 * @author Abraham Murciano and Eli Levin
 */
public class Point {
	private Coordinate[] coordinates;

	/**
	 * Represents the point (0, 0, 0)
	 */
	public static final Point ORIGIN = new Point(0, 0, 0);

	/**
	 * Constructs a point from three coordinates.
	 *
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @param z The z-coordinate.
	 */
	public Point(Coordinate x, Coordinate y, Coordinate z) {
		coordinates = new Coordinate[] {x, y, z};
	}

	/**
	 * Constructs a point from three coordinates' values.
	 *
	 * @param x The x-coordinate value.
	 * @param y The y-coordinate value.
	 * @param z The z-coordinate value.
	 */
	public Point(double x, double y, double z) {
		this(new Coordinate(x), new Coordinate(y), new Coordinate(z));
	}

	/**
	 * Gets the x, y, or z coordinate given the index (0, 1, or 2).
	 *
	 * @param index The index of the coordinate to get.
	 * @throws IllegalArgumentException if index is not between 0 and 2.
	 * @return
	 */
	public Coordinate getCoordinate(int index) {
		try {
			return coordinates[index];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Creates a new point which is a transformation of this point by applying the given
	 * transformation to each of the coordinates.
	 *
	 * @param transformation A function which receives two coordinates and returns another
	 *                       coordinate.
	 * @param auxiliary      An auxiliary point whose corresponding coordinate may (or may not) be
	 *                       used in the transformation function in order to calculate each of the
	 *                       new coordinates.
	 * @return The point made up of applying the transformation to each of the three coordinates.
	 */
	public Point transform(CoordinateTransformation transformation, Point auxiliary) {
		return new Point(transformation.transform(getCoordinate(0), auxiliary.getCoordinate(0)),
				transformation.transform(getCoordinate(1), auxiliary.getCoordinate(1)),
				transformation.transform(getCoordinate(2), auxiliary.getCoordinate(2)));
	}

	/**
	 * Similar to {@link #transform} but does not require an auxiliary point, since the
	 * transformation when called in this way is not supposed to depend on a second coordinate.
	 *
	 * @param transformation A function which receives two coordinates and returns another
	 *                       coordinate.
	 * @return The point made up of applying the transformation to each of the three coordinates.
	 */
	public Point transform(CoordinateTransformation transformation) {
		return transform(transformation, ORIGIN);
	}

	/**
	 * Adds a vector to this point and returns the resulting point.
	 *
	 * @param v The vector to add to this point.
	 * @return The point resulting from adding the vector to this point.
	 */
	public Point add(Vector v) {
		return transform((base, aux) -> base.add(aux), v.getHead());
	}

	/**
	 * Constructs a vector from this point to the given point.
	 *
	 * @param target The coordinate where the vector is to end, if it were to start from this point.
	 * @return The vector from this point to the given point.
	 */
	public Vector vectorTo(Point target) {
		return new Vector(transform((base, aux) -> aux.subtract(base), target));
	}

	/**
	 * Calculates the distance between this point and the target point.
	 *
	 * @param target The point to calculate the distance to.
	 * @return The distance between this point and the target point.
	 */
	public double distance(Point target) {
		return Math.sqrt(squareDistance(target));
	}

	/**
	 * The square of the distance between this point and the target point. This number is much more
	 * efficient to compute than {@link distance} since it does not involve a square root.
	 *
	 * @param target The point to calculate the distance to.
	 * @return The square of the distance between this point and the target point.
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
	 * Calculates the sum of the three coordinates this point is made up of.
	 *
	 * @return The sum of the three coordinates this point is made up of.
	 */
	public double sum() {
		return Arrays.stream(coordinates).reduce(Coordinate::add).get().getValue();
	}

	/**
	 * Checks if the two points are in the same three dimensional space.
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
	 * Computes the hash function based on that of the array of coordinates.
	 */
	@Override
	public int hashCode() {
		return Arrays.hashCode(coordinates);
	}

	/**
	 * Returns the point as a string in the cartesian representation, e.g. "(0, 0, 0)"
	 */
	@Override
	public String toString() {
		return "(" + getCoordinate(0) + ", " + getCoordinate(1) + ", " + getCoordinate(2) + ")";
	}
}
