package geometries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import math.compare.DoubleCompare;
import primitives.LineSegment;
import primitives.Point;
import primitives.Vector;

class BoundingSphere {
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
	BoundingSphere(Point center, double radiusSquared) {
		this.center = center;
		this.radiusSquared = radiusSquared;
	}

	private BoundingSphere(Point center, Point surface, Object __) {
		this(center, center.vectorTo(surface).squareLength());
	}

	BoundingSphere(Point min, Point max) {
		this(min.midPoint(max), min, null);
	}

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

	double surfaceArea() {
		return 4 * Math.PI * radiusSquared;
	}

	boolean contains(Point point) {
		return DoubleCompare.lt(center.vectorTo(point).squareLength(), radiusSquared);
	}
}
