package geometries;

import java.util.Collections;
import java.util.HashSet;
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
public class CylinderTests {
	public final Ray ray = new Ray(new Point(3, 2, 1), new Vector(6, 5, 4));
	public final Cylinder base = new Cylinder(ray, 5, 10);

	@Test
	public void normal() {
		NormalizedVector calc, actual1, actual2;

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

		// test on round side
		calc = base.normal(new Point(30.973500981126143, 23, 13.639748528310783));
		actual1 = new NormalizedVector(2, 0, -3);
		Assert.assertTrue("Normalized vectors should be equal", NormalCompare.eq(calc, actual1));

		// test on flat side
		calc = base.normal(new Point(7.79639313525896, 11.780511727620528, 3.517181606066203));
		actual1 = new NormalizedVector(6, 5, 4);
		Assert.assertTrue("Normalized vectors should be equal", NormalCompare.eq(calc, actual1));

		// base side
		String notEqErrMsg = "Vectors should be equal";
		calc = base.normal(new Point(5.182178902359924, 6.364357804719848, -7.728715609439696));
		actual1 = new NormalizedVector(6, 5, 4);
		actual2 = new NormalizedVector(1, -2, 1);
		Assert.assertTrue(notEqErrMsg,
			NormalCompare.eq(calc, actual1) || NormalCompare.eq(calc, actual2));

		// center of base
		calc = base.normal(new Point(3, 2, 1));
		actual1 = new NormalizedVector(6, 5, 4);
		Assert.assertTrue(notEqErrMsg, NormalCompare.eq(calc, actual1));

		// opposite center
		calc = base.normal(new Point(9.837634587578275, 7.698028822981897, 5.558423058385518));
		actual1 = new NormalizedVector(6, 5, 4);
		Assert.assertTrue(notEqErrMsg, NormalCompare.eq(calc, actual1));

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

		// test on corner
		calc = base.normal(new Point(13.920117492216907, -0.46693698629536406, 9.64090596302415));
		actual1 = new NormalizedVector(6, 5, 4);
		actual2 = new NormalizedVector(1, -2, 1);
		// in practice always ends up being normal to the flat side
		Assert.assertTrue(notEqErrMsg,
			NormalCompare.eq(calc, actual1) || NormalCompare.eq(calc, actual2));
	}

	@Test
	public void testIntersect() {
		Cylinder cyl = new Cylinder(new Ray(new Point(1, 0, 0), new Vector(1, 1, 1)), Math.sqrt(2),
			5 * Math.sqrt(3));
		Ray ray;

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

		//start in middle and come out side
		ray = new Ray(new Point(1, 1, 1), new Vector(-1, 2, 0));
		Assert.assertEquals("Passes through bottom and comes out top",
			new HashSet<>(List.of(new Point(0.6796227589829593, 1.6407544820340814, 1.0))),
			new HashSet<>(cyl.intersect(ray)));

		// pass through bottom and side
		ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 1));
		Assert.assertEquals("Passes through bottom and comes out side",
			new HashSet<>(List.of(new Point(0.5, 0, 0.5), new Point(2, 0, 2))),
			new HashSet<>(cyl.intersect(ray)));

		// pass through bottom and top
		ray = new Ray(new Point(0, 0, 0), new Vector(3, 2, 2));
		Assert.assertEquals("Passes through bottom and comes out top",
			new HashSet<>(
				List.of(new Point(0.4285714285714286, 0.2857142857142857, 0.2857142857142857),
					new Point(6.857142857142858, 4.571428571428571, 4.571428571428571))),
			new HashSet<>(cyl.intersect(ray)));

		// starts in middle and passes through bottom
		ray = new Ray(new Point(2, 2, 2), new Vector(-1, -1, -1));
		Assert.assertEquals("Passes through only top",
			new HashSet<>(List.of(new Point(1d / 3, 1d / 3, 1d / 3))),
			new HashSet<>(cyl.intersect(ray)));

		// starts in middle and passes through top
		ray = new Ray(new Point(2, 2, 2), new Vector(1, 1, 1));
		Assert.assertEquals("Passes through only top",
			new HashSet<>(
				List.of(new Point(5.333333333333334, 5.333333333333334, 5.333333333333334))),
			new HashSet<>(cyl.intersect(ray)));

		//outside cylinder completely
		ray = new Ray(new Point(-1, -1, -1), new Vector(-3, 7, 4));
		Assert.assertEquals("Passes through only top", Collections.emptyList(), cyl.intersect(ray));

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
		//

		// follows axis
		ray = new Ray(new Point(0, -1, -1), new Vector(1, 1, 1));
		Assert.assertEquals("Follows axis",
			new HashSet<>(List.of(new Point(1, 0, 0), new Point(6, 5, 5))),
			new HashSet<>(cyl.intersect(ray)));

		// follows axis and starts on bottom plane
		ray = new Ray(new Point(1, 0, 0), new Vector(1, 1, 1));
		Assert.assertEquals("Follows axis", new HashSet<>(List.of(new Point(6, 5, 5))),
			new HashSet<>(cyl.intersect(ray)));

		// passes though bottom and CORNER(!) of cylinder
		ray = new Ray(new Point(0, 0, 0), new Vector(3, 2, 3));
		Assert.assertEquals("Passes through bottom and comes out top",
			new HashSet<>(List.of(new Point(0.375, 0.25, 0.375), new Point(6, 4, 6))),
			new HashSet<>(cyl.intersect(ray)));

		//hits corner from outside
		ray = new Ray(new Point(3, 4, 6), new Vector(1, 0, 0));
		Assert.assertEquals("Passes through bottom and comes out top", List.of(new Point(6, 4, 6)),
			cyl.intersect(ray));

	}
}
