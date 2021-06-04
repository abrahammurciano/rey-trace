package scene.camera;

import java.util.Iterator;

import primitives.Point;
import primitives.Ray;

/**
 * An iterator to iterate over the rays shot by the camera.
 *
 * This iterator is thred safe. However, it is imperative that calls to {@link #hasNext()} and {@link #next()} which
 * logically depend on each other be surrounded by a {@code synchronized} block. For example, a thread repeatedly
 * calling {@link next()} need not have a {@code synchronized} block, but if it first checks {@link #hasNext()} and then
 * expects {@link #next()} not to throw an exception, those two calls must be in the same {@code synchronized} block.
 */
public class CameraIterator implements Iterator<Pixel> {

	private final PixelGridIterator viewPlaneIterator;
	private final PixelGrid sensor;

	/**
	 * Get an iterator to iterate over the rays shot by the camera.
	 *
	 * @param camera    The camera whose rays to iterate over.
	 * @param subPixels The number of sub pixels in each dimension for each pixel.
	 */
	public CameraIterator(Camera camera, int subPixels) {
		this.viewPlaneIterator = camera.viewPlane.iterator(subPixels);
		this.sensor = camera.sensor;
	}

	@Override
	public boolean hasNext() {
		synchronized (this) {
			return viewPlaneIterator.hasNext();
		}
	}

	@Override
	public Pixel next() {
		int row, col;
		Point[] viewPlanePoints;
		synchronized (this) {
			row = viewPlaneIterator.nextRow();
			col = viewPlaneIterator.nextCol();
			viewPlanePoints = viewPlaneIterator.next();
		}
		int sensorPixels = sensor.numPixels();
		Ray[] rays = new Ray[viewPlanePoints.length * sensorPixels];
		for (int i = 0; i < viewPlanePoints.length; ++i) {
			int j = 0;
			for (Point[] sensorPoints : sensor) {
				rays[i * sensorPixels + j++] =
					new Ray(sensorPoints[0], sensorPoints[0].vectorTo(viewPlanePoints[i]).normalized());
			}
		}
		return new Pixel(row, col, rays);
	}

}
