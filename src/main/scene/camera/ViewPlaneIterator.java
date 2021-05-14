package scene.camera;

import java.util.Iterator;
import java.util.NoSuchElementException;
import primitives.Point;
import primitives.Vector;
import rendering.Resolution;

class ViewPlaneIterator implements Iterator<Point[]> {

	private final ViewPlane view;
	private Point[] current;
	private int col = 0;
	private int row = 0;
	private boolean hasNext = true;

	/**
	 * Construct an iterator to iterate over the pixels in the view plane.
	 *
	 * @param view      The view plane to iterate over.
	 * @param subPixels The number of sub pixels to return for each pixel. For example, 3 means a 3x3 grid for each
	 *                  pixel, so 9 points would be returned for each iteration.
	 */
	ViewPlaneIterator(ViewPlane view, int subPixels) {
		this.view = view;
		if (subPixels == 1) { // base case
			this.current = new Point[] {view.topLeft}; // set to singleton array
			return;
		}
		this.current = new Point[subPixels * subPixels];

		// Make a mini view plane that splits the first pixel into subPixels by subPixels little rectangles.
		ViewPlaneIterator subIterator = new ViewPlane(view.width / view.resolution.x, view.height / view.resolution.y,
			view.topLeft, new Resolution(subPixels, subPixels), view.orientation).iterator();

		for (int i = 0; i < current.length; ++i) {
			current[i] = subIterator.next()[0];
		}
	}

	/**
	 * Get the row index of the next point to be returned by the iterator.
	 *
	 * @return The row index of the next point.
	 */
	int nextRow() {
		return row;
	}

	/**
	 * Get the column index of the next point to be returned by the iterator.
	 *
	 * @return The column index of the next point.
	 */
	int nextCol() {
		return col;
	}

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public Point[] next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		Point[] prev = current; // store value to return
		increment();
		return prev;
	}

	private void increment() {
		col = (col + 1) % view.resolution.x; // increment column
		if (col != 0) { // if column didn't wrap around
			current = addToSubPixels(view.nextCol);
		} else { // column wrapped around, so go to next row
			++row;
			if (row >= view.resolution.y) { // if row overflowed number of pixels
				hasNext = false;
			}
			current = addToSubPixels(view.nextRowFirstCol);
		}
	}

	/**
	 * Add the given vector to all the sub pixels in the {@code current} pixel.
	 *
	 * @param next The vector to add to the points in {@code current}.
	 * @return An array of the points which are the sub pixels of the next pixel.
	 */
	private Point[] addToSubPixels(Vector next) {
		Point[] result = new Point[current.length];
		for (int i = 0; i < current.length; ++i) {
			result[i] = current[i].add(next);
		}
		return result;
	}
}
