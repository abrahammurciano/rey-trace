package geometries;

import org.junit.Assert;
import org.junit.Test;
import primitives.Point;

/**
 * Tests the methods of the Triangle class.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class TriangleTests {

	/**
	 * Tests that constructor rejects colinear points.
	 */
	@Test
	public void testColinearPoints() {
		// Non colinear points
		new Triangle(new Point(0, 0, 0), new Point(2, -1, 0), new Point(1, 1, 0));
		// Colinear points
		Assert.assertThrows("Colinear points should throw an exception.",
				IllegalArgumentException.class,
				() -> new Triangle(new Point(0, 0, 0), new Point(1, 1, 1), new Point(2, 2, 2)));
	}
}
