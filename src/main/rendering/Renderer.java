package rendering;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import cli.Task;
import cli.TaskTracker;
import primitives.Ray;
import scene.camera.Camera;
import scene.camera.Pixel;
import rendering.raytracing.RayTracer;

/**
 * This class is responsible for communicating between a {@link RayTracer} and an {@link ImageWriter}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Renderer implements Task {
	private Camera camera;
	private ImageWriter writer;
	private RayTracer rayTracer;
	private List<TaskTracker> taskTrackers = new LinkedList<>();
	private int threads;

	/**
	 * Construct a renderer with the provided data.
	 *
	 * @param camera    The camera to use to render the scene.
	 * @param rayTracer The rayTracer to use to calculate the colour of each pixel.
	 * @param filename  The filename to write the rendered image to.
	 * @param threads   The number of threads to use to render the image.
	 */
	public Renderer(Camera camera, RayTracer rayTracer, String filename, int threads) {
		this.camera = camera;
		this.rayTracer = rayTracer;
		this.threads = threads;
		this.writer = new ImageWriter(filename, camera.resolution());
	}

	/**
	 * Calculates the colours of each pixel, write them to the ImageWriter, then write the image to the output file.
	 *
	 */
	public void render() {
		performTask();
	}

	@Override
	public void task() {
		Iterator<Pixel<Ray[]>> iterator = camera.iterator();
		Thread[] children = startChildrenThreads(() -> new RenderThread(iterator));
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
	private Thread[] startChildrenThreads(Supplier<Thread> constructor) {
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
	 * When run, this thread will continuously consume {@link Pixel<Ray[]>}s from its iterator, compute its colour using
	 * {@code Renderer.rayTracer}, then write send it to {@code Renderer.writer}. Once the iterator has no more
	 * elements, the thread ends.
	 */
	private class RenderThread extends Thread {
		private Iterator<Pixel<Ray[]>> iterator;

		public RenderThread(Iterator<Pixel<Ray[]>> iterator) {
			this.iterator = iterator;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Pixel<Ray[]> pixel;
					synchronized (iterator) {
						pixel = iterator.next();
					}
					writer.setPixel(pixel.row, pixel.col, rayTracer.trace(pixel.data));
					completeJobs(1);
				} catch (NoSuchElementException e) {
					return;
				}
			}
		}
	}

	@Override
	public List<TaskTracker> taskTrackers() {
		return taskTrackers;
	}

	@Override
	public int totalJobs() {
		Resolution resolution = camera.resolution();
		return resolution.columns * resolution.rows;
	}
}
