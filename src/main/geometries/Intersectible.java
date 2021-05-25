package geometries;

import java.util.List;
import primitives.Ray;

/**
 * This interface applies to objects which can be intersected with a ray to obtain the points of intersection.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public interface Intersectible {

	/**
	 * Calculates all the intersections between a given {@link Ray} and this object.
	 *
	 * @param ray The {@link Ray} to check for intersections.
	 * @return A list of all the intersections with the given {@link Ray}.
	 */
	public default List<Intersection> intersect(Ray ray) {
		return intersect(ray, Double.POSITIVE_INFINITY);
	}

	/**
	 * Calculates all the intersections between a given {@link Ray} and this object within a distance equal to the
	 * square root of {@code maxSquareDistance}.
	 *
	 * @param ray               The {@link Ray} to check for intersections.
	 * @param maxSquareDistance The square of the maximum distance along the ray for which to check intersections.
	 * @return A list of all the intersections with the given {@link Ray}.
	 */
	public List<Intersection> intersect(Ray ray, double maxSquareDistance);

}
