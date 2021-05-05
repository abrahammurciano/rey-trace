package camera;

import java.util.Iterator;
import java.util.NoSuchElementException;
import primitives.Point;

class ViewPlaneIterator implements Iterator<Point> {

	private final ViewPlane view;
	private int col = 0;
	private int row = 0;
	private boolean hasNext = true;

	ViewPlaneIterator(ViewPlane view) {
		this.view = view;
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
	public Point next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		Point prev = view.point(col, row); // store value to return
		increment();
		return prev;
	}

	private void increment() {
		col = (col + 1) % view.resolution.x; // increment column
		if (col == 0) { // if column wrapped around
			++row;
			if (row >= view.resolution.y) { // if row overflowed number of pixels
				hasNext = false;
			}
		}
	}
}
