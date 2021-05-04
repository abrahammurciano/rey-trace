package integration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import camera.Camera;
import camera.CameraBuilder;
import camera.Resolution;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
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

	/**
	 * Tests that the rays from the camera intersect with the sphere in the correct coordinates.
	 */
	@Test
	public void testSphere() {

		// Only one ray intersects
		// Unit sphere at (0, 0, -3) with two intersections
		CameraBuilder builder = new CameraBuilder().location(Point.ORIGIN).front(new NormalizedVector(0, 0, -1))
			.up(new NormalizedVector(0, 1, 0)).width(3).height(3).distance(1).resolution(new Resolution("3x3"));
		Camera camera = builder.build();

		Sphere sphere = new Sphere(new Point(0, 0, -3), 1);
		Set<Point> intersections = new HashSet<>(2);
		for (Ray ray : camera) {
			intersections.addAll(sphere.intersect(ray));
		}
		Set<Point> expected = new HashSet<>(List.of(new Point(0, 0, -4), new Point(0, 0, -2)));
		Assert.assertEquals("Unit sphere with two intersections", expected, intersections);

		// All rays intersect
		// Sphere of radius 2.5 at (0, 0, -2.5) with 18 intersections
		camera = builder.location(new Point(0, 0, 0.5)).build();

		sphere = new Sphere(new Point(0, 0, -2.5), 2.5);
		intersections = new HashSet<>(18);
		for (Ray ray : camera) {
			intersections.addAll(sphere.intersect(ray));
		}
		expected = new HashSet<>(
			List.of(new Point(0.5645856533065, 0, -0.06458565330651), new Point(2.435414346693, 0, -1.935414346693),
				new Point(0.7113248654052, -0.7113248654052, -0.2113248654052),
				new Point(1.2886751345948, -1.2886751345948, -0.7886751345948),
				new Point(0.7113248654052, 0.7113248654052, -0.2113248654052),
				new Point(1.2886751345948, 1.2886751345948, -0.7886751345948), new Point(0, 0, 0), new Point(0, 0, -5),
				new Point(0, 0.56458565330651, -0.06458565330651), new Point(0, 2.435414346693, -1.935414346693),
				new Point(0, -0.56458565330651, -0.06458565330651), new Point(0, -2.435414346693, -1.935414346693),
				new Point(-0.56458565330651, 0, -0.06458565330651), new Point(-2.435414346693, 0, -1.935414346693),
				new Point(-0.7113248654052, 0.7113248654052, -0.2113248654052),
				new Point(-1.2886751345948, 1.2886751345948, -0.7886751345948),
				new Point(-0.7113248654052, -0.7113248654052, -0.2113248654052),
				new Point(-1.2886751345948, -1.2886751345948, -0.7886751345948)));
		Assert.assertEquals("Sphere of radius 2.5 at (0, 0, -2.5) with 18 intersections", expected, intersections);

		// Some rays intersect
		// Sphere of radius 2 at (0, 0, -2) with 10 intersections
		sphere = new Sphere(new Point(0, 0, -2), 2);
		intersections = new HashSet<>(10);
		for (Ray ray : camera) {
			intersections.addAll(sphere.intersect(ray));
		}
		expected = new HashSet<>(List.of(new Point(0, 0, 0), new Point(0, 0, -4),
			new Point(0, 0.5885621722339, -0.08856217223385), new Point(0, 1.911437827766, -1.411437827766),
			new Point(0, -0.5885621722339, -0.08856217223385), new Point(0, -1.911437827766, -1.411437827766),
			new Point(-0.5885621722339, 0, -0.08856217223385), new Point(-1.911437827766, 0, -1.411437827766),
			new Point(0.5885621722339, 0, -0.08856217223385), new Point(1.911437827766, 0, -1.411437827766)));
		Assert.assertEquals("Sphere of radius 2 at (0, 0, -2) with 10 intersections", expected, intersections);

		// Camera is inside sphere
		// Sphere of radius 4 centered at (0, 0, 0) with 9 intersections
		sphere = new Sphere(Point.ORIGIN, 4);
		intersections = new HashSet<>(9);
		for (Ray ray : camera) {
			intersections.addAll(sphere.intersect(ray));
		}
		expected = new HashSet<>(List.of(new Point(2.464008125348, -2.464008125348, -1.964008125348),
			new Point(2.464008125348, 2.464008125348, -1.96400812534), new Point(0, 0, -4),
			new Point(0, 3.067356917396, -2.567356917396), new Point(0, -3.067356917396, -2.567356917396),
			new Point(-3.067356917396, 0, -2.567356917396), new Point(-2.464008125348, 2.464008125348, -1.96400812534),
			new Point(-2.464008125348, -2.464008125348, -1.96400812534),
			new Point(3.067356917396, 0, -2.567356917396)));
		Assert.assertEquals("Sphere surrounding camera", expected, intersections);

		// Sphere behind camera
		// Sphere of radius 4 centered at (0, 0, 0) with 9 intersections
		camera = builder.location(Point.ORIGIN).build();
		sphere = new Sphere(new Point(0, 0, 1), 0.5);
		intersections = new HashSet<>(0);
		for (Ray ray : camera) {
			intersections.addAll(sphere.intersect(ray));
		}
		expected = Collections.emptySet();
		Assert.assertEquals("Sphere behind camera", expected, intersections);
	}

	/**
	 * Tests that the rays from the camera intersect with the plane the correct number of times.
	 */
	@Test
	public void testPlane() {
		CameraBuilder builder = new CameraBuilder().dimensions(3, 3).distance(1).front(new NormalizedVector(0, 0, -1))
			.up(new NormalizedVector(0, 1, 0)).resolution("3x3");
		Camera camera = builder.build();

		// Plane parallel to the view plane.
		Plane plane = new Plane(new Point(0, 0, -2), new Point(1, 0, -2), new Point(2, 2, -2));
		List<Point> intersections = new ArrayList<>(9);
		for (Ray ray : camera) {
			intersections.addAll(plane.intersect(ray));
		}
		Assert.assertEquals("Wrong number of intersections for plane parallel to view plane.", 9, intersections.size());

		// Slightly slanted plane
		plane = new Plane(new Point(0, 0, -2.1), new Point(1, 0, -2.2), new Point(2, 2, -2.6));
		intersections = new ArrayList<>(9);
		for (Ray ray : camera) {
			intersections.addAll(plane.intersect(ray));
		}
		Assert.assertEquals("Wrong number of intersections for a slightly slanted plane.", 9, intersections.size());

		// very slanted plane

		// Plane behind camera
		plane = new Plane(new Point(0, 0, 5), new Point(1, 0, 5), new Point(2, 2, 5));
		intersections = new ArrayList<>(0);
		for (Ray ray : camera) {
			intersections.addAll(plane.intersect(ray));
		}
		Assert.assertEquals("Found intersections for a plane behind camera.", 0, intersections.size());
	}

	/**
	 * Tests that the rays from the camera intersect with the plane the correct number of times.
	 */
	@Test
	public void testTriangle() {
		CameraBuilder builder = new CameraBuilder().dimensions(3, 3).distance(1).front(new NormalizedVector(0, 1, 0))
			.up(new NormalizedVector(0, 0, 1)).resolution("3x3");
		Camera camera = builder.build();

		// triangle in plane parallel to view pane, completely in view pane
		// Only center ray should intersect
		Triangle triangle = new Triangle(new Point(0, 5, 2), new Point(-3, 5, -1), new Point(3, 5,-1));
		List<Point> intersections = new ArrayList<>(1);
		for (Ray ray : camera) {
			intersections.addAll(triangle.intersect(ray));
		}
		Assert.assertEquals("Wrong number of intersections for a parallel triangle", 1, intersections.size());

		// triangle in plane parallel to view pane, all rays intersect triangle
		triangle = new Triangle(new Point(0, 5, 10), new Point(-30, 5, -10), new Point(30, 5,-10));
		intersections = new ArrayList<>(9);
		for (Ray ray : camera) {
			intersections.addAll(triangle.intersect(ray));
		}
		Assert.assertEquals("Wrong number of intersections for a parallel triangle", 9, intersections.size());

		// triangle behind camera
		triangle = new Triangle(new Point(0, -5, 10), new Point(-30, -5, -10), new Point(30, -5,-10));
		intersections = new ArrayList<>();
		for (Ray ray : camera) {
			intersections.addAll(triangle.intersect(ray));
		}
		Assert.assertEquals("Wrong number of intersections for triangle behind camera", 0, intersections.size());

		// triangle partially in camera
		triangle = new Triangle(new Point(0, 5, 10), new Point(-5, 5, -10), new Point(5, 5,-10));
		intersections = new ArrayList<>(3);
		for (Ray ray : camera) {
			intersections.addAll(triangle.intersect(ray));
		}
		Assert.assertEquals("Wrong number of intersections for triangle partially in view", 3, intersections.size());

		// triangle perpendicular to view plane
		triangle = new Triangle(new Point(0, 5, 0), new Point(-5, 2, 0), new Point(5, 2, 0));
		intersections = new ArrayList<>();
		for (Ray ray : camera) {
			intersections.addAll(triangle.intersect(ray));
		}
		Assert.assertEquals("Wrong number of intersections for triangle partially in view", 0, intersections.size());
	}
}
