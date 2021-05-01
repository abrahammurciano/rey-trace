package geometries;

import primitives.ZeroVectorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import math.compare.DoubleCompare;
import math.equations.Quadratic;

/**
 * This represents a sphere. A sphere is described by a center {@link Point} and
 * a positive radius.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Sphere implements Geometry {
	public final Point center;
	public final double radius;

	/**
	 * Constructs a sphere from a given center point and a radius.
	 *
	 * @param center A {@link Point} representing the center of the circle.
	 * @param radius A positive number representing the radius. If given a negative
	 *               number will be assumed to be positive.
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
	 * @param p The point to get normal at. This point is assumed to be on the
	 *          surface of the circle. If it's not then it is undefined behavior.
	 * @throws ZeroVectorException if p is the center of the sphere.
	 */
	@Override
	public NormalizedVector normal(Point p) {
		return center.vectorTo(p).normalized();
	}

	@Override
	public List<Point> intersect(Ray ray) {
		Vector centerToRaySource;
		try {
			centerToRaySource = center.vectorTo(ray.source);
		} catch (ZeroVectorException __) { // ray starts at center
			return List.of(ray.travel(radius));
		}
		double b = 2 * ray.direction.dot(centerToRaySource);
		double c = centerToRaySource.squareLength() - radius * radius;
		Quadratic quadratic = new Quadratic(1, b, c);
		double discriminant = quadratic.discriminant;
		if (DoubleCompare.leq(discriminant, 0)) {
			return Collections.emptyList(); // ray is tangent or doesn't intersect at all
		}
		List<Point> result = new ArrayList<>(2);
		for (double t : quadratic.solutions()) {
			if (DoubleCompare.gt(t, 0)) {
				result.add(ray.travel(t));
			}
		}
		return result;
	}
}
