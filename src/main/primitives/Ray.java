package primitives;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import geometries.Intersection;

/**
 * The {@link Ray} class represents a ray with it's base at the {@link Point}
 * 'source' and shoots off in direction pointed at by {@link Vector}
 * 'direction'.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Ray {
	/** The {@link Point} at which the ray starts. */
	public final Point source;
	/** The {@link NormalizedVector} which the ray is pointing towards. */
	public final NormalizedVector direction;

	/**
	 * Constructs a {@link Ray} from a source {@link Point} and a direction {@link Vector}.
	 *
	 * @param source    The {@link Point} at which the {@link Ray} starts.
	 * @param direction The {@link NormalizedVector} in which the {@link Ray} is directed.
	 */
	public Ray(Point source, NormalizedVector direction) {
		this.source = source;
		this.direction = direction;
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

	/**
	 * Returns the closest intersection to this ray's source from the given intersections.
	 *
	 * @param intersections The intersections from which to get the closest one.
	 * @return The closest intersection to the source of the ray.
	 * @throws NoSuchElementException if the list is empty.
	 */
	public Intersection closest(List<Intersection> intersections) {
		Iterator<Intersection> iterator = intersections.iterator();
		Intersection closest = iterator.next(); // this will throw if empty
		double distance = source.squareDistance(closest.point);
		while (iterator.hasNext()) {
			Intersection next = iterator.next();
			double nextDistance = source.squareDistance(next.point);
			if (nextDistance < distance) {
				distance = nextDistance; // save for future checks
				closest = next;
			}
		}
		return closest;
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
}
