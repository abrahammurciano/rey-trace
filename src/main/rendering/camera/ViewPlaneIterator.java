package rendering.camera;

import java.util.Iterator;
import java.util.NoSuchElementException;
import primitives.Point;

class ViewPlaneIterator implements Iterator<Point> {

	private final ViewPlane view;
	private Point current;
	private int col = 0;
	private int row = 0;
	private boolean hasNext = true;

	ViewPlaneIterator(ViewPlane view) {
		this.view = view;
		this.current = view.topLeft;
	}

	/**
	 * Get the row and column indices of the next point to be returned by the iterator.
	 *
	 * @return The {@link Coordinates} of the next point.
	 */
	Coordinates nextCoordinates() {
		return new Coordinates(row, col);
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
		Point prev = current; // store value to return
		increment();
		return prev;
	}

	private void increment() {
		col = (col + 1) % view.resolution.x; // increment column
		if (col != 0) { // if column didn't wrap around
			current = current.add(view.nextCol);
		} else { // column wrapped around, so go to next row
			++row;
			if (row >= view.resolution.y) { // if row overflowed number of pixels
				hasNext = false;
			}
			current = current.add(view.nextRowFirstCol);
		}
	}
}
