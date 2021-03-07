package geometries;

import primitives.Point;
import primitives.NormalizedVector;

/**
 * Represents a three dimensional shape
 */
public interface Geometry {
	/**
	 * Calculates the normal to the shape at the given point.
	 *
	 * @param p The point at which to calculate the normal.
	 * @return A vector perpendicular to the surface of the shape at the given point.
	 */
	public NormalizedVector normal(Point p);
}
