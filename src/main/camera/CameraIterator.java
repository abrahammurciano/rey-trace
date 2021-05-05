package camera;

import java.util.Iterator;

import primitives.Point;
import primitives.Ray;

/**
 * An iterator to iterate over the rays shot by the camera.
 */
public class CameraIterator implements Iterator<Pixel> {

	private final Point source;
	private final ViewPlaneIterator viewPlaneIterator;

	/**
	 * Get an iterator to iterate over the rays shot by the camera.
	 *
	 * @param camera The camera whose rays to iterate over.
	 */
	public CameraIterator(Camera camera) {
		this.viewPlaneIterator = camera.view.iterator();
		this.source = camera.location;
	}

	@Override
	public boolean hasNext() {
		return viewPlaneIterator.hasNext();
	}

	@Override
	public Pixel next() {
		int row = viewPlaneIterator.nextRow();
		int col = viewPlaneIterator.nextCol();
		Point p = viewPlaneIterator.next();
		return new Pixel(row, col, new Ray(source, source.vectorTo(p).normalized()));
	}

}
