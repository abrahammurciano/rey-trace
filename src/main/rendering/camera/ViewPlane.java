package rendering.camera;

import primitives.Vector;
import rendering.Resolution;
import primitives.Point;

/**
 * Represends the field of view of the camera.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
class ViewPlane implements Iterable<Point> {

	final double width;
	final double height;

	/**
	 * The top left point
	 */
	final Point topLeft;

	/**
	 * Moves one pixel to the left
	 */
	final Vector nextCol;

	/**
	 * Moves one pixel down
	 */
	final Vector nextRowFirstCol;

	public final Resolution resolution;

	public ViewPlane(double width, double height, Point center, Resolution resolution, Orientation orientation) {
		this.width = width;
		this.height = height;
		nextCol = orientation.right.scale(width / resolution.x);
		Vector nextRow = orientation.up.scale(-height / resolution.y);
		nextRowFirstCol = nextRow.add(nextCol.scale(-(resolution.x - 1)));
		topLeft = center.subtract(orientation.right.scale(width / 2)).add(orientation.up.scale(height / 2))
			.add(nextCol.scale(0.5)).add(nextRow.scale(0.5));
		this.resolution = resolution;
	}

	@Override
	public ViewPlaneIterator iterator() {
		return new ViewPlaneIterator(this);
	}
}
