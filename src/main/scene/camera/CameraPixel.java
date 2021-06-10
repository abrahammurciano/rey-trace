package scene.camera;

import primitives.Point;
import primitives.Ray;

/**
 * This class represents a pixel of the camera.
 */
class CameraPixel extends Pixel<Ray[]> {
	/**
	 * Constructs a pixel of the camera with the given fields.
	 *
	 * @param row  The row index of the pixel.
	 * @param col  The columnt index of the pixel.
	 * @param rays The rays from the camera through various points of the pixel.
	 */
	CameraPixel(int row, int col, Ray[] rays) {
		super(rays, row, col);
	}

	CameraPixel(Point position, Pixel<Point[]> pixel) {
		super(constructRays(position, pixel.data), pixel.col, pixel.row);
	}

	private static Ray[] constructRays(Point source, Point[] targets) {
		Ray[] rays = new Ray[targets.length];
		for (int i = 0; i < targets.length; ++i) {
			rays[i] = new Ray(source, source.vectorTo(targets[i]).normalized());
		}
		return rays;
	}
}
