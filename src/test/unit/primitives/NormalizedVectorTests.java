package unit.primitives;

import org.junit.Assert;
import org.junit.Test;

import math.compare.DoubleCompare;
import primitives.NormalizedVector;
import primitives.NonZeroVector;
import primitives.ZeroVectorException;

/**
 * Tests the functions of the vector class
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class NormalizedVectorTests {
	private final NormalizedVector base = new NormalizedVector(1, 2, 3); // (0.2672612419124244, 0.5345224838248488,
																			// 0.8017837257372732)

	/**
	 * Test Vector.subtract
	 */
	@Test
	public void subtract() {
		// Equivalence partition tests
		Assert.assertEquals("Obtuse vectors gave wrong result.", base.subtract(new NormalizedVector(-3, -2, -4)),
			new NonZeroVector(0.82434725644358, 0.9059131601789525, 1.5445650784454807));
		Assert.assertEquals("Acute vectors gave wrong result.", base.subtract(new NormalizedVector(3, 2, 4)),
			new NonZeroVector(-0.2898247726187312, 0.16313180747074507, 0.05900237302906575));

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

		// Parallel vectors
		Assert.assertEquals("Antiparallel vectors gave wrong result.", base.subtract(new NormalizedVector(-1, -2, -3)),
			new NonZeroVector(2 * 0.2672612419124244, 2 * 0.5345224838248488, 2 * 0.8017837257372732));
		NormalizedVector v = new NormalizedVector(1, 2, 3);
		Assert.assertThrows("A vector minus itself should throw a ZeroVectorException.", ZeroVectorException.class,
			() -> base.subtract(v));
	}

	/**
	 * Test Vector.reversed
	 */
	@Test
	public void reversed() {
		Assert.assertEquals("Wrong reversed vector.", base.reversed(), new NormalizedVector(-1, -2, -3));
	}

	/**
	 * Test Vector.length
	 */
	@Test
	public void length() {
		Assert.assertTrue("Wrong normalized vector length.", DoubleCompare.eq(base.length(), 1));
	}
}
