package geometries;

import primitives.ZeroVectorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import primitives.LineSegment;
import primitives.Material;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.VectorBase;
import math.compare.DoubleCompare;
import math.equations.Quadratic;

/**
 * This represents a sphere. A sphere is described by a center {@link Point} and
 * a positive radius.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Sphere extends Geometry {
	/** The center of the sphere. */
	public final Point center;
	/** The radius of the sphere. */
	public final double radius;

	/**
	 * Constructs a sphere from a given center point and a radius.
	 *
	 * @param material The {@link Material} the sphere is made from.
	 * @param center   A {@link Point} representing the center of the circle.
	 * @param radius   A positive number representing the radius. If given a negative
	 *                 number will be assumed to be positive.
	 * @throws IllegalArgumentException if the radius is zero.
	 */
	public Sphere(Material material, Point center, double radius) {
		super(material);
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
	public List<Intersection> intersect(LineSegment line) {
		VectorBase centerToRaySource = center.vectorBaseTo(line.start);
		double b = 2 * line.direction.dot(centerToRaySource);
		double c = centerToRaySource.squareLength() - radius * radius;
		Quadratic quadratic = new Quadratic(1, b, c);
		double discriminant = quadratic.discriminant;
		if (DoubleCompare.leq(discriminant, 0)) {
			return Collections.emptyList(); // ray is tangent or doesn't intersect at all
		}
		List<Intersection> result = new ArrayList<>(2);
		for (double t : quadratic.solutions()) {
			Point intersectionPoint = line.travel(t);
			if (intersectionPoint != null) {
				result.add(intersection(intersectionPoint));
			}
		}
		return result;
	}

	@Override
	public BoundingBox border() {
		// TODO Auto-generated method stub
		return null;
	}
}
