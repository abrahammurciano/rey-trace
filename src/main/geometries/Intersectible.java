package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * This interface applies to objects which can be intersected with a ray to
 * obtain the points of intersection.
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
	public List<Point> intersect(Ray ray);

}
