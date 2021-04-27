package geometries;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Tests the methods of the Triangle class.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class TriangleTests {

	/**
	 * Tests that constructor rejects colinear points.
	 */
	@Test
	public void testColinearPoints() {
		// Non colinear points
		new Triangle(new Point(0, 0, 0), new Point(2, -1, 0), new Point(1, 1, 0));
		// Colinear points
		Assert.assertThrows("Colinear points should throw an exception.",
			IllegalArgumentException.class,
			() -> new Triangle(new Point(0, 0, 0), new Point(1, 1, 1), new Point(2, 2, 2)));
	}

	@Test
	public void testIntersect() {
		Triangle triangle =
			new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(1, 1, 1));

		// Equivalence partition tests

		// Intersection inside triangle
		Ray ray = new Ray(new Point(0.5, 0, 1), new Vector(0.5, 1, -1));
		Assert.assertEquals("Intersection expected but not found or wrong value.",
			triangle.intersect(ray), List.of(new Point(0.75, 0.5, 0.5)));

		// Intersection outside triangle (on outside of only one edge)
		ray = new Ray(new Point(0.5, 0, 1), new Vector(2, 1, -1));
		Assert.assertTrue(
			"No intersections expected for ray which intersects outside edge of triangle.",
			triangle.intersect(ray).isEmpty());

		// Intersection outside polygon (on outside of two edges)
		ray = new Ray(new Point(0.5, 0, 1), new Vector(1, -1, -2));
		Assert.assertTrue(
			"No intersections expected for ray which intersects outside corner of triangle.",
			triangle.intersect(ray).isEmpty());

		// Ray parallel to triangle
		ray = new Ray(new Point(0.5, 0, 1), new Vector(0, -1, -1));
		Assert.assertTrue("No intersections expected for parallel ray.",
			triangle.intersect(ray).isEmpty());

		// Ray starts beyond triangle
		ray = new Ray(new Point(0.75, 1, 0), new Vector(0, 1, -1));
		Assert.assertTrue("No intersections expected for ray starting beyond triangle.",
			triangle.intersect(ray).isEmpty());

		// Boundary values test

		// Ray intersects triangle boundary
		ray = new Ray(new Point(0.5, 0, 1), new Vector(0, 1, -1));
		Assert.assertTrue("Intersection on edge expected to be ignored.",
			triangle.intersect(ray).isEmpty());

		// Ray intersects triangle corner
		ray = new Ray(new Point(0.5, 0, 1), new Vector(0.5, 1, 0));
		Assert.assertTrue("Intersection on corner expected to be ignored.",
			triangle.intersect(ray).isEmpty());

		// Ray starts on boundary
		ray = new Ray(new Point(0.5, 0, 0), new Vector(0.5, 1, 0));
		Assert.assertTrue("Expected no intersection from ray starting on boundary.",
			triangle.intersect(ray).isEmpty());

		// Ray starts on corner
		ray = new Ray(new Point(0, 0, 0), new Vector(0.5, 1, 0));
		Assert.assertTrue("Expected no intersection from ray starting on corner.",
			triangle.intersect(ray).isEmpty());
	}
}
