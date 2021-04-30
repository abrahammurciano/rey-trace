package primitives;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the functions of the ray class
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class RayTests {

	private final Ray r = new Ray(new Point(1, 2, 3), new NormalizedVector(3, 2, 1));

	@Test
	public void equals() {
		Assert.assertEquals("Rays should be equal", r, new Ray(new Point(1, 2, 3), new NormalizedVector(3, 2, 1)));
		Assert.assertNotEquals("Rays should not be equal", r,
			new Ray(new Point(4, 4, 4), new NormalizedVector(3, 2, 1)));

	}
}
