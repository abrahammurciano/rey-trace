package unit.rendering;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testing Rendere Class
 *
 * @author Abraham Murciano
 * @author Eli Levin
 *
 */
public class RendererTest {

	/**
	 * Produce a scene with with only ambient light into images/ambient.png
	 */
	@Test
	public void testRenderAmbient() {
		Assert.assertTrue("Renedered image with ambient light does not look correct",
			Util.renderXml("images/ambient.xml", "images/ambient.png"));
	}

	/**
	 * Produce a scene with emission lights into images/emission.png
	 */
	@Test
	public void testRenderEmission() {
		Assert.assertTrue("Rendered image with emission light does not look correct",
			Util.renderXml("images/emission.xml", "images/emission.png"));
	}

	/**
	 * Produce a scene with a sphere inside a transparent sphere into images/refract-two-spheres.png
	 */
	@Test
	public void refractTwoSpheres() {
		Assert.assertTrue("Sphere in transparent sphere does not look correct",
			Util.renderXml("images/refract-two-spheres.xml", "images/refract-two-spheres.png"));
	}

	/**
	 * Produce a scene with a sphere inside a transparent sphere into images/refract-two-spheres.png
	 */
	@Test
	public void twoSpheresMirrors() {
		Assert.assertTrue("Sphere in transparent sphere with mirrors does not look correct",
			Util.renderXml("images/refract-two-spheres-mirrors.xml", "images/refract-two-spheres-mirrors.png"));
	}

	/**
	 * Produce a scene with a sphere inside a transparent sphere into images/refract-two-spheres.png
	 */
	@Test
	public void triangleTransparentSphere() {
		Assert.assertTrue("Sphere in transparent sphere does not look correct",
			Util.renderXml("images/triangles-transparent-sphere.xml", "images/triangles-transparent-sphere.png"));
	}

	/**
	 * Produce a scene with a sphere inside a transparent sphere into images/refract-two-spheres.png
	 */
	@Test
	public void triangleSphereInitial() {
		Assert.assertTrue("Sphere in transparent sphere does not look correct",
			Util.renderXml("images/triangle-sphere-initial.xml", "images/triangle-sphere-initial.png"));
	}

	/**
	 * Produce a scene with a sphere inside a transparent sphere into images/refract-two-spheres.png
	 */
	@Test
	public void triangleSphereT1() {
		Assert.assertTrue("Sphere in transparent sphere does not look correct",
			Util.renderXml("images/triangle-sphere-t1.xml", "images/triangle-sphere-t1.png"));
	}

	/**
	 * Produce a scene with a sphere inside a transparent sphere into images/refract-two-spheres.png
	 */
	@Test
	public void triangleSphereT2() {
		Assert.assertTrue("Sphere in transparent sphere does not look correct",
			Util.renderXml("images/triangle-sphere-t2.xml", "images/triangle-sphere-t2.png"));
	}

	/**
	 * Produce a scene with a sphere inside a transparent sphere into images/refract-two-spheres.png
	 */
	@Test
	public void triangleSphereL1() {
		Assert.assertTrue("Sphere in transparent sphere does not look correct",
			Util.renderXml("images/triangle-sphere-l1.xml", "images/triangle-sphere-l1.png"));
	}

	/**
	 * Produce a scene with a sphere inside a transparent sphere into images/refract-two-spheres.png
	 */
	@Test
	public void triangleSphereL2() {
		Assert.assertTrue("Sphere in transparent sphere does not look correct",
			Util.renderXml("images/triangle-sphere-l2.xml", "images/triangle-sphere-l2.png"));
	}

	/**
	 * Produce a scene with a sphere inside a transparent sphere into images/refract-two-spheres.png
	 */
	@Test
	public void trianglesSphere() {
		Assert.assertTrue("Sphere in transparent sphere does not look correct",
			Util.renderXml("images/triangles-sphere.xml", "images/triangles-sphere.png"));
	}

	/**
	 * Produce a scene with a sphere inside a transparent sphere into images/refract-two-spheres.png
	 */
	@Test
	public void trianglesSphereRotated() {
		Assert.assertTrue("Sphere in transparent sphere does not look correct",
			Util.renderXml("images/triangles-sphere-rotated.xml", "images/triangles-sphere-rotated.png"));
	}

	/**
	 * Produce a scene with a sphere inside a transparent sphere into images/refract-two-spheres.png
	 */
	@Test
	public void bubblesCylinder() {
		Assert.assertTrue("Sphere in transparent sphere does not look correct",
			Util.renderXml("images/bubbles-cylinder.xml", "images/bubbles-cylinder.png"));
	}

	/**
	 * Produce a scene with a sphere inside a transparent sphere into images/refract-two-spheres.png
	 */
	@Test
	public void bonusObjects() {
		Assert.assertTrue("Sphere in transparent sphere does not look correct",
			Util.renderXml("images/bonus-objects.xml", "images/bonus-objects.png"));
	}

	/**
	 * Produce a scene with a sphere inside a transparent sphere into images/refract-two-spheres.png
	 */
	@Test
	public void bonusObjectsRotated() {
		Assert.assertTrue("Sphere in transparent sphere does not look correct",
			Util.renderXml("images/bonus-objects-rotated.xml", "images/bonus-objects-rotated.png"));
	}
}
