package rendering;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import rendering.camera.Camera;
import rendering.camera.Pixel;
import rendering.raytracing.RayTracer;

/**
 * This class is responsible for communicating between a {@link RayTracer} and an {@link ImageWriter}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Renderer {
	private Camera camera;
	private ImageWriter writer;
	private RayTracer rayTracer;

	/**
	 * Construct a renderer with the provided data.
	 *
	 * @param camera    The camera to use to render the scene.
	 * @param rayTracer The rayTracer to use to calculate the colour of each pixel.
	 * @param filename  The filename to write the rendered image to.
	 */
	public Renderer(Camera camera, RayTracer rayTracer, String filename) {
		this.camera = camera;
		this.rayTracer = rayTracer;
		this.writer = new ImageWriter(filename, camera.resolution());
	}

	/**
	 * Calculates the colours of each pixel, write them to the ImageWriter, then write the image to the output file.
	 *
	 * @param threads The number of threads to use to render the image.
	 */
	public void render(int threads) {
		Iterator<Pixel> iterator = camera.iterator();
		Thread[] children = startChildrenThreads(threads, () -> new RenderThread(iterator));
		waitForChildren(children);
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
					writer.setPixel(pixel.row, pixel.col, rayTracer.trace(pixel.ray));
				} catch (NoSuchElementException e) {
					return;
				}
			}
		}
	}
}
