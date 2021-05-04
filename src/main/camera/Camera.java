package camera;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.Iterator;
import math.matrices.Matrix;
import math.matrices.RotationMatrix;

/**
 * The camera represents the point of view of the rendered image. Iterating over {@link Camera} will yield {@link Ray}s
 * starting from left to right, then top to bottom.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Camera implements Iterable<Ray> {
	public final Point location;
	final Orientation orientation;
	final ViewPlane view;
	final double distance;

	Camera(CameraBuilder builder) {
		this.location = builder.location();
		this.orientation = new Orientation(builder.front(), builder.up());
		this.distance = builder.distance();
		this.view = new ViewPlane(builder.width(), builder.height(),
			builder.location().add(orientation.front.scale(distance)), builder.resolution(), orientation);
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

	/**
	 * Create a new camera which is shifted by the given {@link Vector}.
	 *
	 * @param offset The {@link Vector} by which to shift the camera.
	 * @return A new {@link Camera} shifted by the given {@link Vector}.
	 */
	public Camera shift(Vector offset) {
		return new CameraBuilder(this).location(location.add(offset)).build();
	}

	/**
	 * Create a new camera which is a rotation of this one by the given angles {@code pitch}, {@code yaw} and
	 * {@code roll}.
	 *
	 * @param pitch The angle in radians to rotate about the left-right axis.
	 * @param yaw   The angle in radians to rotate about the up-down axis.
	 * @param roll  The angle in radians to rotate about the front-back axis.
	 * @return A new {@link Camera} rotated by the given angles.
	 */
	public Camera rotate(double pitch, double yaw, double roll) {
		Matrix m = new RotationMatrix(pitch, yaw, roll);
		return new CameraBuilder(this).front(m.multiply(orientation.front).normalized())
			.up(m.multiply(orientation.up).normalized()).build();
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
