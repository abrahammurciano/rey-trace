package camera;

import primitives.Vector;
import primitives.VectorBase;
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
	private final Point topLeft;

	/**
	 * Moves one pixel to the left
	 */
	private final Vector nextCol;

	/**
	 * Moves one pixel down
	 */
	private final Vector nextRow;

	public final Resolution resolution;

	public ViewPlane(double width, double height, Point center, Resolution resolution, Orientation orientation) {
		this.width = width;
		this.height = height;
		nextCol = orientation.right.scale(width / resolution.x);
		nextRow = orientation.up.scale(-height / resolution.y);
		topLeft = center.subtract(orientation.right.scale(width / 2)).add(orientation.up.scale(height / 2))
			.add(nextCol.scale(0.5)).add(nextRow.scale(0.5));
		this.resolution = resolution;
	}

	/**
	 * Calculate the {@link Point} at the center of the pixel at the given column and row.
	 *
	 * @param col The index of the column.
	 * @param row The index of the row.
	 * @return The point at the center of the pixel.
	 */
	public Point point(int col, int row) {
		// Passing VectorBase::create into scale allows scaling by 0, which allows getting the pixels at row 0 or column
		// 0 without preemptive checking.
		return topLeft.add(nextCol.scale(col, VectorBase::create)).add(nextRow.scale(row, VectorBase::create));
	}

	@Override
	public ViewPlaneIterator iterator() {
		return new ViewPlaneIterator(this);
	}
}
