package test;

import org.junit.Assert;
import org.junit.Test;
import primitives.Vector;
import primitives.ZeroVectorException;

/**
 * Tests the functions of the vector class
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class VectorTests {
	/**
	 * Tests Vector.cross
	 */
	@Test
	public void cross() {
		Vector base = new Vector(1, 2, 3);
		Vector calc, actual;

		// Equivalence Partition Tests

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

		// Boundary Values Test
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
		// TODO: implement
	}

	/**
	 * Test Vector.subtract
	 */
	@Test
	public void subtract() {
		// TODO: implement
	}

	/**
	 * Test Vector.scale
	 */
	@Test
	public void scale() {
		// TODO: implement
	}

	/**
	 * Test Vector.reversed
	 */
	@Test
	public void reversed() {
		// TODO: implement
	}

	/**
	 * Test Vector.dot
	 */
	@Test
	public void dot() {
		// TODO: implement
	}

	/**
	 * Test Vector.length
	 */
	@Test
	public void length() {
		// TODO: implement
	}

	/**
	 * Test Vector.normalized
	 */
	@Test
	public void normalized() {
		// TODO: implement
	}

	/**
	 * Test Vector.angle
	 */
	@Test
	public void angle() {
		// TODO: implement
	}

	/**
	 * Test Vector.equals
	 */
	@Test
	public void equals() {
		// TODO: implement
	}
}
