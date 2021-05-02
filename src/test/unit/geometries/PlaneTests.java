package unit.geometries;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import geometries.Plane;
import math.compare.NormalCompare;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;

/**
 * Tests the methods of the Plane class.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class PlaneTests {

	/**
	 * Tests that constructor rejects colinear points.
	 */
	@Test
	public void testColinearPoints() {
		// Non colinear points
		new Plane(new Point(0, 0, 0), new Point(2, -1, 0), new Point(1, 1, 0));
		// Colinear points
		Assert.assertThrows("Colinear points should throw an exception.", IllegalArgumentException.class,
			() -> new Plane(new Point(0, 0, 0), new Point(1, 1, 1), new Point(2, 2, 2)));
	}

	/**
	 * Test Plane.contains
	 */
	@Test
	public void contains() {
		Plane plane = new Plane(new Point(0, 0, 0), new NormalizedVector(0, 0, 1));

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

		// Point on plane
		Assert.assertTrue("Plane claims to not contain a point on its surface.", plane.contains(new Point(3, 3, 0)));
		// Point not on plane
		Assert.assertFalse("Plane claims to contain a point not on its surface.", plane.contains(new Point(3, 3, 1)));

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

		// Plane's defining point
		Assert.assertTrue("Plane claims to not contain its defining point.", plane.contains(new Point(0, 0, 0)));
	}

	/**
	 * Test Plane.normal
	 */
	@Test
	public void normal() {
		Plane plane = new Plane(new Point(0, 0, 0), new Point(2, -1, 0), new Point(1, 1, 0));
		NormalizedVector normal = plane.normal(new Point(0, 0, 0));
		NormalizedVector expected_normal = new NormalizedVector(0, 0, 1);
		Assert.assertTrue("Wrong normal for Polygon.", NormalCompare.eq(normal, expected_normal));
	}

	/**
	 * Test Plane.intersect
	 */
	@Test
	public void testIntersect() {
		Plane plane = new Plane(new Point(0, 0, 0), new Point(2, -1, 0), new Point(1, 1, 0));
		Ray ray;

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

		// Does intersect
		ray = new Ray(new Point(1, 1, 1), new NormalizedVector(1, 1, -1));
		Assert.assertEquals("Expected intersection for intersecting ray.", plane.intersect(ray),
			List.of(new Point(2, 2, 0)));

		// Does not intersect (not parallel)
		ray = new Ray(new Point(1, 1, -1), new NormalizedVector(1, 1, -1));
		Assert.assertTrue("Expected no intersection for non-intersecting ray", plane.intersect(ray).isEmpty());

		// Does not intersect (parallel)
		ray = new Ray(new Point(1, 1, -1), new NormalizedVector(1, 1, 0));
		Assert.assertTrue("No plane intersection expected for parallel ray.", plane.intersect(ray).isEmpty());

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

		// Ray is completely in plane
		ray = new Ray(new Point(1, 1, 0), new NormalizedVector(1, 1, 0));
		Assert.assertTrue("No plane intersection expected for embedded ray.", plane.intersect(ray).isEmpty());

		// Ray starts on plane but not parallel
		ray = new Ray(new Point(1, 1, 0), new NormalizedVector(1, 1, 1));
		Assert.assertTrue("No plane intersection expected for ray starting on plane.", plane.intersect(ray).isEmpty());

		// Ray starts on plane's internal point
		ray = new Ray(new Point(0, 0, 0), new NormalizedVector(1, 1, 1));
		Assert.assertTrue("No plane intersection expected for ray starting on plane's internal point.",
			plane.intersect(ray).isEmpty());
	}
}