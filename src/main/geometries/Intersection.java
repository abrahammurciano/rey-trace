package geometries;

import primitives.Point;

/**
 * This class represents an intersection between a ray and a geometry.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Intersection {
	/** The geometry which the ray intersects. */
	public final Geometry geometry;
	/** The point at which the intersection occurs. */
	public final Point point;

	/**
	 * Construct an intersection at some point with some geometry.
	 *
	 * @param geometry The geometry which the ray intersects.
	 * @param point    The point at which the intersection occurs.
	 */
	public Intersection(Geometry geometry, Point point) {
		this.geometry = geometry;
		this.point = point;
	}
}
