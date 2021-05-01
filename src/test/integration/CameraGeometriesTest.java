package integration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import camera.Camera;
import camera.CameraBuilder;
import camera.Resolution;
import geometries.Sphere;
import org.junit.Assert;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Ray;

/**
 * Tests the integration between the camera and the geometries. Namely, we check that the rays constructed by the camera
 * intersect with the geometries as expected.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class CameraGeometriesTest {

	@Test
	public void testSphere() {

		// Unit sphere at (0, 0, -3) with two intersections
		CameraBuilder builder = new CameraBuilder().location(Point.ORIGIN).front(new NormalizedVector(0, 0, -1))
			.up(new NormalizedVector(0, 1, 0)).width(3).height(3).distance(1).resolution(new Resolution("3x3"));
		Camera camera = builder.build();

		Sphere sphere = new Sphere(new Point(0, 0, -3), 1);
		List<Point> intersections = new ArrayList<>(2);
		for (Ray ray : camera) {
			intersections.addAll(sphere.intersect(ray));
		}
		Set<Point> expected = new HashSet<>(List.of(new Point(0, 0, -4), new Point(0, 0, -2)));
		Assert.assertEquals("Unit sphere with two intersections", expected, new HashSet<>(intersections));

		//
		camera = builder.location(new Point(0, 0, 0.5)).build();

	}
}
