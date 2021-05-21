package unit.lighting;

import org.junit.Assert;
import org.junit.Test;
import unit.rendering.Util;

/**
 * Testing point light
 *
 * @author Abraham Murciano
 * @author Eli Levin
 *
 */
public class PointLightTest {

	/**
	 * Produce a with point light shining on a sphere.
	 */
	@Test
	public void testSpherePointLight() {
		Assert.assertTrue("Renedered image with point light on sphere does not look correct",
			Util.renderXml("images/sphere-point-light.xml", "images/sphere-point-light.png"));
	}

	/**
	 * Produce a scene with point light shining on two triangles.
	 */
	@Test
	public void testTrianglesPointLight() {
		Assert.assertTrue("Rendered image with point light on triangles does not look correct",
			Util.renderXml("images/triangles-point-light.xml", "images/triangles-point-light.png"));
	}
}
