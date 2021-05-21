package unit.lighting;

import org.junit.Assert;
import org.junit.Test;
import unit.rendering.Util;

/**
 * Testing narrow spotlight
 *
 * @author Abraham Murciano
 * @author Eli Levin
 *
 */
public class NarrowSpotlightTest {

	/**
	 * Produce a scene with a narrow spotlight shining on a sphere.
	 */
	@Test
	public void testSphereNarrowSpotlight() {
		Assert.assertTrue("Renedered image with narrow spotlight on sphere does not look correct",
			Util.renderXml("images/sphere-narrow-spotlight.xml", "images/sphere-narrow-spotlight.png"));
	}

	/**
	 * Produce a scene with a narrow spotlight shining on two triangles.
	 */
	@Test
	public void testTrianglesNarrowSpotlight() {
		Assert.assertTrue("Rendered image with narrow spotlight on triangles does not look correct",
			Util.renderXml("images/triangles-narrow-spotlight.xml", "images/triangles-narrow-spotlight.png"));
	}
}
