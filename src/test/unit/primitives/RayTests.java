package unit.primitives;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.Test;
import geometries.Intersection;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;

/**
 * Test the functions of the ray class
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class RayTests {

	private final Ray r = new Ray(new Point(1, 2, 3), new NormalizedVector(3, 2, 1));

	/**
	 * Test Point.closest
	 */
	@Test
	public void closest() {
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

		// Empty list
		Assert.assertThrows("Expected exception for empty list", NoSuchElementException.class,
			() -> r.closest(Collections.emptyList()));

		Intersection closest = new Intersection(null, new Point(1, 2, 3));
		// List with one element
		List<Intersection> intersections = List.of(closest);
		Assert.assertEquals("Closest point of one element list is incorrect", r.closest(intersections), closest);

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

		// List with two elements
		intersections = List.of(closest, new Intersection(null, new Point(1, 2, 4)));
		Assert.assertEquals("Closest point of two element list is incorrect", r.closest(intersections), closest);
	}

	@Test
	public void equals() {
		Assert.assertEquals("Rays should be equal", r, new Ray(new Point(1, 2, 3), new NormalizedVector(3, 2, 1)));
		Assert.assertNotEquals("Rays should not be equal", r,
			new Ray(new Point(4, 4, 4), new NormalizedVector(3, 2, 1)));

	}
}
