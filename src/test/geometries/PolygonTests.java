package geometries;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import util.NormalCompare;

/**
 * Tests the methods of the Polygon class.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class PolygonTests {

	/**
	 * Test Polygon.Polygon
	 */
	@Test
	public void testConstructor() {
		// ===========================================================
		// Tests that constructor takes at least 3 significant points.
		// ===========================================================

		// Equivalence partition tests

		// More than three points should not throw an exception.
		new Polygon(new Point(0, 0, 0), new Point(2, -1, 0), new Point(1, 1, 0),
			new Point(0.5, 1, 0));
		// More than three significant points should not throw an exception.
		new Polygon(new Point(0, 0, 0), new Point(1, -0.5, 0), new Point(2, -1, 0),
			new Point(1, 1, 0), new Point(0.5, 1, 0));
		// Less than three vertices should throw an exception.
		Assert.assertThrows("Too few points should throw an exception.",
			IllegalArgumentException.class,
			() -> new Polygon(new Point(0, 0, 0), new Point(1, 1, 1)));
		// Less than three significant vertices should throw an exception.
		Assert.assertThrows("Too few significant points should throw an exception.",
			IllegalArgumentException.class, () -> new Polygon(new Point(0, 0, 0),
				new Point(1, 1, 1), new Point(2, 2, 2), new Point(3, 3, 3)));

		// Boundary values test

		// Exactly three points should not throw an exception.
		new Polygon(new Point(0, 0, 0), new Point(1, 1, 0), new Point(0.5, 1, 0));
		// Exactly three significant points should not throw an exception.
		new Polygon(new Point(0, 0, 0), new Point(1, -0.5, 0), new Point(2, -1, 0),
			new Point(1, 1, 0));

		// ===================================================
		// Test that constructor accepts only convex polygons.
		// ===================================================

		// Equivalence partition tests (there are no boundary values)

		// Convex polygon
		new Polygon(new Point(0, 0, 1), new Point(2, 2, 2), new Point(1, 1, 0),
			new Point(-1, -1, -1));

		// Concave polygons
		Assert.assertThrows("Concave polygons should throw an exception.",
			IllegalArgumentException.class, () -> new Polygon(new Point(0, 0, 0),
				new Point(1, 2, 0), new Point(2, 0, 0), new Point(1, 1, 0)));

		// Complex polygon - Type 1
		Assert.assertThrows("Complex polygon should throw an exception.",
			IllegalArgumentException.class, () -> new Polygon(new Point(0, 0, 0),
				new Point(1, 2, 0), new Point(2, 0, 0), new Point(0, 2, 0)));
		// Complex polygon - Type 2
		Assert.assertThrows("Complex star polygon should throw an exception.",
			IllegalArgumentException.class, () -> new Polygon(new Point(0, 0, 0),
				new Point(1, 2, 0), new Point(2, 0, 0), new Point(0, 2, 0), new Point(2, 1, 0)));

		// Non-planar polygon
		Assert.assertThrows("Non-planar polygon should throw an exception.",
			IllegalArgumentException.class, () -> new Polygon(new Point(0, 0, 0),
				new Point(1, 2, 0), new Point(0, 2, 0), new Point(1, 0, 1)));

		// =====================================================
		// Test that constructor doesn't accept repeated points.
		// =====================================================

		// Equivalence partition tests

		// Repeated points in the middle
		Assert.assertThrows("Repeated points should throw an exception.",
			IllegalArgumentException.class, () -> new Polygon(new Point(0, 0, 1),
				new Point(2, 2, 2), new Point(2, 2, 2), new Point(1, 1, 0), new Point(-1, -1, -1)));
		// Repeated end points
		Assert.assertThrows("Repeated end points should throw an exception.",
			IllegalArgumentException.class, () -> new Polygon(new Point(0, 0, 0),
				new Point(0, 1, 0), new Point(1, 1, 0), new Point(1, 0, 0), new Point(0, 0, 0)));
	}

	/**
	 * Test Polygon.normal
	 */
	@Test
	public void testNormal() {
		Polygon polygon = new Polygon(new Point(0, 0, 1), new Point(2, 2, 2), new Point(1, 1, 0),
			new Point(-1, -1, -1));
		NormalizedVector normal = polygon.normal(new Point(0, 0, 0));
		NormalizedVector expected_normal = new NormalizedVector(1, -1, 0);
		Assert.assertTrue("Wrong normal for Polygon.", NormalCompare.eq(normal, expected_normal));
	}

	@Test
	public void testIntersect() {
		Polygon polygon = new Polygon(new Point(0, 0, 0), new Point(1, 0, 0), new Point(1, 1, 1),
			new Point(0, 1, 1));

		// Equivalence partition tests

		// Intersection inside polygon
		Ray ray = new Ray(new Point(0.5, 0, 1), new Vector(0, 1, -1));
		Assert.assertEquals("Intersection expected but not found or wrong value.",
			polygon.intersect(ray), List.of(new Point(0.5, 0.5, 0.5)));

		// Intersection outside polygon (on outside of only one edge)
		ray = new Ray(new Point(0.5, 0, 1), new Vector(2, 1, -1));
		Assert.assertTrue(
			"No intersections expected for ray which intersects outside edge of polygon.",
			polygon.intersect(ray).isEmpty());

		// Intersection outside polygon (on outside of two edges)
		ray = new Ray(new Point(0.5, 0, 1), new Vector(1, -1, -2));
		Assert.assertTrue(
			"No intersections expected for ray which intersects outside corner of polygon.",
			polygon.intersect(ray).isEmpty());

		// Ray parallel to polygon
		ray = new Ray(new Point(0.5, 0, 1), new Vector(0, -1, -1));
		Assert.assertTrue("No intersections expected for parallel ray.",
			polygon.intersect(ray).isEmpty());

		// Ray starts beyond polygon
		ray = new Ray(new Point(0.5, 1, 0), new Vector(0, 1, -1));
		Assert.assertTrue("No intersections expected for ray starting beyond polygon.",
			polygon.intersect(ray).isEmpty());

		// Boundary values test

		// Ray intersects polygon boundary
		ray = new Ray(new Point(0.5, 0, 1), new Vector(0, 1, 0));
		Assert.assertTrue("Intersection on edge expected to be ignored.",
			polygon.intersect(ray).isEmpty());

		// Ray intersects polygon corner
		ray = new Ray(new Point(0.5, 0, 1), new Vector(0.5, 1, 0));
		Assert.assertTrue("Intersection on corner expected to be ignored.",
			polygon.intersect(ray).isEmpty());

		// Ray starts on boundary
		ray = new Ray(new Point(0.5, 0, 0), new Vector(0.5, 1, 0));
		Assert.assertTrue("Expected no intersection from ray starting on boundary.",
			polygon.intersect(ray).isEmpty());

		// Ray starts on corner
		ray = new Ray(new Point(0, 0, 0), new Vector(0.5, 1, 0));
		Assert.assertTrue("Expected no intersection from ray starting on corner.",
			polygon.intersect(ray).isEmpty());
	}
}
