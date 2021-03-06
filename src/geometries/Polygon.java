package geometries;

import java.util.ArrayList;
import java.util.List;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

/**
 * This class represents a polygon in three dimensional space. A polygon is a plane figure that is
 * described by a finite number of straight line segments connected to form a polygonal circuit.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Polygon implements Geometry {

	private List<Point> vertices;
	private Plane plane; // The plane which all the points must reside on.

	/**
	 * This constructor accepts a list of the vertices of the polygon.
	 *
	 * @param vertices A list of the vertices of the polygon, in order.
	 * @throws IllegalArgumentException if there are less than three vertices, any of the vertices
	 *                                  are not on the same plane as the rest, the vertices are out
	 *                                  of order, or the vertices form a non-convex polygon.
	 */
	public Polygon(List<Point> vertices) {
		int size = vertices.size();
		this.vertices = new ArrayList<>(size);

		double sum = 0.0; // sum of exterior angles
		for (int i = 0; i < size; ++i) { // loop through input vertices
			Vector v1 = vertices.get(i - 1 % size).vectorTo(vertices.get(i)); // prev to current
			Vector v2 = vertices.get(i).vectorTo(vertices.get(i + 1 % size)); // current to next
			double angle = v1.angle(v2);
			// if exterior angle is zero, point is on an existing edge and can be ignored
			if (Util.isZero(angle)) {
				continue;
			}
			vertices.add(vertices.get(i));
			sum += angle;
		}

		// If the sum of the exterior angles is greater than 2 Pi radians then it's not convex or
		// not all points are on the same plane.
		if (!Util.isZero(sum - 2 * Math.PI)) {
			throw new IllegalArgumentException(
					"Error: The polygon must be convex and all the vertices must be on a common plane.");
		}

		// Checks for at least three vertices
		if (this.vertices.size() < 3) {
			throw new IllegalArgumentException(
					"Error: A polygon must contain at least three vertices.");
		}
		// Construct the plane from the first three vertices (not in a straight line).
		this.plane = new Plane(this.vertices.get(0), this.vertices.get(1), this.vertices.get(2));
	}

	@Override
	public Vector normal(Point p) {
		return plane.normal(p);
	}

}
