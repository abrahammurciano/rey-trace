package elements;

import primitives.Vector;
import java.util.Iterator;
import java.util.NoSuchElementException;
import primitives.Point;
import util.Resolution;

/**
 * Represends the field of view of the camera.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
class ViewPlane implements Iterable<Point> {
	/**
	 * The top left point
	 */
	public final Point p0;

	/**
	 * Moves one pixel to the left
	 */
	private final Vector nextCol;

	/**
	 * Moves one pixel down and back to first column (from last column)
	 */
	private final Vector nextRow;

	public final Resolution resolution;

	public ViewPlane(double width, double height, Point center, Resolution resolution,
		Orientation orientation) {
		nextCol = orientation.right.scale(width / resolution.x);
		Vector nextRowStraight = orientation.up.scale(height / resolution.y);
		nextRow = nextRowStraight.subtract(nextCol.scale((double) (resolution.x - 1)));
		p0 = center.subtract(orientation.right.scale(width / 2))
			.subtract(orientation.up.scale(height / 2)).add(nextCol.scale(0.5))
			.add(nextRowStraight.scale(0.5));
		this.resolution = resolution;
	}

	@Override
	public Iterator<Point> iterator() {
		return new PointIterator(this);
	}

	public class PointIterator implements Iterator<Point> {

		private final ViewPlane view;
		private Point current;
		private int x;
		private int y;

		public PointIterator(ViewPlane view) {
			this.view = view;
			current = view.p0;
			this.x = 0;
			this.y = 0;
		}

		@Override
		public boolean hasNext() {
			return x < view.resolution.x - 1 || y < view.resolution.y - 1;
		}

		@Override
		public Point next() {
			Point prev = current; // store value to return
			x = (x + 1) % view.resolution.x; // increment x
			if (x == 0) { // if x wrapped around
				++y;
				if (y >= view.resolution.y) { // if y overflowed number of pixels
					throw new NoSuchElementException();
				}
				current = current.add(view.nextRow); // move current to start of next row
			} else {
				current = current.add(view.nextCol); // move current one column across
			}
			return prev;
		}

	};
}
