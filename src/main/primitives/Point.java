package primitives;

/**
 * The {@link Point} class represents a {@link Point} in three dimensional space.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Point extends Triple {

	public static final Point ORIGIN = new Point(0, 0, 0);

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
	 * This constructor accepts a {@link Triple} and constructs the appropriate {@link Point} with those values.
	 *
	 * @param triple The {@link Triple} with the coordinates which this {@link Point} has.
	 * @throws ZeroVectorException if this {@link Vector} is the zero vector.
	 */
	public Point(Triple triple) {
		this(triple.x, triple.y, triple.z);
	}

	/**
	 * Adds a {@link Vector} to this {@link Point} and returns the resulting {@link Point}.
	 *
	 * @param vector The {@link Vector} to add to this {@link Point}.
	 * @return The {@link Point} resulting from adding the {@link Vector} to this {@link Point}.
	 */
	public Point add(Vector vector) {
		return new Point(transform(Double::sum, vector));
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
		// Sum the coordinates of the square point.
		return transform((base, aux) -> {
			double diff = aux - base;
			return diff * diff;
		}, target).sum();
	}
}
