package camera;

import primitives.Point;
import primitives.Ray;
import java.util.Iterator;

/**
 * The camera represents the point of view of the rendered image. Iterating over {@link Camera} will yield {@link Ray}s
 * starting from left to right, then top to bottom.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Camera implements Iterable<Ray> {
	public final Point location;
	private final Orientation orientation;
	private final ViewPlane view;

	Camera(CameraBuilder builder) {
		this.location = builder.location();
		this.orientation = new Orientation(builder.front(), builder.up());
		this.view = new ViewPlane(builder.width(), builder.height(),
			builder.location().add(orientation.front.scale(builder.distance())), builder.resolution(), orientation);
	}

	/**
	 * Calculate the {@link Ray} to the center of the pixel at the given column and row.
	 *
	 * @param col The index of the column.
	 * @param row The index of the row.
	 * @return The ray to the center of the pixel.
	 */
	public Ray point(int col, int row) {
		return new Ray(location, location.vectorTo(view.point(col, row)).normalized());
	}

	@Override
	public Iterator<Ray> iterator() {
		return new CameraIterator(this);
	}

	public class CameraIterator implements Iterator<Ray> {

		private final Point source;
		private final Iterator<Point> viewPlaneIterator;

		public CameraIterator(Camera camera) {
			this.viewPlaneIterator = camera.view.iterator();
			this.source = camera.location;
		}

		@Override
		public boolean hasNext() {
			return viewPlaneIterator.hasNext();
		}

		@Override
		public Ray next() {
			Point p = viewPlaneIterator.next();
			return new Ray(source, source.vectorTo(p).normalized());
		}

	}

}
