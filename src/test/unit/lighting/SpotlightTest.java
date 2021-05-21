package unit.lighting;

import org.junit.Assert;
import org.junit.Test;
import unit.rendering.Util;

/**
 * Testing spotlight
 *
 * @author Abraham Murciano
 * @author Eli Levin
 *
 */
public class SpotlightTest {

	/**
	 * Produce a with spotlight shining on a sphere.
	 */
	@Test
	public void testSphereDirectional() {
		Assert.assertTrue("Renedered image with spotlight on sphere does not look correct",
			Util.renderXml("images/sphere-spotlight.xml", "images/sphere-spotlight.png"));
	}

	/**
	 * Produce a scene with spotlight shining on two triangles.
	 */
	@Test
	public void testTrianglesDirectional() {
		Assert.assertTrue("Rendered image with emission light does not look correct",
			Util.renderXml("images/triangles-spotlight.xml", "images/triangles-spotlight.png"));
	}
}
