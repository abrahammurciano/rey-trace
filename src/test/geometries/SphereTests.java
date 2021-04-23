package geometries;

import org.junit.Assert;
import org.junit.Test;
import primitives.NormalizedVector;
import primitives.Point;
import util.NormalCompare;

/**
 * Tests the functions of the vector class
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class SphereTests {
	public final Sphere s = new Sphere(new Point(1, 2, 3), 5);

	@Test
	public void normal() {
		NormalizedVector calc, actual;

		// Test at direction (2,1,1)
		calc = s.normal(
			new Point(5.08248290463863016366, 4.04124145231931508183, 5.04124145231931508183));
		actual = new NormalizedVector(2, 1, 1);
		Assert.assertTrue("Normalized vectors to the sphere should be equal.",
			NormalCompare.eq(calc, actual));

		// Test at direction (1,2,3)
		calc = s.normal(new Point(2.336306209562122, 4.672612419124244, 7.008918628686366));
		actual = new NormalizedVector(1, 2, 3);
		Assert.assertTrue("Normalized vectors to the sphere should be equal in direction (1,2,3)",
			NormalCompare.eq(calc, actual));
	}
}
