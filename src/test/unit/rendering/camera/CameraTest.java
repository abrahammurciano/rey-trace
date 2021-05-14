package unit.rendering.camera;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;
import scene.camera.Camera;
import scene.camera.CameraSettings;

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
		Point position = camera.position;

		List<Ray> expected = List.of(new Ray(position, new NormalizedVector(2, -10, 2)),
			new Ray(position, new NormalizedVector(0, -10, 2)), new Ray(position, new NormalizedVector(-2, -10, 2)),
			new Ray(position, new NormalizedVector(2, -10, 0)), new Ray(position, new NormalizedVector(0, -10, 0)),
			new Ray(position, new NormalizedVector(-2, -10, 0)), new Ray(position, new NormalizedVector(2, -10, -2)),
			new Ray(position, new NormalizedVector(0, -10, -2)), new Ray(position, new NormalizedVector(-2, -10, -2)));

		List<Ray> actual1 = new ArrayList<>(9);
		camera.forEach(pixel -> actual1.add(pixel.rays[0]));
		Assert.assertEquals("Wrong rays for 3x3 view plane.", expected, actual1);

		camera = new Camera(settings.dimensions(8, 8).resolution("4x4"));

		expected = List.of(new Ray(position, new NormalizedVector(3, -10, 3)),
			new Ray(position, new NormalizedVector(1, -10, 3)), new Ray(position, new NormalizedVector(-1, -10, 3)),
			new Ray(position, new NormalizedVector(-3, -10, 3)), new Ray(position, new NormalizedVector(3, -10, 1)),
			new Ray(position, new NormalizedVector(1, -10, 1)), new Ray(position, new NormalizedVector(-1, -10, 1)),
			new Ray(position, new NormalizedVector(-3, -10, 1)), new Ray(position, new NormalizedVector(3, -10, -1)),
			new Ray(position, new NormalizedVector(1, -10, -1)), new Ray(position, new NormalizedVector(-1, -10, -1)),
			new Ray(position, new NormalizedVector(-3, -10, -1)), new Ray(position, new NormalizedVector(3, -10, -3)),
			new Ray(position, new NormalizedVector(1, -10, -3)), new Ray(position, new NormalizedVector(-1, -10, -3)),
			new Ray(position, new NormalizedVector(-3, -10, -3)));

		List<Ray> actual2 = new ArrayList<>(16);
		camera.forEach(pixel -> actual2.add(pixel.rays[0]));
		Assert.assertEquals("Wrong rays for 4x4 view plane.", expected, actual2);
	}

	@Test
	public void testRotate() {
		Camera camera = new Camera(new CameraSettings());
		Assert.assertEquals("Default camera front should be positive x", NormalizedVector.I,
			new CameraSettings(camera).front());

		CameraSettings settings = new CameraSettings(camera.rotate(90 * Math.PI / 180, 0, 0));
		Assert.assertEquals("Pitch of 90 degrees should point down.", NormalizedVector.K.reversed(), settings.front());
		Assert.assertEquals("Pitch of 90 degrees' up should point forward.", NormalizedVector.I, settings.up());

		settings = new CameraSettings(camera.rotate(0, 90 * Math.PI / 180, 0));
		Assert.assertEquals("Yaw of 90 degrees should point left.", NormalizedVector.J, settings.front());
		Assert.assertEquals("Yaw of 90 degrees' up should point up.", NormalizedVector.K, settings.up());

		settings = new CameraSettings(camera.rotate(0, 0, 90 * Math.PI / 180));
		Assert.assertEquals("Roll of 90 degrees should point forward.", NormalizedVector.I, settings.front());
		Assert.assertEquals("Roll of 90 degrees' up should point right.", NormalizedVector.J.reversed(), settings.up());
	}

}
