package test;

import org.junit.Assert;
import org.junit.Test;
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
public class VectorTests {
	private static final String NON_PARALLEL_VECTORS_GAVE_WRONG_RESULT =
			"Non-parallel vectors gave wrong result.";
	private static final String PARALLEL_VECTORS_GAVE_WRONG_RESULT =
			"Parallel vectors gave wrong result.";

	/**
	 * Tests Vector.cross
	 */
	@Test
	public void cross() {
		Vector base = new Vector(1, 2, 3);
		Vector calc, actual;

		// Equivalence partition tests
		// Same quadrant
		calc = base.cross(new Vector(3, 4, 5));
		actual = new Vector(-2, 4, -2);
		Assert.assertEquals("Wrong output for cross product in the same quadrant.", calc, actual);

		// Opposite quadrant
		calc = base.cross(new Vector(-2, -4, -3));
		actual = new Vector(6, -3, 0);
		Assert.assertEquals("Wrong output for cross product in opposite quadrant.", calc, actual);

		// Other quadrant
		calc = base.cross(new Vector(-2, -8, 5));
		actual = new Vector(34, -11, -4);
		Assert.assertEquals("Wrong output for cross product in other quadrant.", calc, actual);

		// Boundary values test
		Vector colinear = new Vector(-2, -4, -6);
		Assert.assertThrows("Expected ZeroVectorException to be thrown for opposite vectors.",
				ZeroVectorException.class, () -> base.cross(colinear));
		Assert.assertThrows("Expected ZeroVectorException to be thrown co-directional vectors.",
				ZeroVectorException.class, () -> base.cross(colinear.reversed()));
	}

	/**
	 * Test Vector.add
	 */
	@Test
	public void add() {
		Vector base = new Vector(1, 2, 3);

		// Equivalence partition tests
		Assert.assertEquals(NON_PARALLEL_VECTORS_GAVE_WRONG_RESULT, base.add(new Vector(3, 2, 4)),
				new Vector(4, 4, 7));
		Assert.assertEquals(NON_PARALLEL_VECTORS_GAVE_WRONG_RESULT,
				base.add(new Vector(-3, -2, -4)), new Vector(-2, 0, -1));

		// Boundary values test
		// Parallel vectors
		Assert.assertEquals(PARALLEL_VECTORS_GAVE_WRONG_RESULT, base.add(base),
				new Vector(2, 4, 6));
		Assert.assertEquals(PARALLEL_VECTORS_GAVE_WRONG_RESULT, base.add(new Vector(-2, -4, -6)),
				new Vector(-1, -2, -3));
		Assert.assertThrows("A vector plus its negation should throw a ZeroVectorException.",
				ZeroVectorException.class, () -> base.add(new Vector(-1, -2, -3)));
	}

	/**
	 * Test Vector.subtract
	 */
	@Test
	public void subtract() {
		Vector base = new Vector(1, 2, 3);

		// Equivalence partition tests
		Assert.assertEquals(NON_PARALLEL_VECTORS_GAVE_WRONG_RESULT,
				base.subtract(new Vector(3, 2, 4)), new Vector(-2, 0, -1));
		Assert.assertEquals(NON_PARALLEL_VECTORS_GAVE_WRONG_RESULT,
				base.subtract(new Vector(-3, -2, -4)), new Vector(4, 4, 7));

		// Boundary values test
		// Parallel vectors
		Assert.assertEquals(PARALLEL_VECTORS_GAVE_WRONG_RESULT,
				base.subtract(new Vector(-1, -2, -3)), new Vector(2, 4, 6));
		Assert.assertEquals(PARALLEL_VECTORS_GAVE_WRONG_RESULT, base.subtract(new Vector(2, 4, 6)),
				new Vector(-1, -2, -3));
		Assert.assertThrows("A vector minus itself should throw a ZeroVectorException.",
				ZeroVectorException.class, () -> base.subtract(new Vector(1, 2, 3)));
	}

	/**
	 * Test Vector.scale
	 */
	@Test
	public void scale() {
		Vector base = new Vector(1, 2, 3);

		// Equivalence partition tests
		Assert.assertEquals("Wrong output for scale factor > 1", base.scale(2),
				new Vector(2, 4, 6));
		Assert.assertEquals("Wrong output for scale factor in range (0,1)", base.scale(0.5),
				new Vector(0.5, 1, 1.5));
		Assert.assertEquals("Wrong output for scale factor in range (-1,0)", base.scale(-0.5),
				new Vector(-0.5, -1, -1.5));
		Assert.assertEquals("Wrong output for scale factor < -1", base.scale(-2),
				new Vector(-2, -4, -6));

		// Boundary values test
		Assert.assertEquals("Wrong output for scale factor 1", base.scale(1), new Vector(1, 2, 3));
		Assert.assertThrows("Scale factor of zero did not throw ZeroVectorException.",
				ZeroVectorException.class, () -> base.scale(0));
		Assert.assertEquals("Wrong output for scale factor -1", base.scale(-1),
				new Vector(-1, -2, -3));
	}

	/**
	 * Test Vector.reversed
	 */
	@Test
	public void reversed() {
		Vector base = new Vector(1, 2, 3);
		Assert.assertEquals("Wrong reversed vector.", base.reversed(), new Vector(-1, -2, -3));
	}

	/**
	 * Test Vector.dot
	 */
	@Test
	public void dot() {
		Vector base = new Vector(1, 2, 3);

		// Equivalence partition tests
		Assert.assertTrue("Acute vectors give wrong dot product.",
				Util.equals(base.dot(new Vector(1, 2, 4)), 17));
		Assert.assertTrue("Obtuse vectors give wrong dot product.",
				Util.equals(base.dot(new Vector(-2, -2, -3)), -15));

		// Boundary values test
		Assert.assertTrue("Parallel vectors give wrong dot product.",
				Util.equals(base.dot(new Vector(2, 4, 6)), 28));
		Assert.assertTrue("Perpendicular vectors give wrong dot product.",
				Util.equals(base.dot(new Vector(3, -4, 5d / 3)), 0));
		Assert.assertTrue("Antiparallel vectors give wrong dot product.",
				Util.equals(base.dot(new Vector(-2, -4, -6)), -28));
	}

	/**
	 * Test Vector.length
	 */
	@Test
	public void length() {
		Vector base = new Vector(1, 2, 3);
		Assert.assertTrue("Wrong vector length.", Util.equals(base.length(), 3.741657386773941));
	}

	/**
	 * Test Vector.normalized
	 */
	@Test
	public void normalized() {
		// Equivalence partition tests
		Assert.assertTrue("Vector with magnitude larger than 1 not normalized correctly.",
				Util.equals(
						Point.ORIGIN.add(new Vector(1, 2, 3).normalized()).distance(Point.ORIGIN),
						1));
		Assert.assertTrue("Vector with magnitude smaller than 1 not normalized correctly.",
				Util.equals(Point.ORIGIN.add(new Vector(0.1, 0.2, 0.3).normalized())
						.distance(Point.ORIGIN), 1));

		// Boundary values test
		Assert.assertTrue("Vector with magnitude equal to 1 not normalized correctly.",
				Util.equals(Point.ORIGIN
						.add(new Vector(1.414213562373095, 1.414213562373095, 1.414213562373095)
								.normalized())
						.distance(Point.ORIGIN), 1));
	}

	/**
	 * Test Vector.angle
	 */
	@Test
	public void angle() {
		Vector base = new Vector(1, 2, 3);

		// Equivalence partition tests
		Assert.assertTrue("Acute vectors give wrong angle.",
				Util.equals(base.angle(new Vector(1, 2, 4)), 0.1307826338479177));
		Assert.assertTrue("Obtuse vectors give wrong angle.",
				Util.equals(base.angle(new Vector(-2, -2, -3)), 2.905697773154866));

		// Boundary values test
		Assert.assertTrue("Parallel vectors give wrong angle.",
				Util.equals(base.angle(new Vector(2, 4, 6)), 0));
		Assert.assertTrue("Perpendicular vectors give wrong angle.",
				Util.equals(base.angle(new Vector(3, -4, 5d / 3)), Math.PI / 2));
		Assert.assertTrue("Antiparallel vectors give wrong angle.",
				Util.equals(base.angle(new Vector(-2, -4, -6)), Math.PI));
	}

	/**
	 * Test Vector.equals
	 */
	@Test
	public void equals() {
		Vector base = new Vector(1, 2, 3);
		Assert.assertEquals("Equivalent vectors not treated as such.", base, new Vector(1, 2, 3));
		Assert.assertNotEquals("Nonequivalent vectors are treated as equal.", base,
				new Vector(2, 2, 3));
	}
}
