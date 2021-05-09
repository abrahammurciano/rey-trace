package rendering;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import primitives.Colour;
import rendering.camera.Camera;
import rendering.camera.CameraSettings;
import rendering.camera.Coordinates;
import rendering.camera.Pixel;
import rendering.raytracing.RayTracer;

/**
 * This class is responsible for communicating between a {@link RayTracer} and an {@link ImageWriter}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Renderer {
	private final Camera camera;
	private final ImageWriter writer;
	private final RayTracer rayTracer;
	private final Map<Coordinates, Colour> buffer = new HashMap<>();
	private final int threads;
	private final Resolution resolution;

	/**
	 * Construct a renderer with the provided data.
	 *
	 * @param camera    The camera to use to render the scene.
	 * @param rayTracer The rayTracer to use to calculate the colour of each pixel.
	 * @param filename  The filename to write the rendered image to.
	 */
	public Renderer(Camera camera, RayTracer rayTracer, String filename, int threads) {
		this.resolution = camera.resolution();
		Resolution enhanced = new Resolution(resolution.x * 2, resolution.y * 2);
		this.camera = new Camera(new CameraSettings(camera).resolution(enhanced));
		this.rayTracer = rayTracer;
		this.writer = new ImageWriter(filename, camera.resolution());
		this.threads = threads;
	}

	/**
	 * Calculates the colours of each pixel, write them to the ImageWriter, then write the image to the output file.
	 *
	 * @param threads The number of threads to use to render the image.
	 */
	public void render() {
		Iterator<Pixel> iterator = camera.iterator();
		Thread[] children = startChildrenThreads(threads, () -> new RenderThread(iterator));
		waitForChildren(children);
		antiAliasing();
		writer.writeToFile();
	}

	/**
	 * Create and start the specified number of children threads using the given constructor, then return the created
	 * threads.
	 *
	 * @param threads     The number of threads to create.
	 * @param constructor A function which creates new threads.
	 * @return An array of the created threads.
	 */
	private Thread[] startChildrenThreads(int threads, Supplier<Thread> constructor) {
		Thread[] children = new Thread[threads];
		for (int i = 0; i < threads; ++i) {
			children[i] = constructor.get();
			children[i].start();
		}
		return children;
	}

	/**
	 * Waits for all the threads in the given array to finish. If this thread is interrupted during the wait, it will
	 * interrupt all the children and return immediately.
	 *
	 * @param children The array of threads to wait for.
	 */
	private void waitForChildren(Thread[] children) {
		for (Thread child : children) {
			try {
				child.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				for (Thread child_ : children) {
					child_.interrupt();
				}
				Thread.currentThread().interrupt();
				return;
			}
		}
	}

	private void antiAliasing() {
		for (int row = 0; row < resolution.x; ++row) {
			for (int col = 0; col < resolution.y; ++col) {
				Colour c1 = buffer.get(new Coordinates(row * 2, col * 2));
				Colour c2 = buffer.get(new Coordinates(row * 2 + 1, col * 2));
				Colour c3 = buffer.get(new Coordinates(row * 2, col * 2 + 1));
				Colour c4 = buffer.get(new Coordinates(row * 2 + 1, col * 2 + 1));
				writer.setPixel(row, col, Colour.average(c1, c2, c3, c4));
			}
		}
	}

	/**
	 * When run, this thread will continuously consume {@link Pixel}s from its iterator, compute its colour using
	 * {@code Renderer.rayTracer}, then write send it to {@code Renderer.writer}. Once the iterator has no more
	 * elements, the thread ends.
	 */
	private class RenderThread extends Thread {
		private Iterator<Pixel> iterator;

		public RenderThread(Iterator<Pixel> iterator) {
			this.iterator = iterator;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Pixel pixel = iterator.next();
					Colour colour = rayTracer.trace(pixel.ray);
					synchronized (buffer) {
						buffer.put(pixel.coordinates, colour);
					}
				} catch (NoSuchElementException e) {
					return;
				}
			}
		}
	}
}
