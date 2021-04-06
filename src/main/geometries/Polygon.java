package geometries;

import java.util.ArrayList;
import java.util.List;
import primitives.Point;
import util.DoubleCompare;
import primitives.Vector;
import primitives.ZeroVectorException;
import primitives.NormalizedVector;

/**
 * This class represents a polygon in three dimensional space. A polygon is a plane figure that is described by a finite
 * number of straight line segments connected to form a polygonal circuit.
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
	 * @throws IllegalArgumentException if there are less than three significant vertices, any of the vertices are not on
	 *                                  the same plane as the rest, the vertices are out of order and thus form a non-convex
	 *                                  polygon, consecutive vertices are repeated, or the last point is equal to the first
	 *                                  point.
	 */
	public Polygon(Point... vertices) {
		int size = vertices.length;
		this.vertices = new ArrayList<>(size);

		double sum = 0.0; // sum of exterior angles
		for (int i = 0; i < size; ++i) { // loop through input vertices
			try {
				Vector v1 = vertices[mod(i - 1, size)].vectorTo(vertices[i]); // prev to current
				Vector v2 = vertices[i].vectorTo(vertices[(i + 1) % size]); // current to next
				double angle = v1.angle(v2);
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

		// If the sum of the exterior angles is greater than 2 Pi radians then it's not convex or
		// not all points are on the same plane.
		if (DoubleCompare.neq(sum, 2 * Math.PI)) {
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

	/**
	 * Calculates a % b but for negative inputs will still give a result between 0 and b (similar to how Python implements
	 * mod).
	 *
	 * @param a The dividend
	 * @param b The divisor
	 * @return The remainder (between 0 and b)
	 */
	private int mod(int a, int b) {
		return (((a % b) + b) % b);
	}

}
