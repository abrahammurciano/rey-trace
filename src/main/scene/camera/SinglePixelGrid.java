package scene.camera;

import java.util.Iterator;
import primitives.Point;
import rendering.Resolution;


/**
 * A {@link PixelGrid} whose pixels store their center {@link Point}.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
class SinglePixelGrid extends PixelGrid<Point> {

	SinglePixelGrid(double width, double height, Point center, Resolution resolution, Orientation orientation) {
		super(width, height, center, resolution, orientation);
	}

	@Override
	public Iterator<Pixel<Point>> iterator() {
		return new PixelGridIterator<>(this, topLeft, Point::add);
	}

}
