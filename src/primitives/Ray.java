package primitives;

/**
 * The {@link Ray} class represents a ray with it's base at the {@link Point} 'source' and shoots
 * off in direction pointed at by {@link Vector} 'direction'.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Ray {
	private Point source;
	private NormalizedVector direction;

	/**
	 * Constructs a {@link Ray} from a source {@link Point} and a direction {@link Vector}.
	 *
	 * @param source    The {@link Point} at which the {@link Ray} starts.
	 * @param direction The {@link Vector} in which the {@link Ray} is directed.
	 */
	public Ray(Point source, Vector direction) {
		this.source = source;
		this.direction = direction.normalized();
	}

	/**
	 * returns source point of the Ray
	 *
	 * @return source Point
	 */
	public Point getSource() {
		return source;
	}

	/**
	 * returns direction Vector of the Ray
	 *
	 * @return direction Vector
	 */
	public Vector getDirection() {
		return direction;
	}

	/**
	 * Compares the source and direction of the two {@link Ray}s.
	 */
	@Override
	public boolean equals(Object r) {
		if (this == r) {
			return true;
		}
		if (r == null || getClass() != r.getClass()) {
			return false;
		}
		Ray ray = (Ray) r;
		return source.equals(ray.source) && direction.equals(ray.direction);
	}

	/**
	 * Computes the hash code based on that of the source {@link Point} and the direction
	 * {@link Vector}.
	 */
	@Override
	public int hashCode() {
		return source.hashCode() ^ direction.hashCode();
	}

	/**
	 * Returns a string representing the source and direction as a tuple.
	 */
	@Override
	public String toString() {
		// ((0, 0, 0), (1, 1, 1))
		return "(" + source + ", " + direction + ")";
	}
}
