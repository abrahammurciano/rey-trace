package scene.camera;

import java.util.function.BiFunction;
import primitives.Vector;
import util.EfficientIterator;

class PixelGridIterator<T> extends EfficientIterator<Pixel<T>> {

	private final PixelGrid<T> view;
	private int col = 0;
	private int row = 0;
	private BiFunction<T, Vector, T> shift;

	/**
	 * Construct an iterator to iterate over the pixels in the view plane.
	 *
	 * @param view      The view plane to iterate over.
	 * @param subPixels The number of sub pixels to return for each pixel. For example, 3 means a 3x3 grid for each
	 *                  pixel, so 9 points would be returned for each iteration.
	 */
	PixelGridIterator(PixelGrid<T> view, T first, BiFunction<T, Vector, T> shift) {
		this.view = view;
		this.shift = shift;
		this.next = new Pixel<>(first, 0, 0);
	}

	@Override
	protected synchronized void setNext() {
		col = (col + 1) % view.resolution.x; // increment column
		if (col != 0) { // if column didn't wrap around
			next = nextPixel(view.nextCol);
		} else { // column wrapped around, so go to next row
			++row;
			if (row >= view.resolution.y) { // if row overflowed number of pixels
				hasNext = false;
			} else {
				next = nextPixel(view.nextRowFirstCol);
			}
		}
	}

	/**
	 * Uses the shift function and other fields to construct the next pixel.
	 *
	 * @param offset The vector from the current {@link Pixel} to the next one.
	 * @return The next pixel.
	 */
	private Pixel<T> nextPixel(Vector offset) {
		return new Pixel<>(shift.apply(next.representation, offset), col, row);
	}
}
