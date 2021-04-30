package primitives;

import java.util.Objects;

/**
 * The {@link Ray} class represents a ray with it's base at the {@link Point}
 * 'source' and shoots off in direction pointed at by {@link Vector}
 * 'direction'.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Ray {
	public final Point source;
	public final NormalizedVector direction;

	/**
	 * Constructs a {@link Ray} from a source {@link Point} and a direction {@link Vector}.
	 *
	 * @param source The {@link Point} at which the {@link Ray} starts.
	 * @param direction The {@link NormalizedVector} in which the {@link Ray} is directed.
	 */
	public Ray(Point source, NormalizedVector direction) {
		this.source = source;
		this.direction = direction;
	}

	/**
	 * Compares the source and direction of the two {@link Ray}s.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o == null || getClass() != o.getClass()) {
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

	/**
	 * Calculate the point along the ray after traveling "distance" units in the ray's direction.
	 *
	 * @param distance The distance to travel in the ray's direction.
	 * @return The point on the ray after travelling "distance" units.
	 */
	public Point travel(double distance) {
		return source.add(direction.scale(distance));
	}
}
