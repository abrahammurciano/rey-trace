package integration;

import org.junit.Assert;
import org.junit.Test;
import unit.rendering.Util;

/**
 * Testing integration between multiple light sources.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 *
 */
public class LightingTests {

	/**
	 * Produce a scene with many lights shining on a sphere.
	 */
	@Test
	public void testSphereMultiLights() {
		Assert.assertTrue("Renedered image with many lights on sphere does not look correct",
			Util.renderXml("images/sphere-multiple-lights.xml", "images/sphere-narrow-multiple-lights.png"));
	}

	/**
	 * Produce a scene with many lights shining on two triangles.
	 */
	@Test
	public void testTrianglesMultiLights() {
		Assert.assertTrue("Rendered image with many lights on triangles does not look correct",
			Util.renderXml("images/triangles-multiple-lights.xml", "images/triangles-multiple-lights.png"));
	}
}
