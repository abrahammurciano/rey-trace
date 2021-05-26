package primitives;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import geometries.Intersection;
import math.compare.DoubleCompare;

/**
 * The {@link Ray} class represents a ray with it's base at the {@link Point}
 * 'source' and shoots off in direction pointed at by {@link Vector}
 * 'direction'.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Ray extends LineSegment {

	/**
	 * Constructs a {@link Ray} from a source {@link Point} and a direction {@link Vector}.
	 *
	 * @param start     The {@link Point} at which the {@link Ray} starts.
	 * @param direction The {@link NormalizedVector} in which the {@link Ray} is directed.
	 */
	public Ray(Point start, NormalizedVector direction) {
		super(start, direction, Double.POSITIVE_INFINITY);
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
		double distance = start.squareDistance(closest.point);
		while (iterator.hasNext()) {
			Intersection next = iterator.next();
			double nextDistance = start.squareDistance(next.point);
			if (nextDistance < distance) {
				distance = nextDistance; // save for future checks
				closest = next;
			}
		}
		return closest;
	}

	@Override
	protected boolean withinDistance(double distance) {
		return DoubleCompare.gt(distance, 0);
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
		return Objects.equals(start, ray.start) && Objects.equals(direction, ray.direction);
	}

	/**
	 * Computes the hash code based on that of the source {@link Point} and the direction {@link Vector}.
	 */
	@Override
	public int hashCode() {
		return start.hashCode() ^ direction.hashCode();
	}

	/**
	 * Returns a string representing the source and direction as a tuple.
	 */
	@Override
	public String toString() {
		// ((0, 0, 0), (1, 1, 1))
		return "(" + start + ", " + direction + ")";
	}
}
