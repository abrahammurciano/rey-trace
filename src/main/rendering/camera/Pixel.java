package rendering.camera;

import primitives.Ray;

/**
 * This class represents a pixel of the camera.
 */
public class Pixel {
	/** The row index of the pixel. */
	public final int row;
	/** The columnt index of the pixel. */
	public final int col;
	/** The ray from the camera through the center of the pixel. */
	public final Ray ray;

	/**
	 * Constructs a pixel of the camera with the given fields.
	 *
	 * @param row The row index of the pixel.
	 * @param col The columnt index of the pixel.
	 * @param ray The ray from the camera through the center of the pixel.
	 */
	Pixel(int row, int col, Ray ray) {
		this.row = row;
		this.col = col;
		this.ray = ray;
	}
}
