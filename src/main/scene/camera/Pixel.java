package scene.camera;

import primitives.Ray;

/**
 * This class represents a pixel of the camera.
 */
public class Pixel {
	/** The row index of the pixel. */
	public final int row;
	/** The columnt index of the pixel. */
	public final int col;
	/** The ray from the camera through various points of the pixel. */
	public final Ray[] rays;

	/**
	 * Constructs a pixel of the camera with the given fields.
	 *
	 * @param row  The row index of the pixel.
	 * @param col  The columnt index of the pixel.
	 * @param rays The rays from the camera through various points of the pixel.
	 */
	Pixel(int row, int col, Ray[] rays) {
		this.row = row;
		this.col = col;
		this.rays = rays;
	}
}
