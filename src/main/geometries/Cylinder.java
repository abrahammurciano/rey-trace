package geometries;

import primitives.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import math.compare.DoubleCompare;
import primitives.LineSegment;
import primitives.Material;
import primitives.NormalizedVector;
import primitives.Ray;

/**
 * This class represents a cylinder, which is a three-dimensional tube with a height, and closed disks on either end.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class Cylinder extends Geometry {
	private double height;

	private final Tube middle;
	private final Plane bottom;
	private final Plane top;
	private final BoundingBox border;

	/**
	 * This constructs a Cylinder.
	 *
	 * @param material The {@link Material} the cylinder is made from.
	 * @param ray      The ray that makes up the center of the Cylinder.
	 * @param radius   A positive double that represents the radius.
	 * @param height   A positive double that represents the height of the Cylinder.
	 *
	 * @throws IllegalArgumentException if the radius is zero or the height is not positive.
	 */
	public Cylinder(Material material, Ray ray, double radius, double height) {
		super(material);
		middle = new Tube(material, ray, radius);
		if (DoubleCompare.leq(height, 0)) { // if height is 0 it's a disk
			throw new IllegalArgumentException("Error: Height must be a positive number.");
		}
		this.height = height;
		bottom = new Plane(material, ray.start, direction());
		Point topPoint = ray.start.add(direction().scale(height));
		top = new Plane(material, topPoint, direction());
		border = calcBorder(ray.start, topPoint, radius);
	}

	static BoundingBox calcBorder(Point p1, Point p2, double radius) {
		return Sphere.calcBorder(p1, radius).union(Sphere.calcBorder(p2, radius));
	}

	/**
	 * Gets the axis {@link NormalizedVector} of the {@link Cylinder}.
	 *
	 * @return The axis {@link NormalizedVector} of the {@link Cylinder}.
	 */
	private NormalizedVector direction() {
		return middle.axis.direction;
	}

	/**
	 * This function returns the normal vector relative to the {@link Point} p. p is assumed to be on the surface of the
	 * Cylinder. Anything else is undefined behavior.
	 *
	 * @param p The point at which to find the normal vector.
	 */
	@Override
	public NormalizedVector normal(Point p) {
		if (top.contains(p) || bottom.contains(p)) {
			return direction();
		} else {
			return middle.normal(p);
		}
	}

	@Override
	public List<Intersection> intersect(LineSegment line) {
		List<Intersection> intersections = new ArrayList<>(2);
		intersections.addAll(intersectLid(line, bottom));
		intersections.addAll(intersectLid(line, top));
		if (intersections.size() == 2) {
			return intersections;
		}
		intersections.addAll(intersectMiddle(line));
		return intersections;
	}

	// helper function
	private List<Intersection> intersectMiddle(LineSegment line) {
		List<Intersection> intersections = middle.intersect(line);
		if (intersections.isEmpty()) {
			return intersections;
		}
		intersections.removeIf(intersection -> {
			double intersectionHeight = direction().dot(bottom.point.vectorBaseTo(intersection.point));
			return DoubleCompare.leq(intersectionHeight, 0) || DoubleCompare.geq(intersectionHeight, height);
		});
		return intersections;
	}

	// helper function
	private List<Intersection> intersectLid(LineSegment line, Plane lid) {
		List<Intersection> intersection = lid.intersect(line);
		if (!intersection.isEmpty()
			&& DoubleCompare.leq(intersection.get(0).point.squareDistance(lid.point), middle.radius * middle.radius)) {
			return intersection;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public BoundingBox border() {
		return border;
	}
}
