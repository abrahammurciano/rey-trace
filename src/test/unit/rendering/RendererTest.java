package unit.rendering;

import java.io.IOException;
import org.junit.Test;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import primitives.Colour;
import primitives.NormalizedVector;
import primitives.Point;
import rendering.Renderer;
import rendering.camera.Camera;
import rendering.camera.CameraSettings;
import rendering.raytracing.BasicRayTracer;
import scene.Scene;

/**
 * Testing Rendere Class
 *
 * @author Abraham Murciano
 * @author Eli Levin
 *
 */
public class RendererTest {

	Camera camera = new Camera(new CameraSettings().front(NormalizedVector.K.reversed()).up(NormalizedVector.J)
		.distance(100).dimensions(500, 500).resolution("1000x1000"));

	/**
	 * Produce a scene with basic 3D model and render it into a jpeg image.
	 */
	@Test
	public void testRender() {
		String filename = "images/test1.jpg";
		Scene scene = new Scene(new Colour(75, 127, 90), new AmbientLight(new Colour(255, 191, 191)));

		scene.geometries.add(new Sphere(new Point(0, 0, -100), 50),
			new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up left
			new Triangle(new Point(100, 0, -100), new Point(0, 100, -100), new Point(100, 100, -100)), // up right
			new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)), // down left
			new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100)));

		new Renderer(camera, new BasicRayTracer(scene), filename, 10).render();

		Util.assertImageCorrect("Rendered image looks incorrect.", filename);
	}

	/**
	 * Test for XML based scene - for bonus
	 */
	@Test
	public void basicRenderXml() {
		String filename = "images/basicRenderTestTwoColors.xml";
		Scene scene;
		try {
			scene = new xml.XmlSceneParser().parse(filename);
		} catch (IOException __) {
			System.out.println(filename + ": file not found or could not be opened.");
			return;
		}

		new Renderer(camera, new BasicRayTracer(scene), "images/test2.png", 10).render();

		Util.assertImageCorrect("XmlRenderedImage does not look correct", "images/test2.png");
	}
}
