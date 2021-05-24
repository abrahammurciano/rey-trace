package unit.lighting;

import org.junit.Assert;
import org.junit.Test;
import unit.rendering.Util;

/**
 * Testing directional light
 *
 * @author Abraham Murciano
 * @author Eli Levin
 *
 */
public class DirectionalLightTest {

	/**
	 * Produce a with directional light shining on a sphere.
	 */
	@Test
	public void testSphereDirectional() {
		Assert.assertTrue("Renedered image with directional light on sphere does not look correct",
			Util.renderXml("images/sphere-directional.xml", "images/sphere-directional.png"));
	}

	/**
	 * Produce a scene with directional light shining on two triangles.
	 */
	@Test
	public void testTrianglesDirectional() {
		Assert.assertTrue("Rendered image with directional light on triangles does not look correct",
			Util.renderXml("images/triangles-directional.xml", "images/triangles-directional.png"));
	}
}
