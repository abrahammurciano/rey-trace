package unit.geometries;

import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import geometries.Triangle;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;
import unit.geometries.util.PointExtractor;

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
		new Triangle(null, Point.ORIGIN, new Point(2, -1, 0), new Point(1, 1, 0));
		// Colinear points
		Point p2 = new Point(1, 1, 1);
		Point p3 = new Point(2, 2, 2);
		Assert.assertThrows("Colinear points should throw an exception.", IllegalArgumentException.class,
			() -> new Triangle(null, Point.ORIGIN, p2, p3));
	}

	@Test
	public void testIntersect() {
		Triangle triangle = new Triangle(null, Point.ORIGIN, new Point(1, 0, 0), new Point(1, 1, 1));

		// @formatter:off
		//  _____            _            _
		// | ____|__ _ _   _(_)_   ____ _| | ___ _ __   ___ ___
		// |  _| / _` | | | | \ \ / / _` | |/ _ \ '_ \ / __/ _ \
		// | |__| (_| | |_| | |\ V / (_| | |  __/ | | | (_|  __/
		// |_____\__, |\__,_|_| \_/ \__,_|_|\___|_| |_|\___\___|
		//          |_|
		//  ____            _   _ _   _
		// |  _ \ __ _ _ __| |_(_) |_(_) ___  _ __  ___
		// | |_) / _` | '__| __| | __| |/ _ \| '_ \/ __|
		// |  __/ (_| | |  | |_| | |_| | (_) | | | \__ \
		// |_|   \__,_|_|   \__|_|\__|_|\___/|_| |_|___/
		// @formatter:on

		// Intersection inside triangle
		Ray ray = new Ray(new Point(0.5, 0, 1), new NormalizedVector(0.5, 1, -1));
		Assert.assertEquals("Intersection expected but not found or wrong value.", Set.of(new Point(0.75, 0.5, 0.5)),
			PointExtractor.extractPoints(triangle.intersect(ray)));

		// Intersection outside triangle (on outside of only one edge)
		ray = new Ray(new Point(0.5, 0, 1), new NormalizedVector(2, 1, -1));
		Assert.assertTrue("No intersections expected for ray which intersects outside edge of triangle.",
			triangle.intersect(ray).isEmpty());

		// Intersection outside polygon (on outside of two edges)
		ray = new Ray(new Point(0.5, 0, 1), new NormalizedVector(1, -1, -2));
		Assert.assertTrue("No intersections expected for ray which intersects outside corner of triangle.",
			triangle.intersect(ray).isEmpty());

		// Ray parallel to triangle
		ray = new Ray(new Point(0.5, 0, 1), new NormalizedVector(0, -1, -1));
		Assert.assertTrue("No intersections expected for parallel ray.", triangle.intersect(ray).isEmpty());

		// Ray starts beyond triangle
		ray = new Ray(new Point(0.75, 1, 0), new NormalizedVector(0, 1, -1));
		Assert.assertTrue("No intersections expected for ray starting beyond triangle.",
			triangle.intersect(ray).isEmpty());

		// @formatter:off
		//  ____                        _
		// | __ )  ___  _   _ _ __   __| | __ _ _ __ _   _
		// |  _ \ / _ \| | | | '_ \ / _` |/ _` | '__| | | |
		// | |_) | (_) | |_| | | | | (_| | (_| | |  | |_| |
		// |____/ \___/ \__,_|_| |_|\__,_|\__,_|_|   \__, |
		//  _____         _                          |___/
		// |_   _|__  ___| |_ ___
		//   | |/ _ \/ __| __/ __|
		//   | |  __/\__ \ |_\__ \
		//   |_|\___||___/\__|___/
		// @formatter:on

		// Ray intersects triangle boundary
		ray = new Ray(new Point(0.5, 0, 1), new NormalizedVector(0, 1, -1));
		Assert.assertTrue("Intersection on edge expected to be ignored.", triangle.intersect(ray).isEmpty());

		// Ray intersects triangle corner
		ray = new Ray(new Point(0.5, 0, 1), new NormalizedVector(0.5, 1, 0));
		Assert.assertTrue("Intersection on corner expected to be ignored.", triangle.intersect(ray).isEmpty());

		// Ray starts on boundary
		ray = new Ray(new Point(0.5, 0, 0), new NormalizedVector(0.5, 1, 0));
		Assert.assertTrue("Expected no intersection from ray starting on boundary.", triangle.intersect(ray).isEmpty());

		// Ray starts on corner
		ray = new Ray(Point.ORIGIN, new NormalizedVector(0.5, 1, 0));
		Assert.assertTrue("Expected no intersection from ray starting on corner.", triangle.intersect(ray).isEmpty());
	}
}
