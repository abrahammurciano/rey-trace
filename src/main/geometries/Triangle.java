package geometries;

import primitives.Material;
import primitives.Point;

/**
 * This class represents a triangle in three dimensional space. It is a polygon with specifically three vertices.
 */
public class Triangle extends Polygon {

	/**
	 * Constructs a triangle from three {@link Point}s.
	 *
	 * @param p1 A vertex of the triangle.
	 * @param p2 A vertex of the triangle.
	 * @param p3 A vertex of the triangle.
	 * @throws IllegalArgumentException if the given {@link Point}s are colinear.
	 */
	public Triangle(Material material, Point p1, Point p2, Point p3) {
		super(material, p1, p2, p3);
	}

}
