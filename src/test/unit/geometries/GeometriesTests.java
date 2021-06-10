package unit.geometries;

import org.junit.Assert;
import org.junit.Test;
import geometries.GeometryList;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
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
		NormalizedVector i = NormalizedVector.I;
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
		Sphere intersectingSphere = new Sphere(null, new Point(2, 0, 0), 1);
		Plane nonIntersectingPlane = new Plane(null, new Point(0, 0, 1), i);
		Triangle intersectingTriangle =
			new Triangle(null, new Point(4, 0, 1), new Point(4, 1, -1), new Point(4, -1, -1));
		GeometryList geometries = new GeometryList(intersectingSphere, nonIntersectingPlane, intersectingTriangle);
		Assert.assertEquals("Wrong number of intersections when some shapes intersect", 3,
			geometries.intersect(ray).size());

		// Boundary values test

		// Empty geometries
		geometries = new GeometryList();
		Assert.assertTrue("Empty geometries returned intersections.", geometries.intersect(ray).isEmpty());

		// No geometries intersect
		geometries = new GeometryList(nonIntersectingPlane);
		Assert.assertTrue("Expected no intersections when no geometries intersect.",
			geometries.intersect(ray).isEmpty());

		// Only one shape intersects
		geometries = new GeometryList(intersectingSphere, nonIntersectingPlane);
		Assert.assertEquals("Wrong number of intersections when some shapes intersect", 2,
			geometries.intersect(ray).size());

		// All shapes intersect
		geometries = new GeometryList(intersectingSphere, intersectingTriangle);
		Assert.assertEquals("Wrong number of intersections when all shapes intersect", 3,
			geometries.intersect(ray).size());
	}
}
