package scene.camera;

import primitives.Vector;
import rendering.Resolution;
import primitives.Point;

/**
 * Represends a rectangle in space with points along regular intervales along the width and height.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
class PixelGrid implements Iterable<Point[]> {

	/** The width of the pixel grid. */
	final double width;
	/** The height of the pixel grid. */
	final double height;
	/** The top left point. */
	final Point topLeft;
	/** Moves one pixel to the left. */
	final Vector nextCol;
	/** Moves one pixel down. */
	final Vector nextRowFirstCol;
	/** The resolution of the pixel grid. */
	final Resolution resolution;
	/** The orientation of the pixel grid. */
	final Orientation orientation;
	/** The center of the pixel grid. */
	final Point center;

	public PixelGrid(double width, double height, Point center, Resolution resolution, Orientation orientation) {
		this.width = width;
		this.height = height;
		this.orientation = orientation;
		this.center = center;
		nextCol = orientation.right.scale(width / resolution.x);
		Vector nextRow = orientation.up.scale(-height / resolution.y);
		nextRowFirstCol = resolution.x > 1 ? nextRow.add(nextCol.scale(-(resolution.x - 1))) : nextRow;
		topLeft = center.subtract(orientation.right.scale(width / 2)).add(orientation.up.scale(height / 2))
			.add(nextCol.scale(0.5)).add(nextRow.scale(0.5));
		this.resolution = resolution;
	}

	@Override
	public PixelGridIterator iterator() {
		return iterator(1);
	}

	/**
	 * Get an iterator which returns an array of size subPixels x subPixels for each pixel.
	 *
	 * @param subPixels The number of sub pixels in each dimension for each pixel.
	 * @return An iterator for this pixel grid.
	 */
	public PixelGridIterator iterator(int subPixels) {
		return new PixelGridIterator(this, subPixels);
	}

	/**
	 * Returns the number of pixels in this pixel grid.
	 *
	 * @return The number of pixels in this pixel grid.
	 */
	public int numPixels() {
		return resolution.x * resolution.y;
	}
}
