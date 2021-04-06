package primitives;

import java.util.Objects;

/**
 * The {@link Ray} class represents a ray with it's base at the {@link Point} 'source' and shoots off in direction
 * pointed at by {@link Vector} 'direction'.
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
	 * Gets source {@link Point} of the {@link Ray}.
	 *
	 * @return The source {@link Point} of the {@link Ray}.
	 */
	public Point source() {
		return source;
	}

	/**
	 * Gets direction {@link Vector} of the {@link Ray}.
	 *
	 * @return The normalized direction {@link Vector}.
	 */
	public NormalizedVector direction() {
		return direction;
	}

	/**
	 * Compares the source and direction of the two {@link Ray}s.
	 */

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Ray)) {
			return false;
		}
		Ray ray = (Ray) o;
		return Objects.equals(source, ray.source) && Objects.equals(direction, ray.direction);
	}

	/**
	 * Computes the hash code based on that of the source {@link Point} and the direction {@link Vector}.
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