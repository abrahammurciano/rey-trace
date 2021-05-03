package unit.geometries;

import java.util.HashSet;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import geometries.Sphere;
import math.compare.NormalCompare;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Tests the functions of the {@link Vector} class
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class SphereTests {
	public final Sphere s = new Sphere(new Point(1, 2, 3), 5);

	@Test
	public void testNormal() {
		NormalizedVector calc, actual;

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

		// Test at direction (2,1,1)
		calc = s.normal(new Point(5.08248290463863016366, 4.04124145231931508183, 5.04124145231931508183));
		actual = new NormalizedVector(2, 1, 1);
		Assert.assertTrue("Normalized vectors to the sphere should be equal.", NormalCompare.eq(calc, actual));

		// Test at direction (1,2,3)
		calc = s.normal(new Point(2.336306209562122, 4.672612419124244, 7.008918628686366));
		actual = new NormalizedVector(1, 2, 3);
		Assert.assertTrue("Normalized vectors to the sphere should be equal in direction (1,2,3)",
			NormalCompare.eq(calc, actual));
	}

	@Test
	public void testIntersect() {
		Sphere sphere = new Sphere(new Point(1, 0, 0), 1);

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

		// Ray's line is outside the sphere
		Ray ray = new Ray(new Point(-1, 0, 0), new NormalizedVector(1, 1, 0));
		Assert.assertTrue("No intersections expected when ray's line is outside sphere",
			sphere.intersect(ray).isEmpty());

		// Ray starts before and crosses the sphere
		ray = new Ray(new Point(-1, 0, 0), new NormalizedVector(3, 1, 0));
		Point p1 = new Point(0.06515307716504659, 0.35505102572168223, 0);
		Point p2 = new Point(1.5348469228349528, 0.8449489742783177, 0);
		Assert.assertEquals("Wrong result for ray crossing sphere.", new HashSet<>(sphere.intersect(ray)),
			new HashSet<>(List.of(p1, p2)));

		// Ray starts inside the sphere (1 intersection)
		ray = new Ray(new Point(1.5, 0.5, 0.5), new NormalizedVector(0, -1, -1));
		p1 = new Point(1.5, -0.612372435695794, -0.612372435695794);
		Assert.assertEquals("Wrong result for ray starting inside sphere.", sphere.intersect(ray), List.of(p1));

		// Ray starts after the sphere (no intersections)
		ray = new Ray(new Point(1.5, -1, -1), new NormalizedVector(0, -1, -1));
		Assert.assertTrue("Expected no intersections for ray going away from sphere.", sphere.intersect(ray).isEmpty());

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

		// Ray starts at surface and goes inwards (not through center) (one
		// intersection)
		ray = new Ray(Point.ORIGIN, new NormalizedVector(1, 1, 0));
		p1 = new Point(1, 1, 0);
		Assert.assertEquals("Wrong result for ray staring on boundary going in", sphere.intersect(ray), List.of(p1));

		// Ray starts at surface and goes outside (not directly away from center) (no
		// intersections)
		ray = new Ray(Point.ORIGIN, new NormalizedVector(-1, -2, -3));
		Assert.assertTrue("Expected no intersection for ray starting on surface heading out.",
			sphere.intersect(ray).isEmpty());

		// Ray starts before the sphere and goes through center (2 intersections)
		ray = new Ray(new Point(-1, 0, 0), NormalizedVector.I);
		p1 = Point.ORIGIN;
		p2 = new Point(2, 0, 0);
		Assert.assertEquals("Wrong result for ray going through center of sphere.",
			new HashSet<>(sphere.intersect(ray)), new HashSet<>(List.of(p1, p2)));

		// Ray starts at surface and goes inside throuch center (one intersection)
		ray = new Ray(Point.ORIGIN, NormalizedVector.I);
		p1 = new Point(2, 0, 0);
		Assert.assertEquals("Wrong result for ray starting at surface going through center of sphere.",
			sphere.intersect(ray), List.of(p1));

		// Ray starts inside and passes through center (one intersection)
		ray = new Ray(new Point(0.5, 0, 0), NormalizedVector.I);
		p1 = new Point(2, 0, 0);
		Assert.assertEquals("Wrong result for ray starting inside and going through center of sphere.",
			sphere.intersect(ray), List.of(p1));

		// Ray starts at the center (one intersection)
		ray = new Ray(new Point(1, 0, 0), NormalizedVector.I);
		p1 = new Point(2, 0, 0);
		Assert.assertEquals("Wrong result for ray starting at center of sphere.", sphere.intersect(ray), List.of(p1));

		// Ray starts at sphere and goes outside directly away from center (no
		// intersections)
		ray = new Ray(new Point(2, 0, 0), NormalizedVector.I);
		Assert.assertTrue(
			"Expected no intersections for ray starting on surface and going directly away from center of sphere.",
			sphere.intersect(ray).isEmpty());

		// Ray starts after sphere and heads directly away from center (no
		// intersections)
		ray = new Ray(new Point(3, 0, 0), NormalizedVector.I);
		Assert.assertTrue(
			"Expected no intersections for ray starting after sphere and going directly away from center of sphere.",
			sphere.intersect(ray).isEmpty());

		// Tangent ray starts before the intersection (no intersections)
		ray = new Ray(new Point(0, 0, 1), NormalizedVector.I);
		Assert.assertTrue("Expected no intersections for tangent.", sphere.intersect(ray).isEmpty());

		// Tangent ray starts at the intersection (no intersections)
		ray = new Ray(new Point(1, 0, 1), NormalizedVector.I);
		Assert.assertTrue("Expected no intersections for tangent starting at the intersection.",
			sphere.intersect(ray).isEmpty());

		// Tangent ray starts after the intersection (no intersections)
		ray = new Ray(new Point(2, 0, 1), NormalizedVector.I);
		Assert.assertTrue("Expected no intersections for tangent starting after intersection.",
			sphere.intersect(ray).isEmpty());

		// Ray's line is outside, ray is orthogonal to ray start to sphere's center line
		ray = new Ray(new Point(1, 2, 2), NormalizedVector.I);
		Assert.assertTrue("Expected no intersections for ray perpendicular to ray.source.vectorTo(sphere.center)",
			sphere.intersect(ray).isEmpty());
	}
}
