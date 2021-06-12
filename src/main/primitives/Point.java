package primitives;

import util.DoubleTriFunction;

/**
 * The {@link Point} class represents a {@link Point} in three dimensional space.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Point extends Triple {
	/** The point (0, 0, 0). */
	public static final Point ORIGIN = new Point(0, 0, 0);
	/** The point (-INF, -INF, -INF). */
	public static final Point NEGATIVE_INFINITY =
		new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
	/** The point (INF, INF, INF). */
	public static final Point POSITIVE_INFINITY =
		new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

	/**
	 * Constructs a {@link Point} from three coordinates.
	 *
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @param z The z-coordinate.
	 */
	public Point(double x, double y, double z) {
		super(x, y, z);
	}

	/**
	 * Adds a {@link NonZeroVector} to this {@link Point} and returns the resulting {@link Point}.
	 *
	 * @param vector The {@link Vector} to add to this {@link Point}.
	 * @return The {@link Point} resulting from adding the {@link NonZeroVector} to this {@link Point}.
	 */
	public Point add(Vector vector) {
		return transform(Double::sum, vector, Point::new);
	}

	/**
	 * Subtract a {@link NonZeroVector} from this {@link Point} and returns the resulting {@link Point}.
	 *
	 * @param vector The {@link NonZeroVector} to subtract from this {@link Point}.
	 * @return The {@link Point} resulting from dubtracting the {@link NonZeroVector} from this {@link Point}.
	 */
	public Point subtract(NonZeroVector vector) {
		return add(vector.reversed());
	}

	/**
	 * Constructs a {@link Vector} from this {@link Point} to the given {@link Point} of the type {@code creator}
	 * returns.
	 *
	 * @param target  The coordinate where the {@link NonZeroVector} is to end, if it were to start from this
	 *                {@link Point}.
	 * @param creator A function which receives three doubles and returns a new {@link Vector}
	 * @return The {@link NonZeroVector} from this {@link Point} to the given {@link Point}.
	 */
	private <T extends Vector> T vectorTo(Point target, DoubleTriFunction<T> creator) {
		return transform((base, aux) -> aux - base, target, creator);
	}

	/**
	 * Constructs a {@link Vector} from this {@link Point} to the given {@link Point}. With this method, the zero
	 * vector may be returned.
	 *
	 * @param target The coordinate where the {@link Vector} is to end, if it were to start from this {@link Point}.
	 * @return The {@link Vector} from this {@link Point} to the given {@link Point}.
	 */
	public Vector vectorBaseTo(Point target) {
		return vectorTo(target, Vector::new);
	}

	/**
	 * Constructs a {@link NonZeroVector} from this {@link Point} to the given {@link Point}.
	 *
	 * @param target The coordinate where the {@link NonZeroVector} is to end, if it were to start from this
	 *               {@link Point}.
	 * @return The {@link NonZeroVector} from this {@link Point} to the given {@link Point}.
	 * @throws ZeroVectorException if the target is equal to this {@link Point}.
	 */
	public NonZeroVector vectorTo(Point target) {
		return vectorTo(target, NonZeroVector::new);
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
		// Construct a point whose coordinates are the squares of the differences of the coordinates of the two points.
		// Sum the coordinates of the square point.
		return transform((base, aux) -> {
			double diff = aux - base;
			return diff * diff;
		}, target, Point::new).sum();
	}

	public Point minimumest(Point target) {
		return transform((base, aux) -> Math.min(base, aux), target, Point::new);
	}

	public Point maximumest(Point target) {
		return transform((base, aux) -> Math.max(base, aux), target, Point::new);
	}

	@Override
	public String toString() {
		return "(" + super.toString() + ")";
	}
}
