package geometries;

import primitives.ZeroVectorException;
import primitives.NormalizedVector;
import primitives.Point;
import util.DoubleCompare;

/**
 * This represents a sphere. A sphere is described by a center {@link Point} and a positive radius.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Sphere implements Geometry {
	private Point center;
	private double radius;

	/**
	 * Constructs a sphere from a given center point and a radius.
	 *
	 * @param center A {@link Point} representing the center of the circle.
	 * @param radius A positive number representing the radius. If given a negative number will be assumed to be positive.
	 * @throws IllegalArgumentException if the radius is zero.
	 */
	public Sphere(Point center, double radius) {
		if (DoubleCompare.eq(radius, 0)) {
			throw new IllegalArgumentException("Error: Radius must not be zero.");
		}
		this.center = center;
		this.radius = Math.abs(radius);
	}

	/**
	 * Returns normal to the sphere at given point.
	 *
	 * @param p The point to get normal at. This point is assumed to be on the surface of the circle. If it's not then it is
	 *			undefined behavior.
	 * @throws ZeroVectorException if p is the center of the sphere.
	 */
	@Override
	public NormalizedVector normal(Point p) {
		return center.vectorTo(p).normalized();
	}
}
