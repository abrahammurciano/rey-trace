package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;
import primitives.NormalizedVector;

/**
 * Represents a three dimensional shape
 */
public interface Geometry {
	/**
	 * Calculates the normal to the {@link Geometry} at the given {@link Point}. If the given {@link Point} is not on
	 * the surface of the {@link Geometry} the resulting behaviour is undefined.
	 *
	 * @param point The {@link Point} at which to calculate the normal.
	 * @return A {@link NormalizedVector} perpendicular to the surface of the shape at the given {@link Point}.
	 */
	public NormalizedVector normal(Point point);

	/**
	 * Calculates all the intersections between a given {@link Ray} and this {@link Geometry}
	 *
	 * @param ray The {@link Ray} to check for intersections.
	 * @return A list of all the intersections with the given {@link Ray}.
	 */
	public List<Point> intersect(Ray ray);
}
