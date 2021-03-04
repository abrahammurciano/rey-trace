package geometries;

import java.util.Arrays;
import primitives.Point;

public class Triangle extends Polygon {

	/**
	 * Constructs a triangle from three {@link Point}s.
	 *
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(Arrays.asList(p1, p2, p3));
	}

}
