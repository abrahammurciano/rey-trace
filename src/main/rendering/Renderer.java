package rendering;

import java.util.Iterator;
import camera.Camera;
import camera.Pixel;
import rendering.rayTracing.RayTracer;
import scene.Scene;

/**
 * This class is responsible for communicating between a {@link RayTracer} and an {@link ImageWriter}.
 *
 * @author
 * @author
 */
public class Renderer {
	private Camera camera;

	/**
	 * Construct a renderer with the provided data.
	 *
	 * @param scene     The scene to be rendered.
	 * @param camera    The camera to use to render the scene.
	 * @param rayTracer The rayTracer to use to calculate the colour of each pixel.
	 * @param filename  The filename to write the rendered image to.
	 */
	public Renderer(Scene scene, Camera camera, RayTracer rayTracer, String filename) {
		this.camera = camera;
	}

	/**
	 * Calculates the colours of each pixel, write them to the ImageWriter, then write the image to the output file.
	 *
	 * @param threads The number of threads to use to render the image.
	 */
	public void render(int threads) {
		Iterator<Pixel> iterator = camera.iterator();
		for (int i = 0; i < threads; ++i) {
			new RenderThread(iterator).start();
		}
	}

	private class RenderThread extends Thread {
		private Iterator<Pixel> iterator;

		public RenderThread(Iterator<Pixel> iterator) {
			this.iterator = iterator;
		}

		@Override
		public void run() {
			synchronized (iterator) {
				// TODO: complete
			}
		}
	}
}
