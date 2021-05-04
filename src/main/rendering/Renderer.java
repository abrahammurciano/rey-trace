package rendering;

import camera.Camera;
import rendering.rayTracing.RayTracer;
import scene.Scene;

/**
 * This class is responsible for communicating between a {@link RayTracer} and an {@link ImageWriter}.
 *
 * @author
 * @author
 */
public class Renderer {
	/**
	 * Construct a renderer with the provided data.
	 *
	 * @param scene     The scene to be rendered.
	 * @param camera    The camera to use to render the scene.
	 * @param rayTracer The rayTracer to use to calculate the colour of each pixel.
	 * @param filename  The filename to write the rendered image to.
	 */
	public Renderer(Scene scene, Camera camera, RayTracer rayTracer, String filename) {
		// TODO: implement
	}

	/**
	 * Calculates the colours of each pixel, write them to the ImageWriter, then write the image to the output file.
	 */
	public void render() {
		// TODO: implement
	}
}
