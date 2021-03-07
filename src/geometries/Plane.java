package geometries;

import primitives.Point;
import primitives.Util;
import primitives.Vector;

/**
 * A {@link Plane} is a flat two dimensional surface in three dimensional space which goes off to
 * infinity in all directions.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Plane implements Geometry {

	private Point point;
	private Vector normal;

	/**
	 * This constructor accepts a point on the plane and a vector perpendicular to the plane.
	 *
	 * @param point  A point on the plane.
	 * @param normal A vector perpendicular to the plane.
	 */
	public Plane(Point point, Vector normal) {
		this.point = point;
		this.normal = normal;
	}

	/**
	 * This constructor accepts three distinct points on the plane.
	 *
	 * @param p1 A point on the plane.
	 * @param p2 A point on the plane.
	 * @param p3 A point on the plane.
	 * @throws IllegalArgumentException if the three points are on a single line.
	 */
	public Plane(Point p1, Point p2, Point p3) {
		Vector v1 = p1.vectorTo(p2);
		Vector v2 = p2.vectorTo(p3);
		try {
			this.normal = v1.cross(v2).normalized();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(
					"Error: The three points must not be on the same line.");
		}
		this.point = p1;
	}

	/**
	 * Calculates the normal to the {@link Plane}. The given {@link Point} is disregarded.
	 *
	 * @param p The {@link Point} at which to calculate the normal.
	 * @return A vector perpendicular to the surface of the {@link Plane}.
	 */
	@Override
	public Vector normal(Point p) {
		return normal;
	}

}
