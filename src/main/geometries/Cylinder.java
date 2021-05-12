package geometries;

import primitives.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import math.compare.DoubleCompare;
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

	private Tube middle;
	private Plane bottom;
	private Plane top;

	/**
	 * This constructs a Cylinder.
	 *
	 * @param ray    The ray that makes up the center of the Cylinder.
	 * @param radius A positive double that represents the radius.
	 * @param height A positive double that represents the height of the Cylinder.
	 *
	 * @throws IllegalArgumentException if the radius is zero or the height is not positive.
	 */
	public Cylinder(Ray ray, double radius, double height) {
		middle = new Tube(ray, radius);
		if (DoubleCompare.leq(height, 0)) { // if height is 0 it's a disk
			throw new IllegalArgumentException("Error: Height must be a positive number.");
		}
		this.height = height;
		bottom = new Plane(ray.source, direction());
		top = new Plane(ray.source.add(direction().scale(height)), direction());
	}

	/**
	 * Gets the axis {@link NormalizedVector} of the {@link Cylinder}.
	 *
	 * @return The axis {@link NormalizedVector} of the {@link Cylinder}.
	 */
	public NormalizedVector direction() {
		return middle.direction();
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
			return middle.direction();
		} else {
			return middle.normal(p);
		}
	}

	/**
	 * This function will find intersection points (possibly none) between a {@link Ray} and an {@link Cylinder}.
	 *
	 * @param ray The {@link Ray} to intersect
	 * @return a list (possibly empty) of intersection points
	 */
	@Override
	public List<Intersection> intersect(Ray ray) {
		List<Intersection> intersections = new ArrayList<>(2);
		intersections.addAll(intersectLid(ray, bottom));
		intersections.addAll(intersectLid(ray, top));
		if (intersections.size() == 2) {
			return intersections;
		}
		intersections.addAll(intersectMiddle(ray));
		return intersections;
	}

	// helper function
	private List<Intersection> intersectMiddle(Ray ray) {
		List<Intersection> intersections = middle.intersect(ray);
		if (intersections.isEmpty()) {
			return intersections;
		}
		intersections.removeIf(intersection -> {
			double intersectionHeight = middle.axis.direction.dot(bottom.point.vectorBaseTo(intersection.point));
			return DoubleCompare.leq(intersectionHeight, 0) || DoubleCompare.geq(intersectionHeight, height);
		});
		return intersections;
	}

	// helper function
	private List<Intersection> intersectLid(Ray ray, Plane lid) {
		List<Intersection> intersection = lid.intersect(ray);
		if (!intersection.isEmpty()
			&& DoubleCompare.leq(intersection.get(0).point.squareDistance(lid.point), middle.radius * middle.radius)) {
			return intersection;
		} else {
			return Collections.emptyList();
		}
	}
}
