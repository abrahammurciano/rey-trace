package geometries;

import java.util.HashSet;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Tests the methods of the Geometries class.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class GeometriesTests {
	@Test
	public void testIntersect() {
		Vector i = new Vector(1, 0, 0);
		Ray ray = new Ray(Point.ORIGIN, i);

		// Equivalence partition tests

		// Some shapes intersect
		Geometries geometries =
			new Geometries(new Sphere(new Point(2, 0, 0), 1), new Plane(new Point(0, 0, 1), i),
				new Triangle(new Point(4, 0, 1), new Point(4, 1, -1), new Point(4, -1, -1)));
		Assert.assertEquals("Wrong result when some shapes intersect",
			new HashSet<>(geometries.intersect(ray)),
			new HashSet<>(List.of(new Point(1, 0, 0), new Point(3, 0, 0), new Point(4, 0, 0))));

		// Boundary values test

		// Test intersections with empty geometries
		geometries = new Geometries();
		Assert.assertTrue("Empty geometries returned intersections.",
			geometries.intersect(ray).isEmpty());
	}
}
