package scene.camera;

import java.util.Iterator;

import primitives.Point;
import primitives.Ray;
import util.EfficientIterator;

/**
 * An iterator to iterate over the rays shot by the camera.
 *
 * This iterator is thred safe. However, it is imperative that calls to {@link #hasNext()} and {@link #next()} which
 * logically depend on each other be surrounded by a {@code synchronized} block. For example, a thread repeatedly
 * calling {@link next()} need not have a {@code synchronized} block, but if it first checks {@link #hasNext()} and then
 * expects {@link #next()} not to throw an exception, those two calls must be in the same {@code synchronized} block.
 */
public class CameraIterator extends EfficientIterator<Pixel<Ray[]>> {

	private final Iterator<Pixel<Point[]>> viewPlaneIterator;
	private final SinglePixelGrid sensor;

	/**
	 * Get an iterator to iterate over the rays shot by the camera.
	 *
	 * @param camera The camera whose rays to iterate over.
	 */
	public CameraIterator(Camera camera) {
		this.viewPlaneIterator = camera.viewPlane.iterator();
		this.sensor = camera.sensor;
		setNext();
	}

	@Override
	protected void setNext() {
		if (!viewPlaneIterator.hasNext()) {
			hasNext = false;
			return;
		}
		Pixel<Point[]> pixel = viewPlaneIterator.next();
		Point[] subPixels = pixel.data;
		int sensorSize = sensor.numPixels();
		Ray[] rays = new Ray[subPixels.length * sensorSize];
		for (int i = 0; i < subPixels.length; ++i) {
			int j = 0;
			for (Pixel<Point> sensorPixel : sensor) {
				rays[i * sensorSize + j++] =
					new Ray(sensorPixel.data, sensorPixel.data.nonZeroVectorTo(subPixels[i]).normalized());
			}
		}
		next = new Pixel<Ray[]>(rays, pixel.row, pixel.col);
	}
}
