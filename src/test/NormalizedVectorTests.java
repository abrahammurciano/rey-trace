package test;

import org.junit.Assert;
import org.junit.Test;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Util;
import primitives.Vector;
import primitives.ZeroVectorException;

/**
 * Tests the functions of the vector class
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class NormalizedVectorTests {
	private final NormalizedVector base = new NormalizedVector(1, 2, 3); // (0.2672612419124244, 0.5345224838248488, 0.8017837257372732)

	/**
	 * Test Vector.subtract
	 */
	@Test
	public void subtract() {
		// Equivalence partition tests
		Assert.assertEquals("Obtuse vectors gave wrong result.",
				base.subtract(new NormalizedVector(-3, -2, -4)),
				new Vector(0.82434725644358, 0.9059131601789525, 1.5445650784454807));
		Assert.assertEquals("Acute vectors gave wrong result.",
				base.subtract(new NormalizedVector(3, 2, 4)),
				new Vector(-0.2898247726187312, 0.16313180747074507, 0.05900237302906575));

		// Boundary values test
		// Parallel vectors
		Assert.assertEquals("Antiparallel vectors gave wrong result.",
				base.subtract(new NormalizedVector(-1, -2, -3)),
				new Vector(2 * 0.2672612419124244, 2 * 0.5345224838248488, 2 * 0.8017837257372732));
		Assert.assertThrows("A vector minus itself should throw a ZeroVectorException.",
				ZeroVectorException.class, () -> base.subtract(new NormalizedVector(1, 2, 3)));
	}

	/**
	 * Test Vector.reversed
	 */
	@Test
	public void reversed() {
		Assert.assertEquals("Wrong reversed vector.", base.reversed(),
				new NormalizedVector(-1, -2, -3));
	}

	/**
	 * Test Vector.length
	 */
	@Test
	public void length() {
		Assert.assertTrue("Wrong normalized vector length.", Util.equals(base.length(), 1));
	}
}
