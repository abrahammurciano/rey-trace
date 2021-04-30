package geometries;

import primitives.Point;
import primitives.NormalizedVector;

/**
 * Represents a three dimensional shape
 */
public interface Geometry extends Intersectible {
	/**
	 * Calculates the normal to the {@link Geometry} at the given {@link Point}. If the given {@link Point} is not on
	 * the surface of the {@link Geometry} the resulting behaviour is undefined.
	 *
	 * @param point The {@link Point} at which to calculate the normal.
	 * @return A {@link NormalizedVector} perpendicular to the surface of the shape at the given {@link Point}.
	 */
	public NormalizedVector normal(Point point);
}
