package geometries;

import org.junit.Assert;
import org.junit.Test;
import primitives.NormalizedVector;
import primitives.Point;

/**
 * Tests the methods of the Polygon class.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class PolygonTests {
	/**
	 * Tests that constructor takes at least 3 significant points.
	 */
	@Test
	public void testPointCount() {
		// Test number of points given
		// Equivalence partition tests
		// More than three points should not throw an exception.
		new Polygon(new Point(0, 0, 0), new Point(2, -1, 0), new Point(1, 1, 0),
				new Point(0.5, 1, 0));
		// More than three significant points should not throw an exception.
		new Polygon(new Point(0, 0, 0), new Point(1, -0.5, 0), new Point(2, -1, 0),
				new Point(1, 1, 0), new Point(0.5, 1, 0));
		// Less than three vertices should throw an exception.
		Assert.assertThrows("Too few points should throw an exception.",
				IllegalArgumentException.class,
				() -> new Polygon(new Point(0, 0, 0), new Point(1, 1, 1)));
		// Less than three significant vertices should throw an exception.
		Assert.assertThrows("Too few significant points should throw an exception.",
				IllegalArgumentException.class, () -> new Polygon(new Point(0, 0, 0),
						new Point(1, 1, 1), new Point(2, 2, 2), new Point(3, 3, 3)));

		// Boundary values test
		// Exactly three points should not throw an exception.
		new Polygon(new Point(0, 0, 0), new Point(1, 1, 0), new Point(0.5, 1, 0));
		// Exactly three significant points should not throw an exception.
		new Polygon(new Point(0, 0, 0), new Point(1, -0.5, 0), new Point(2, -1, 0),
				new Point(1, 1, 0));
	}

	/**
	 * Test that constructor accepts only convex polygons.
	 */
	@Test
	public void testConvex() {
		// Equivalence partition tests (there are no boundary values)

		// Convex polygon
		new Polygon(new Point(0, 0, 1), new Point(2, 2, 2), new Point(1, 1, 0),
				new Point(-1, -1, -1));

		// Concave polygons
		Assert.assertThrows("Concave polygons should throw an exception.",
				IllegalArgumentException.class, () -> new Polygon(new Point(0, 0, 0),
						new Point(1, 2, 0), new Point(2, 0, 0), new Point(1, 1, 0)));

		// Complex polygon - Type 1
		Assert.assertThrows("Complex polygon should throw an exception.",
				IllegalArgumentException.class, () -> new Polygon(new Point(0, 0, 0),
						new Point(1, 2, 0), new Point(2, 0, 0), new Point(0, 2, 0)));
		// Complex polygon - Type 2
		Assert.assertThrows("Complex star polygon should throw an exception.",
				IllegalArgumentException.class,
				() -> new Polygon(new Point(0, 0, 0), new Point(1, 2, 0), new Point(2, 0, 0),
						new Point(0, 2, 0), new Point(2, 1, 0)));

		// Non-planar polygon
		Assert.assertThrows("Non-planar polygon should throw an exception.",
				IllegalArgumentException.class, () -> new Polygon(new Point(0, 0, 0),
						new Point(1, 2, 0), new Point(0, 2, 0), new Point(1, 0, 1)));
	}

	/**
	 * Test that constructor doesn't accept repeated points.
	 */
	@Test
	public void testRepeatedPoints() {
		// Equivalence partition tests
		// Repeated points in the middle
		Assert.assertThrows("Repeated points should throw an exception.",
				IllegalArgumentException.class,
				() -> new Polygon(new Point(0, 0, 1), new Point(2, 2, 2), new Point(2, 2, 2),
						new Point(1, 1, 0), new Point(-1, -1, -1)));
		// Repeated end points
		Assert.assertThrows("Repeated end points should throw an exception.",
				IllegalArgumentException.class,
				() -> new Polygon(new Point(0, 0, 0), new Point(0, 1, 0), new Point(1, 1, 0),
						new Point(1, 0, 0), new Point(0, 0, 0)));
	}

	/**
	 * Test Polygon.normal
	 */
	@Test
	public void normal() {
		Polygon polygon = new Polygon(new Point(0, 0, 1), new Point(2, 2, 2), new Point(1, 1, 0),
				new Point(-1, -1, -1));
		NormalizedVector normal = polygon.normal(new Point(0, 0, 0));
		NormalizedVector expected_normal = new NormalizedVector(1, -1, 0);
		Assert.assertTrue("Wrong normal for Polygon.",
				normal.equals(expected_normal) || normal.equals(expected_normal.reversed()));
	}
}
