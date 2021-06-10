package scene.camera;

import java.util.Iterator;
import primitives.Point;
import primitives.Vector;
import rendering.Resolution;

/**
 * A {@link PixelGrid} whose pixels store an array of points which are all evenly spaced throught that pixel. The number
 * of subpixel rows and columns can be specified by the constructor parameter {@code subPixels}. Possible applications
 * of this class include antialiasing.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
class MultiPixelGrid extends PixelGrid<Point[]> {

	private Resolution subResolution;
	private Point[] firstPoints;
	/** The number of sub rows and sub columns of points to store in each pixel. */
	public final int subPixels;

	/**
	 * Construct a new MultiPixelGrid with {@code subPixels * subPixels} points evenly spaced out in each pixel.
	 *
	 * @param width       The total width of the grid, that is, the number of columns times the width of each pixel.
	 * @param height      The total height of the grid, that is, the number of rows times the height of each pixel.
	 * @param center      The center point of the grid.
	 * @param resolution  The number of pixel rows and columns to use.
	 * @param orientation The three-dimensional orientation of the pixel grid.
	 * @param subPixels   The number of sub rows and sub columns of points to store in each pixel.
	 */
	protected MultiPixelGrid(double width, double height, Point center, Resolution resolution, Orientation orientation,
		int subPixels) {
		super(width, height, center, resolution, orientation);
		this.subPixels = subPixels;
		this.subResolution = new Resolution(subPixels, subPixels);
		firstPoints = firstPoints();
	}

	@Override
	public Iterator<Pixel<Point[]>> iterator() {
		return new PixelGridIterator<>(this, firstPoints, MultiPixelGrid::shiftPoints);
	}

	private Point[] firstPoints() {
		PixelGrid<Point> subGrid =
			new SinglePixelGrid(width / resolution.x, height / resolution.y, topLeft, subResolution, orientation);
		Point[] points = new Point[subGrid.numPixels()];
		int i = 0;
		for (Pixel<Point> pixel : subGrid) {
			points[i++] = pixel.data;
		}
		return points;
	}

	private static Point[] shiftPoints(Point[] points, Vector offset) {
		Point[] result = new Point[points.length];
		for (int i = 0; i < result.length; ++i) {
			result[i] = points[i].add(offset);
		}
		return result;
	}

}
