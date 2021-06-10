package scene.camera;

import primitives.NonZeroVector;
import rendering.Resolution;
import primitives.Point;

/**
 * Represends a rectangle in space with points along regular intervales along the width and height.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
abstract class PixelGrid<T> implements Iterable<Pixel<T>> {

	/** The width of the pixel grid. */
	final double width;
	/** The height of the pixel grid. */
	final double height;
	/** The top left point. */
	final Point topLeft;
	/** Moves one pixel to the left. */
	final NonZeroVector nextCol;
	/** Moves one pixel down. */
	final NonZeroVector nextRowFirstCol;
	/** The resolution of the pixel grid. */
	final Resolution resolution;
	/** The orientation of the pixel grid. */
	final Orientation orientation;
	/** The center of the pixel grid. */
	final Point center;

	protected PixelGrid(double width, double height, Point center, Resolution resolution, Orientation orientation) {
		this.width = width;
		this.height = height;
		this.orientation = orientation;
		this.center = center;
		nextCol = orientation.right.scale(width / resolution.x);
		NonZeroVector nextRow = orientation.up.scale(-height / resolution.y);
		nextRowFirstCol = resolution.x > 1 ? nextRow.add(nextCol.scale(-(resolution.x - 1))) : nextRow;
		topLeft = center.subtract(orientation.right.scale(width / 2)).add(orientation.up.scale(height / 2))
			.add(nextCol.scale(0.5)).add(nextRow.scale(0.5));
		this.resolution = resolution;
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
