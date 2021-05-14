package scene.camera;

import primitives.Vector;
import rendering.Resolution;
import primitives.Point;

/**
 * Represends the field of view of the camera.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
class ViewPlane implements Iterable<Point[]> {

	/** The width of the view plane. */
	final double width;
	/** The height of the view plane. */
	final double height;
	/** The top left point. */
	final Point topLeft;
	/** Moves one pixel to the left. */
	final Vector nextCol;
	/** Moves one pixel down. */
	final Vector nextRowFirstCol;
	/** The resolution of the view plane. */
	final Resolution resolution;
	/** The orientation of the view plane. */
	final Orientation orientation;

	public ViewPlane(double width, double height, Point center, Resolution resolution, Orientation orientation) {
		this.width = width;
		this.height = height;
		this.orientation = orientation;
		nextCol = orientation.right.scale(width / resolution.x);
		Vector nextRow = orientation.up.scale(-height / resolution.y);
		nextRowFirstCol = nextRow.add(nextCol.scale(-(resolution.x - 1)));
		topLeft = center.subtract(orientation.right.scale(width / 2)).add(orientation.up.scale(height / 2))
			.add(nextCol.scale(0.5)).add(nextRow.scale(0.5));
		this.resolution = resolution;
	}

	@Override
	public ViewPlaneIterator iterator() {
		return iterator(1);
	}

	/**
	 * Get an iterator which returns an array of size subPixels x subPixels for each pixel.
	 *
	 * @param subPixels The number of sub pixels in each dimension for each pixel.
	 * @return An iterator for this view plane.
	 */
	public ViewPlaneIterator iterator(int subPixels) {
		return new ViewPlaneIterator(this, subPixels);
	}
}
