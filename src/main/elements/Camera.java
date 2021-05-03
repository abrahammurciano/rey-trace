package elements;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Iterator;

import primitives.Matrix3x3;
import primitives.NormalizedVector;
import util.Resolution;

/**
 * The camera represents the point of view of the rendered image.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Camera implements Iterable<Ray> {
	private final Point location;
	private final Orientation orientation;
	private final ViewPlane view;
	private final double width, height, distance;

	public Camera(Point location, NormalizedVector front, NormalizedVector up, double width, double height, double distance, Resolution resolution) {
		this.location = location;
		this.orientation = new Orientation(front, up);
		this.width = width;
		this.height = height;
		this.distance = distance;
		this.view = new ViewPlane(width, height, location.add(orientation.front.scale(distance)), resolution, orientation);
	}

	public Camera moveAndRotate(Vector shift, NormalizedVector newFront, double twistAngle) {
		Matrix3x3 rotate = Matrix3x3.getRotation(orientation.front, newFront);
		Matrix3x3 twist = Matrix3x3.getRotation(newFront, twistAngle);
		Matrix3x3 rotateAndTwist = rotate.multiply(twist); // this may be backwards. matrix multiplication is hard
		return new Camera(location.add(shift), rotateAndTwist.multiply(orientation.front).normalized(), rotateAndTwist.multiply(orientation.up).normalized(), width, height, distance, view.resolution);
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
			return new Ray(source, source.vectorTo(p));
		}

	}

}
