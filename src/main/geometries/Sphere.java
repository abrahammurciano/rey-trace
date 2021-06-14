package geometries;

import primitives.ZeroVectorException;
import java.util.ArrayList;
import java.util.List;
import primitives.LineSegment;
import primitives.Material;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.NonZeroVector;
import math.compare.DoubleCompare;

/**
 * This represents a sphere. A sphere is described by a center {@link Point} and
 * a positive radius.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Sphere extends Geometry {

	private final BasicSphere basicSphere;
	private final Boundary boundary;

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
		basicSphere = new BasicSphere(center, radius * radius);
		this.boundary = calcBorder(center, radius);
	}

	static Boundary calcBorder(Point center, double radius) {
		NonZeroVector toEdge = NormalizedVector.I.scale(radius);
		return new Boundary(center.subtract(toEdge), center.add(toEdge));
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
		return basicSphere.center.nonZeroVectorTo(p).normalized();
	}

	@Override
	public List<Intersection> intersect(LineSegment line) {
		List<Intersection> result = new ArrayList<>(2);
		basicSphere.intersect(line).forEach(p -> result.add(intersection(p)));
		return result;
	}

	@Override
	public Boundary boundary() {
		return boundary;
	}
}
