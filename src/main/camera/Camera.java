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
	// lots of code repetition here...
	// But how to do without needless allocation?
	// Could builder pattern help?
	// public Camera moveAndRotate(Vector shift, NormalizedVector newFront, double twistAngle) {
	// 	Matrix3x3 rotate = Matrix3x3.getRotation(orientation.front, newFront);
	// 	Matrix3x3 twist = Matrix3x3.getRotation(newFront, twistAngle);
	// 	Matrix3x3 rotateAndTwist = twist.multiply(rotate); // this may be backwards. matrix multiplication is hard
	// 	return new Camera(location.add(shift), rotateAndTwist.multiply(orientation.front),
	// 		rotateAndTwist.multiply(orientation.up), width, height, distance, view.resolution);
	// }

	// public Camera rotate(NormalizedVector newFront, double twistAngle) {
	// 	Matrix3x3 rotate = Matrix3x3.getRotation(orientation.front, newFront);
	// 	Matrix3x3 twist = Matrix3x3.getRotation(newFront, twistAngle);
	// 	Matrix3x3 rotateAndTwist = twist.multiply(rotate); // this may be backwards. matrix multiplication is hard
	// 	return new Camera(location, rotateAndTwist.multiply(orientation.front),
	// 		rotateAndTwist.multiply(orientation.up), width, height, distance, view.resolution);
	// }

	// public Camera move(Vector shift) {
	// 	return new Camera(location.add(shift), orientation.front, orientation.up, width, height, distance,
	// 		view.resolution);
	// }

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
