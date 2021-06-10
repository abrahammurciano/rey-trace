package scene.camera;

import java.util.Iterator;
import primitives.Point;
import rendering.Resolution;

public class SinglePixelGrid extends PixelGrid<Point> {

	protected SinglePixelGrid(double width, double height, Point center, Resolution resolution,
		Orientation orientation) {
		super(width, height, center, resolution, orientation);
	}

	@Override
	public Iterator<Pixel<Point>> iterator() {
		return new PixelGridIterator<>(this, topLeft, Point::add);
	}

}
