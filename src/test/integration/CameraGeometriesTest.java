package integration;

import org.junit.Test;
import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Assert;
import primitives.NormalizedVector;
import primitives.Point;
import rendering.Resolution;
import rendering.camera.Camera;
import rendering.camera.CameraSettings;
import rendering.camera.Pixel;

/**
 * Tests the integration between the camera and the geometries. Namely, we check that the rays constructed by the camera
 * intersect with the geometries as expected.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class CameraGeometriesTest {

	/**
	 * Tests that the rays from the camera intersect with the sphere in the correct coordinates.
	 */
	@Test
	public void testSphere() {

		// Only one ray intersects
		// Unit sphere at (0, 0, -3) with two intersections
		CameraSettings settings = new CameraSettings().location(Point.ORIGIN).front(new NormalizedVector(0, 0, -1))
			.up(NormalizedVector.J).width(3).height(3).distance(1).resolution(new Resolution("3x3"));

		Camera camera = new Camera(settings);
		Sphere sphere = new Sphere(new Point(0, 0, -3), 1);
		checkIntersectCount(camera, sphere, 2, "Unit sphere with two intersections");

		// All rays intersect
		// Sphere of radius 2.5 at (0, 0, -2.5) with 18 intersections
		camera = new Camera(settings.location(new Point(0, 0, 0.5)));
		sphere = new Sphere(new Point(0, 0, -2.5), 2.5);
		checkIntersectCount(camera, sphere, 18, "Sphere of radius 2.5 at (0, 0, -2.5) with 18 intersections");

		// Some rays intersect
		// Sphere of radius 2 at (0, 0, -2) with 10 intersections
		sphere = new Sphere(new Point(0, 0, -2), 2);
		checkIntersectCount(camera, sphere, 10, "Sphere of radius 2 at (0, 0, -2) with 10 intersections");

		// Camera is inside sphere
		// Sphere of radius 4 centered at (0, 0, 0) with 9 intersections
		sphere = new Sphere(Point.ORIGIN, 4);
		checkIntersectCount(camera, sphere, 9, "Sphere surrounding camera");

		// Sphere behind camera
		// Sphere of radius 4 centered at (0, 0, 0) with 9 intersections
		camera = new Camera(settings.location(Point.ORIGIN));
		sphere = new Sphere(new Point(0, 0, 1), 0.5);
		checkIntersectCount(camera, sphere, 0, "Sphere behind camera");
	}

	/**
	 * Tests that the rays from the camera intersect with the plane the correct number of times.
	 */
	@Test
	public void testPlane() {
		CameraSettings settings = new CameraSettings().dimensions(3, 3).distance(1)
			.front(new NormalizedVector(0, 0, -1)).up(NormalizedVector.J).resolution("3x3");

		// Plane parallel to the view plane.
		Camera camera = new Camera(settings);
		Plane plane = new Plane(new Point(0, 0, -2), new Point(1, 0, -2), new Point(2, 2, -2));
		checkIntersectCount(camera, plane, 9, "Wrong number of intersections for plane parallel to view plane.");

		// Slightly slanted plane
		plane = new Plane(new Point(0, 0, -2.1), new Point(1, 0, -2.2), new Point(2, 2, -2.6));
		checkIntersectCount(camera, plane, 9, "Wrong number of intersections for a slightly slanted plane.");

		// very slanted plane
		plane = new Plane(new Point(-1, 0, 0), new Point(-1, 1, 0), new Point(0, 0, -2));
		checkIntersectCount(camera, plane, 6, "Wrong number of intersections for a very slanted plane.");

		// Plane behind camera
		plane = new Plane(new Point(0, 0, 5), new Point(1, 0, 5), new Point(2, 2, 5));
		checkIntersectCount(camera, plane, 0, "Found intersections for a plane behind camera.");
	}

	/**
	 * Tests that the rays from the camera intersect with the plane the correct number of times.
	 */
	@Test
	public void testTriangle() {
		CameraSettings settings = new CameraSettings().dimensions(3, 3).distance(1).front(new NormalizedVector(0, 1, 0))
			.up(new NormalizedVector(0, 0, 1)).resolution("3x3");
		Camera camera = new Camera(settings);

		// triangle in plane parallel to view pane, completely in view pane
		// Only center ray should intersect
		Triangle triangle = new Triangle(new Point(0, 5, 2), new Point(-3, 5, -1), new Point(3, 5, -1));
		checkIntersectCount(camera, triangle, 1, "Wrong number of intersections for a parallel triangle");

		// triangle in plane parallel to view pane, all rays intersect triangle
		triangle = new Triangle(new Point(0, 5, 10), new Point(-30, 5, -10), new Point(30, 5, -10));
		checkIntersectCount(camera, triangle, 9, "Wrong number of intersections for a parallel triangle");

		// triangle behind camera
		triangle = new Triangle(new Point(0, -5, 10), new Point(-30, -5, -10), new Point(30, -5, -10));
		checkIntersectCount(camera, triangle, 0, "Wrong number of intersections for triangle behind camera");

		// triangle partially in camera
		triangle = new Triangle(new Point(0, 5, 10), new Point(-5, 5, -10), new Point(5, 5, -10));
		checkIntersectCount(camera, triangle, 3, "Wrong number of intersections for triangle partially in view");

		// triangle perpendicular to view plane
		triangle = new Triangle(new Point(0, 5, 0), new Point(-5, 2, 0), new Point(5, 2, 0));
		checkIntersectCount(camera, triangle, 0, "Wrong number of intersections for triangle partially in view");
	}

	/**
	 * Check that the rays from a camera intersects a geometry the correct number of times.
	 *
	 * @param camera        The camera to shoot rays from.
	 * @param geometry      The geometry to intersect with the rays.
	 * @param expectedCount The expected number of intersection points between the rays and the geometry.
	 * @param message       The message to display should the test fail.
	 */
	private void checkIntersectCount(Camera camera, Geometry geometry, int expectedCount, String message) {
		int actual = 0;
		for (Pixel pixel : camera) {
			actual += geometry.intersect(pixel.ray).size();
		}
		Assert.assertEquals(message, expectedCount, actual);
	}
}
