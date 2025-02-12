package geometries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import math.compare.DoubleCompare;
import primitives.LineSegment;
import primitives.Material;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.NonZeroVector;
import primitives.ZeroVectorException;

/**
 * This class represents a polygon in three dimensional space. A polygon is a plane figure that is described by a finite
 * number of straight line segments connected to form a polygonal circuit.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Polygon extends Geometry {

	private final List<Point> vertices;
	private final Plane plane; // The plane which all the points must reside on.
	private final Boundary boundary;
	private final int size;

	/**
	 * This constructor accepts a list of the vertices of the polygon.
	 *
	 * @param material The {@link Material} the polygon is made from.
	 * @param vertices A list of the vertices of the polygon, in order.
	 * @throws IllegalArgumentException if there are less than three significant vertices, any of the vertices are not
	 *                                  on the same plane as the rest, the vertices are out of order and thus form a
	 *                                  non-convex polygon, consecutive vertices are repeated, or the last point is
	 *                                  equal to the first point.
	 */
	public Polygon(Material material, Point... vertices) {
		super(material);
		this.vertices = new ArrayList<>(vertices.length);

		double sum = 0.0; // sum of exterior angles
		for (int i = 0; i < vertices.length; ++i) { // loop through 3-tuples of input vertices
			try {
				Point p1 = vertices[i];
				Point p2 = vertices[(i + 1) % vertices.length];
				Point p3 = vertices[(i + 2) % vertices.length];
				double angle = p1.nonZeroVectorTo(p2).angle(p2.nonZeroVectorTo(p3));
				// if exterior angle is zero, point is on an existing edge and can be ignored
				if (DoubleCompare.eq(angle, 0)) {
					continue;
				}
				this.vertices.add(vertices[i]);
				sum += angle;
			} catch (ZeroVectorException e) {
				throw new IllegalArgumentException(
					"Error: Repeated vertices are not allowed. Perhaps you are repeating the start point at the end.");
			}
		}
		this.size = this.vertices.size();

		// If the sum of the exterior angles is greater than 2 Pi radians then it's not convex or not all points are on
		// the same plane.
		if (DoubleCompare.neq(sum, 2 * Math.PI)) {
			throw new IllegalArgumentException(
				"Error: The polygon must be convex and all the vertices must be on a common plane.");
		}

		// Checks for at least three vertices
		if (this.size < 3) {
			throw new IllegalArgumentException("Error: A polygon must contain at least three vertices.");
		}
		this.boundary = calcBorder(vertices);
		// Construct the plane from the first three vertices (not in a straight line).
		this.plane = new Plane(material, this.vertices.get(0), this.vertices.get(1), this.vertices.get(2));
	}

	static Boundary calcBorder(Point[] vertices) {
		Boundary result = new Boundary(vertices[0]);
		for (int i = 1; i < vertices.length; i++) {
			result = result.union(new Boundary(vertices[i]));
		}
		return result;
	}

	/**
	 * Calculates the normal to the {@link Polygon}. The given {@link Point} is disregarded.
	 *
	 * @param p The point at which to calculate the normal.
	 * @return A vector perpendicular to the {@link Polygon}.
	 */
	@Override
	public NormalizedVector normal(Point p) {
		return plane.normal(p);
	}

	@Override
	public List<Intersection> intersect(LineSegment line) {
		List<Intersection> candidates = plane.intersect(line);
		if (candidates.isEmpty()) { // The ray doesn't intersect the plane
			return candidates;
		}

		// Check if the plane intersection is within the polygon
		Point p1 = vertices.get(0);
		Point p2 = vertices.get(1);
		NonZeroVector normal = line.start.nonZeroVectorTo(p1).cross(p1.nonZeroVectorTo(p2)); // No zero vectors bc ray
																								// intersects the
		// plane
		int comparison = DoubleCompare.compare(normal.dot(line.direction), 0);
		for (int i = 2; i <= size; ++i) { // Loop through consecutive points
			p1 = p2;
			p2 = vertices.get(i % size);
			normal = line.start.nonZeroVectorTo(p1).cross(p1.nonZeroVectorTo(p2));
			if (comparison != DoubleCompare.compare(normal.dot(line.direction), 0)) {
				return Collections.emptyList();
			}
		}
		return candidates;
	}

	@Override
	public Boundary boundary() {
		return boundary;
	}

}
