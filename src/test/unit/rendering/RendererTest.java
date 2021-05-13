package unit.rendering;

import java.io.IOException;
import org.junit.Test;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import primitives.Colour;
import primitives.Material;
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
	 * Produce a scene with basic 3D model and render it into a png image.
	 */
	@Test
	public void testRender() {
		Scene scene = new Scene(new Colour(75, 127, 90), new AmbientLight(new Colour(255, 191, 191)));

		Material black = new Material(0, 0, 0);

		scene.geometries.add(new Sphere(black, new Point(0, 0, -100), 50),
			new Triangle(black, new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)),
			new Triangle(black, new Point(100, 0, -100), new Point(0, 100, -100), new Point(100, 100, -100)),
			new Triangle(black, new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)),
			new Triangle(black, new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100)));


		String outfile = "images/test1.png";
		new Renderer(camera, new BasicRayTracer(scene), outfile).render(10, 1);

		Util.assertImageCorrect("Rendered image does not look correct.", outfile);
	}

	/**
	 * Test for XML based scene - for bonus
	 */
	@Test
	public void basicRenderXml() {
		renderXml("images/ambient.xml", "images/ambient.png");
		renderXml("images/emission.xml", "images/emission.png");
	}

	private void renderXml(String inFile, String outFile) {
		Scene scene;
		try {
			scene = new xml.XmlSceneParser().parse(inFile);
		} catch (IOException __) {
			System.out.println(inFile + ": file not found or could not be opened.");
			return;
		}

		new Renderer(camera, new BasicRayTracer(scene), outFile).render(10, 3);

		Util.assertImageCorrect("XmlRenderedImage does not look correct", outFile);
	}
}
