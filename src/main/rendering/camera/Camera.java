package rendering.camera;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import rendering.Resolution;
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
public class Camera implements Iterable<Pixel> {
	/** The location at which the camera located. */
	public final Point location;
	/** The {@link Orientation} in which the camera is facing. */
	final Orientation orientation;
	/** The {@link ViewPlane} the camera is to shoot rays through. */
	final ViewPlane view;
	/** The distance between the {@link Camera} and the {@link ViewPlane}. */
	final double distance;

	/**
	 * Constructs a camera from the given {@link CameraSettings}.
	 *
	 * @param settings The {@link CameraSettings} containing the data necessary to create the camera.
	 */
	public Camera(CameraSettings settings) {
		this.location = settings.location();
		this.orientation = new Orientation(settings.front(), settings.up());
		this.distance = settings.distance();
		this.view = new ViewPlane(settings.width(), settings.height(),
			settings.location().add(orientation.front.scale(distance)), settings.resolution(), orientation);
	}

	/**
	 * Gets the resolution of the {@link Camera}.
	 *
	 * @return The resolution of the {@link Camera}.
	 */
	public Resolution resolution() {
		return view.resolution;
	}

	/**
	 * Create a new camera which is shifted by the given {@link Vector}.
	 *
	 * @param offset The {@link Vector} by which to shift the camera.
	 * @return A new {@link Camera} shifted by the given {@link Vector}.
	 */
	public Camera shift(Vector offset) {
		return new Camera(new CameraSettings(this).location(location.add(offset)));
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
		return new Camera(new CameraSettings(this).front(m.multiply(orientation.front).normalized())
			.up(m.multiply(orientation.up).normalized()));
	}

	@Override
	public Iterator<Pixel> iterator() {
		return iterator(1);
	}

	/**
	 * Get an iterator which iterates over the pixels of the camera.
	 *
	 * @param subPixels The number of sub pixels in each dimension for each pixel.
	 * @return An iterator which iterates over the pixels of the camera.
	 */
	public Iterator<Pixel> iterator(int subPixels) {
		return new CameraIterator(this, subPixels);
	}

}
