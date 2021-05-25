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
}
