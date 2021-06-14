package geometries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import math.compare.DoubleCompare;
import primitives.LineSegment;
import primitives.Point;
import primitives.Vector;

/**
 * This class represents a sphere and has some of the operations that can be performed with a sphere. This class is used
 * by {@link Boundary} and by {@Sphere}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
class BasicSphere {
	/** The center of the sphere. */
	final Point center;
	/** The radius of the sphere. */
	final double radiusSquared;

	/**
	 * Constructs a sphere from a given center point and a radius.
	 *
	 * @param material      The {@link Material} the sphere is made from.
	 * @param center        A {@link Point} representing the center of the circle.
	 * @param radiusSquared A positive number representing the radius. If given a negative
	 *                      number will be assumed to be positive.
	 * @throws IllegalArgumentException if the radius is zero.
	 */
	BasicSphere(Point center, double radiusSquared) {
		this.center = center;
		this.radiusSquared = radiusSquared;
	}

	/**
	 * Construct the smallest possible sphere that contains the two given points.
	 *
	 * @param p1 One point on the surface.
	 * @param p2 Another point on the surface.
	 */
	BasicSphere(Point p1, Point p2) {
		this(p1.midPoint(p2), p1, null);
	}

	/**
	 * Construct a sphere given the center and a point on the surface.
	 *
	 * @param center  The center of the sphere.
	 * @param surface A point on the surface of the sphere.
	 * @param __      A dummy parameter to distinguish between this constructor and {@link #BasicSphere(Point, Point)}.
	 */
	private BasicSphere(Point center, Point surface, Object __) {
		this(center, center.vectorTo(surface).squareLength());
	}

	/**
	 * Given a line segment, this method calculates the points of intersection between this sphere and the line segment.
	 *
	 * @param line The {@link LineSegment} to check for intersections with this sphere.
	 * @return A list of points where the intersections are.
	 */
	List<Point> intersect(LineSegment line) {
		Vector toCenter = line.start.vectorTo(center);
		double scalarsMid = toCenter.dot(line.direction);
		double perpendicularDistanceSquared = toCenter.squareLength() - scalarsMid * scalarsMid;
		if (DoubleCompare.geq(perpendicularDistanceSquared, radiusSquared)) {
			return Collections.emptyList();
		}
		double scalarsOffset = Math.sqrt(radiusSquared - perpendicularDistanceSquared);
		List<Point> result = new ArrayList<>(2);
		addIfIntersection(result, line, scalarsMid + scalarsOffset);
		addIfIntersection(result, line, scalarsMid - scalarsOffset);
		return result;
	}

	private void addIfIntersection(List<Point> result, LineSegment line, double distance) {
		Point p = line.travel(distance);
		if (p != null) {
			result.add(p);
		}
	}

	/**
	 * Calculate the surface area of the sphere.
	 *
	 * @return The surface area of the sphere.
	 */
	double surfaceArea() {
		return 4 * Math.PI * radiusSquared;
	}

	/**
	 * Determines if a point is enclosed within the sphere.
	 *
	 * @param point The point to test.
	 * @return True if the point is enclosed within the sphere, false otherwise.
	 */
	boolean contains(Point point) {
		return DoubleCompare.lt(center.vectorTo(point).squareLength(), radiusSquared);
	}
}
