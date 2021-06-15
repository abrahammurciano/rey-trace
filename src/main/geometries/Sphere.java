package geometries;

import primitives.ZeroVectorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import primitives.LineSegment;
import primitives.Material;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Vector;
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

	private final Boundary boundary;
	private final Point center;
	private final double radiusSquared;

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
		this.center = center;
		this.radiusSquared = radius * radius;
		if (DoubleCompare.eq(radius, 0)) {
			throw new IllegalArgumentException("Error: Radius must not be zero.");
		}
		this.boundary = calcBorder(center, radius);
	}

	static Boundary calcBorder(Point center, double radius) {
		NonZeroVector toCorner = new NonZeroVector(radius, radius, radius);
		return new Boundary(center.subtract(toCorner), center.add(toCorner));
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
		return center.nonZeroVectorTo(p).normalized();
	}

	@Override
	public List<Intersection> intersect(LineSegment line) {
		Vector toCenter = line.start.vectorTo(center);
		double scalarsMid = toCenter.dot(line.direction);
		double perpendicularDistanceSquared = toCenter.squareLength() - scalarsMid * scalarsMid;
		if (DoubleCompare.geq(perpendicularDistanceSquared, radiusSquared)) {
			return Collections.emptyList();
		}
		double scalarsOffset = Math.sqrt(radiusSquared - perpendicularDistanceSquared);
		List<Intersection> result = new ArrayList<>(2);
		addIfIntersection(result, line, scalarsMid + scalarsOffset);
		addIfIntersection(result, line, scalarsMid - scalarsOffset);
		return result;
	}

	private void addIfIntersection(List<Intersection> result, LineSegment line, double distance) {
		Point p = line.travel(distance);
		if (p != null) {
			result.add(intersection(p));
		}
	}

	@Override
	public Boundary boundary() {
		return boundary;
	}
}
