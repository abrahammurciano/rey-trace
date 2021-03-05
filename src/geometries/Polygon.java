package geometries;

import java.util.ArrayList;
import java.util.List;
import primitives.Point;
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
	 * @param vertices A list of the vertices of the polygon.
	 * @throws IllegalArgumentException if there are less than three vertices, any of the vertices
	 *                                  are not on the same plane as the rest, the vertices are out
	 *                                  of order, or the vertices form a non-convex polygon.
	 */
	public Polygon(List<Point> vertices) {
		// Checks for at least three vertices
		if (vertices == null || vertices.size() < 3) {
			throw new IllegalArgumentException(
					"Error: A polygon must contain at least three vertices.");
		}

		// Construct the plane from the first three vertices
		this.plane = new Plane(vertices.get(0), vertices.get(1), vertices.get(2));

		// Check that all the vertices are on the plane.
		for (Point vertex : vertices) {
			if (!plane.contains(vertex)) {
				throw new IllegalArgumentException(
						"Error: All the vertices must be on a common plane.");
			}
		}

		// TODO: Check that the vertices form a convex polygon and are in order (i suspect they can
		// be checked with one check for both conditions).

		this.vertices = new ArrayList<>(vertices);
	}

	@Override
	public Vector normal(Point p) {
		return plane.normal(p);
	}

}
