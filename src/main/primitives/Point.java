package primitives;

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
	 * Adds a {@link Vector} to this {@link Point} and returns the resulting {@link Point}.
	 *
	 * @param vector The {@link VectorBase} to add to this {@link Point}.
	 * @return The {@link Point} resulting from adding the {@link Vector} to this {@link Point}.
	 */
	public Point add(VectorBase vector) {
		return transform(Double::sum, vector, Point::new);
	}

	/**
	 * Subtract a {@link Vector} from this {@link Point} and returns the resulting {@link Point}.
	 *
	 * @param vector The {@link Vector} to subtract from this {@link Point}.
	 * @return The {@link Point} resulting from dubtracting the {@link Vector} from this {@link Point}.
	 */
	public Point subtract(Vector vector) {
		return add(vector.reversed());
	}

	/**
	 * Constructs a {@link VectorBase} from this {@link Point} to the given {@link Point} of the type {@code creator}
	 * returns.
	 *
	 * @param target  The coordinate where the {@link Vector} is to end, if it were to start from this {@link Point}.
	 * @param creator A function which receives three doubles and returns a new {@link VectorBase}
	 * @return The {@link Vector} from this {@link Point} to the given {@link Point}.
	 */
	private <T extends VectorBase> T vectorTo(Point target, TripleCreator<T> creator) {
		return transform((base, aux) -> aux - base, target, creator);
	}

	/**
	 * Constructs a {@link VectorBase} from this {@link Point} to the given {@link Point}. With this method, the zero
	 * vector may be returned.
	 *
	 * @param target The coordinate where the {@link VectorBase} is to end, if it were to start from this {@link Point}.
	 * @return The {@link VectorBase} from this {@link Point} to the given {@link Point}.
	 */
	public VectorBase vectorBaseTo(Point target) {
		return vectorTo(target, VectorBase::new);
	}

	/**
	 * Constructs a {@link Vector} from this {@link Point} to the given {@link Point}.
	 *
	 * @param target The coordinate where the {@link Vector} is to end, if it were to start from this {@link Point}.
	 * @return The {@link Vector} from this {@link Point} to the given {@link Point}.
	 * @throws ZeroVectorException if the target is equal to this {@link Point}.
	 */
	public Vector vectorTo(Point target) {
		return vectorTo(target, Vector::new);
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

	@Override
	public String toString() {
		return "(" + super.toString() + ")";
	}
}
