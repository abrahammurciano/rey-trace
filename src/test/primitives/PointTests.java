package primitives;

import org.junit.Assert;
import org.junit.Test;

import util.DoubleCompare;

/**
 * Test the functions of the point class
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class PointTests {
	private final Point p = new Point(1,2,3);
	/**
	 * Test Point.add
	 */
	@Test
	public void add() {
		Point calc, actual;
		
		// simple adddition
		calc = p.add(new Vector(2,3,4));
		actual = new Point(3,5,7);
		Assert.assertEquals("Incorrect output for adding vector to point", calc, actual);

		//cross over zero
		calc = p.add(new Vector(-2,-4,-5));
		actual = new Point(-1,-2,-2);
		Assert.assertEquals("Incorrect output for adding vector to point while crossing origin", calc, actual);
		
		// floating point addition
		calc = p.add(new Vector(0.4537,-0.7891,0.1309));
		actual = new Point(1.4537,1.2109,3.1309);
		Assert.assertEquals("Incorrect output for adding vector with 4 points of accuracy", calc, actual);

		// go to origin
		calc = p.add(new Vector(-1,-2,-3));
		actual = Point.ORIGIN;
		Assert.assertEquals("Incorrect output for adding vector to obtain origin", calc, actual);
	}

	/**
	 * Test Vector.vectorTo
	 */
	@Test
	public void vectorTo() {
		// This method is supposed to accept a point and return a vector to it
		Vector calc, actual;

		// non-zero vector
		calc = p.vectorTo(new Point(2,3,4));
		actual = new Vector(1,1,1);
		Assert.assertEquals("Incorrect vectorTo other point.", calc, actual);

		// return zero vector
		Assert.assertThrows("Expected zero vector", ZeroVectorException.class,	() -> p.vectorTo(new Point(1,2,3)));
	}

	/**
	 * Test Point.vectorFrom
	 */
	@Test
	public void vectorFrom() {
		Vector calc, actual;

		calc = p.vectorFrom(new Point(2,3,4));
		actual = new Vector(-1,-1,-1);
		Assert.assertEquals("Incorrect vectorTo other point.", calc, actual);

		Assert.assertThrows("Expected zero vector", ZeroVectorException.class,	() -> p.vectorFrom(new Point(1,2,3)));
	}

	/**
	 * Test Point.distance
	 */
	@Test
	public void distance() {
		double calc, actual;

		calc = p.distance(new Point(2,3,4));
		actual = Math.sqrt(3);
		Assert.assertTrue("Incorrect distance to other point", DoubleCompare.eq(calc,actual));

		calc = p.distance(new Point(1,2,3));
		actual = 0;
		Assert.assertTrue("Distance should be zero", DoubleCompare.eq(calc,actual));
	}

	/**
	 * Test Point.sum
	 */
	@Test
	public void sum() {
		double calc, actual;

		calc = p.sum();
		actual = 6;
		Assert.assertTrue("Incorrect sum", DoubleCompare.eq(calc, actual));

		Point newP = new Point(3.742,2.589,-29.0381);
		calc = newP.sum();
		actual = -22.7071;
		Assert.assertTrue("Incorrect sum", DoubleCompare.eq(calc, actual));

		newP = new Point(1,1,-2);
		calc = newP.sum();
		actual = 0;
		Assert.assertTrue("Incorrect sum", DoubleCompare.eq(calc, actual));
	}

	/**
	 * Test Point.equals
	 */
	@Test
	public void equals() {
        Assert.assertEquals("Equal points are not considered equal.", p, new Point(1,2,3));
        Assert.assertNotEquals("Non-equal points are considered equal.", p, new Point(2,3,4));
	}
}
