package geometries;

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
		// Test intersections with empty geometries
		Geometries geometries = new Geometries();
		Ray ray = new Ray(Point.ORIGIN, new Vector(1, 0, 0));
		Assert.assertTrue("Empty geometries returned intersections.",
			geometries.intersect(ray).isEmpty());
	}
}
