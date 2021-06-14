package scene.camera;

/**
 * This class represents a pixel in a {@link PixelGrid}. The pixel knows its position in the grid in terms of the row
 * and column indices.
 *
 * @param <T> The generic type T is the type of the data that each pixel knows about itself. This may be simply
 *            the center point of the pixel, it may be a collection of many points in the pixel, it may be a ray (or an
 *            array of rays) from some point to the pixel, or anything else.
 */
public class Pixel<T> {
	/** The data stored by each pixel. This may be the center point of the pixel for example, or a ray to the pixel. */
	public final T data;
	/** The row index of the pixel in its {@link PixelGrid}. */
	public final int row;
	/** The column index of the pixel in its {@link PixelGrid}. */
	public final int col;

	/**
	 * Construct a new {@link Pixel} with the given row and column indices storing some {@code data} about itself.
	 *
	 * @param data The data stored by the pixel.
	 * @param row  The row index of the pixel.
	 * @param col  The column index of the pixel.
	 */
	public Pixel(T data, int row, int col) {
		this.data = data;
		this.row = row;
		this.col = col;
	}
}
