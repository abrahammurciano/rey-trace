package geometries;

import org.junit.Assert;
import org.junit.Test;

import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;

/**
 * Tests the methods of the Geometries class.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class GeometriesTests {
	@Test
	public void testIntersect() {
		NormalizedVector i = new NormalizedVector(1, 0, 0);
		Ray ray = new Ray(Point.ORIGIN, i);

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

		// Some shapes intersect
		Sphere intersectingSphere = new Sphere(new Point(2, 0, 0), 1);
		Plane nonIntersectingPlane = new Plane(new Point(0, 0, 1), i);
		Triangle intersectingTriangle = new Triangle(new Point(4, 0, 1), new Point(4, 1, -1), new Point(4, -1, -1));
		Geometries geometries = new Geometries(intersectingSphere, nonIntersectingPlane, intersectingTriangle);
		Assert.assertEquals("Wrong number of intersections when some shapes intersect",
				geometries.intersect(ray).size(), 3);

		// Boundary values test

		// Empty geometries
		geometries = new Geometries();
		Assert.assertTrue("Empty geometries returned intersections.", geometries.intersect(ray).isEmpty());

		// No geometries intersect
		geometries = new Geometries(nonIntersectingPlane);
		Assert.assertTrue("Expected no intersections when no geometries intersect.",
				geometries.intersect(ray).isEmpty());

		// Only one shape intersects
		geometries = new Geometries(intersectingSphere, nonIntersectingPlane);
		Assert.assertEquals("Wrong number of intersections when some shapes intersect",
				geometries.intersect(ray).size(), 2);

		// All shapes intersect
		geometries = new Geometries(intersectingSphere, intersectingTriangle);
		Assert.assertEquals("Wrong number of intersections when all shapes intersect", geometries.intersect(ray).size(),
				3);
	}
}
