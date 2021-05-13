package geometries;

import primitives.Material;
import primitives.NormalizedVector;
import primitives.Point;

/**
 * Represents a three dimensional shape
 */
public abstract class Geometry implements Intersectible {
	/** The material of this geometry. */
	public final Material material;

	protected Geometry(Material material) {
		this.material = material;
	}

	/**
	 * Calculates the normal to the {@link Geometry} at the given {@link Point}. If the given {@link Point} is not on
	 * the surface of the {@link Geometry} the resulting behaviour is undefined.
	 *
	 * @param point The {@link Point} at which to calculate the normal.
	 * @return A {@link NormalizedVector} perpendicular to the surface of the shape at the given {@link Point}.
	 */
	public abstract NormalizedVector normal(Point point);

	/**
	 * Create an intersection object with this geometry and the given {@link Point}.
	 *
	 * @param point The point at which the intersection occurs.
	 * @return An {@link Intersection} with this geometry at the given point.
	 */
	protected Intersection intersection(Point point) {
		return new Intersection(this, point);
	}
}
