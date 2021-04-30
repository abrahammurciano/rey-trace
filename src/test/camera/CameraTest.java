package camera;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;
import util.Resolution;

/**
 * Testing Camera Class
 *
 * @author Abraham Murciano
 * @author Eli Levin
 *
 */
public class CameraTest {

	/**
	 * Test that iterating over the camera gives the correct rays.
	 */
	@Test
	public void testIteration() {

		// Odd width and height
		Point location = Point.ORIGIN;
		NormalizedVector up = new NormalizedVector(0, 0, 1);
		NormalizedVector front = new NormalizedVector(0, -1, 0);
		double width = 6;
		double height = 6;
		double distance = 10;
		Resolution resolution = new Resolution("3x3");
		Camera camera = new Camera(location, front, up, width, height, distance, resolution);

		List<Ray> expected = List.of(new Ray(location, new NormalizedVector(2, -10, 2)),
			new Ray(location, new NormalizedVector(0, -10, 2)), new Ray(location, new NormalizedVector(-2, -10, 2)),
			new Ray(location, new NormalizedVector(2, -10, 0)), new Ray(location, new NormalizedVector(0, -10, 0)),
			new Ray(location, new NormalizedVector(-2, -10, 0)), new Ray(location, new NormalizedVector(2, -10, -2)),
			new Ray(location, new NormalizedVector(0, -10, -2)), new Ray(location, new NormalizedVector(-2, -10, -2)));

		List<Ray> actual = new ArrayList<>(9);
		camera.forEach(actual::add);
		Assert.assertEquals("Wrong rays for 3x3 view plane.", expected, actual);

		width = 8;
		height = 8;
		resolution = new Resolution("4x4");
		camera = new Camera(location, front, up, width, height, distance, resolution);

		expected = List.of(new Ray(location, new NormalizedVector(3, -10, 3)),
			new Ray(location, new NormalizedVector(1, -10, 3)), new Ray(location, new NormalizedVector(-1, -10, 3)),
			new Ray(location, new NormalizedVector(-3, -10, 3)), new Ray(location, new NormalizedVector(3, -10, 1)),
			new Ray(location, new NormalizedVector(1, -10, 1)), new Ray(location, new NormalizedVector(-1, -10, 1)),
			new Ray(location, new NormalizedVector(-3, -10, 1)), new Ray(location, new NormalizedVector(3, -10, -1)),
			new Ray(location, new NormalizedVector(1, -10, -1)), new Ray(location, new NormalizedVector(-1, -10, -1)),
			new Ray(location, new NormalizedVector(-3, -10, -1)), new Ray(location, new NormalizedVector(3, -10, -3)),
			new Ray(location, new NormalizedVector(1, -10, -3)), new Ray(location, new NormalizedVector(-1, -10, -3)),
			new Ray(location, new NormalizedVector(-3, -10, -3)));

		actual = new ArrayList<>(16);
		camera.forEach(actual::add);
		Assert.assertEquals("Wrong rays for 4x4 view plane.", expected, actual);
	}

}
