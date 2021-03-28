package primitives;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import util.DoubleCompare;;

/**
 * The {@link Point} class represents a {@link Point} in three dimensional space.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Point {
	/**
	 * The x-coordinate
	 */
	final double x;

	/**
	 * The y-coordinate
	 */
	final double y;

	/**
	 * The z-coordinate
	 */
	final double z;

	/**
	 * Represents the {@link Point} (0, 0, 0)
	 */
	public static final Point ORIGIN = new Point(0, 0, 0);

	/**
	 * Constructs a {@link Point} from three coordinates.
	 *
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @param z The z-coordinate.
	 */
	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Creates a new {@link Point} which is a transformation of this {@link Point} by applying the given transformation to
	 * each of the coordinates.
	 *
	 * @param transformation A function which receives two coordinates and returns another coordinate.
	 * @param aux            An auxiliary {@link Point} whose corresponding coordinate may (or may not) be used in the
	 *                       transformation function in order to calculate each of the new coordinates.
	 * @return The {@link Point} made up of applying the transformation to each of the three coordinates.
	 */
	public Point transform(DoubleBinaryOperator transformation, Point aux) {
		return new Point(transformation.applyAsDouble(x, aux.x),
				transformation.applyAsDouble(y, aux.y), transformation.applyAsDouble(z, aux.z));
	}

	/**
	 * Similar to {@link #transform(DoubleBinaryOperator, Point)} but does not require an auxiliary {@link Point}, since the
	 * transformation when called in this way does not depend on a second coordinate.
	 *
	 * @param transformation A function which receives a single coordinate and returns another coordinate.
	 * @return The {@link Point} made up of applying the transformation to each of the three coordinates.
	 */
	public Point transform(DoubleUnaryOperator transformation) {
		return new Point(transformation.applyAsDouble(x), transformation.applyAsDouble(y),
				transformation.applyAsDouble(z));
	}

	/**
	 * Adds a {@link Vector} to this {@link Point} and returns the resulting {@link Point}.
	 *
	 * @param v The {@link Vector} to add to this {@link Point}.
	 * @return The {@link Point} resulting from adding the {@link Vector} to this {@link Point}.
	 */
	public Point add(Vector v) {
		return transform((base, aux) -> base + aux, v.head);
	}

	/**
	 * Constructs a {@link Vector} from this {@link Point} to the given {@link Point}.
	 *
	 * @param target The coordinate where the {@link Vector} is to end, if it were to start from this {@link Point}.
	 * @return The {@link Vector} from this {@link Point} to the given {@link Point}.
	 * @throws ZeroVectorException if the target is equal to this {@link Point}.
	 */
	public Vector vectorTo(Point target) {
		return new Vector(transform((base, aux) -> aux - base, target));
	}

	/**
	 * Constructs a {@link Vector} from the given {@link Point} to this {@link Point}.
	 *
	 * @param source The coordinate where the {@link Vector} is to start, if it were to end at this {@link Point}.
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
			double diff = aux - base;
			return diff * diff;
		}, target);
		// Sum the coordinates of the square point.
		return squarePoint.sum();
	}

	/**
	 * Calculates the sum of the three coordinates this {@link Point} is made up of.
	 *
	 * @return The sum of the three coordinates this {@link Point} is made up of.
	 */
	public double sum() {
		return x + y + z;
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
		return DoubleCompare.eq(x, point.x) && DoubleCompare.eq(y, point.y)
				&& DoubleCompare.eq(z, point.z);
	}


	/**
	 * Computes the hash function based on that of the array of coordinates.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}


	/**
	 * Returns the {@link Point} as a string in the cartesian representation, e.g. "(0, 0, 0)"
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
