package scene.camera;

import java.util.Iterator;
import primitives.Point;
import primitives.Vector;
import rendering.Resolution;

public class MultiPixelGrid extends PixelGrid<Point[]> {

	private Resolution subResolution;
	private Point[] firstPoints;
	public final int antialiasing;

	protected MultiPixelGrid(double width, double height, Point center, Resolution resolution, Orientation orientation,
		int antialiasing) {
		super(width, height, center, resolution, orientation);
		this.antialiasing = antialiasing;
		this.subResolution = new Resolution(antialiasing, antialiasing);
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
			points[i++] = pixel.representation;
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
