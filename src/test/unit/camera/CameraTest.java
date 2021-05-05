package unit.camera;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import camera.Camera;
import camera.CameraSettings;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;

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
		CameraSettings settings =
			new CameraSettings().front(new NormalizedVector(0, -1, 0)).dimensions(6, 6).distance(10).resolution("3x3");
		Camera camera = new Camera(settings);
		Point location = camera.location;

		List<Ray> expected = List.of(new Ray(location, new NormalizedVector(2, -10, 2)),
			new Ray(location, new NormalizedVector(0, -10, 2)), new Ray(location, new NormalizedVector(-2, -10, 2)),
			new Ray(location, new NormalizedVector(2, -10, 0)), new Ray(location, new NormalizedVector(0, -10, 0)),
			new Ray(location, new NormalizedVector(-2, -10, 0)), new Ray(location, new NormalizedVector(2, -10, -2)),
			new Ray(location, new NormalizedVector(0, -10, -2)), new Ray(location, new NormalizedVector(-2, -10, -2)));

		List<Ray> actual1 = new ArrayList<>(9);
		camera.forEach(pixel -> actual1.add(pixel.ray));
		Assert.assertEquals("Wrong rays for 3x3 view plane.", expected, actual1);

		camera = new Camera(settings.dimensions(8, 8).resolution("4x4"));

		expected = List.of(new Ray(location, new NormalizedVector(3, -10, 3)),
			new Ray(location, new NormalizedVector(1, -10, 3)), new Ray(location, new NormalizedVector(-1, -10, 3)),
			new Ray(location, new NormalizedVector(-3, -10, 3)), new Ray(location, new NormalizedVector(3, -10, 1)),
			new Ray(location, new NormalizedVector(1, -10, 1)), new Ray(location, new NormalizedVector(-1, -10, 1)),
			new Ray(location, new NormalizedVector(-3, -10, 1)), new Ray(location, new NormalizedVector(3, -10, -1)),
			new Ray(location, new NormalizedVector(1, -10, -1)), new Ray(location, new NormalizedVector(-1, -10, -1)),
			new Ray(location, new NormalizedVector(-3, -10, -1)), new Ray(location, new NormalizedVector(3, -10, -3)),
			new Ray(location, new NormalizedVector(1, -10, -3)), new Ray(location, new NormalizedVector(-1, -10, -3)),
			new Ray(location, new NormalizedVector(-3, -10, -3)));

		List<Ray> actual2 = new ArrayList<>(16);
		camera.forEach(pixel -> actual2.add(pixel.ray));
		Assert.assertEquals("Wrong rays for 4x4 view plane.", expected, actual2);
	}

}
