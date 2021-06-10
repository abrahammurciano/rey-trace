package scene.camera;

import primitives.Point;
import primitives.Ray;
import primitives.NonZeroVector;
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
public class Camera implements Iterable<Pixel<Ray[]>> {
	/** The {@link PixelGrid} where all rays are shot from. */
	public final SinglePixelGrid sensor;
	/** The {@link Orientation} in which the camera is facing. */
	final Orientation orientation;
	/** The {@link PixelGrid} the camera is to shoot rays through. */
	final MultiPixelGrid viewPlane;
	/** The distance between the {@link Camera} and the {@link PixelGrid}. */
	final double distance;

	/**
	 * Constructs a camera from the given {@link CameraSettings}.
	 *
	 * @param settings The {@link CameraSettings} containing the data necessary to create the camera.
	 */
	public Camera(CameraSettings settings) {
		this.orientation = new Orientation(settings.front(), settings.up());
		this.sensor = new SinglePixelGrid(settings.sensorSize(), settings.sensorSize(), settings.position(),
			new Resolution(settings.sensorPixels(), settings.sensorPixels()), orientation);
		this.distance = settings.distance();
		this.viewPlane = new MultiPixelGrid(settings.width(), settings.height(),
			settings.position().add(orientation.front.scale(distance)), settings.resolution(), orientation,
			settings.antialiasing());
	}

	/**
	 * Default constructor for camera.
	 */
	public Camera() {
		this(new CameraSettings());
	}

	/**
	 * Gets the resolution of the {@link Camera}.
	 *
	 * @return The resolution of the {@link Camera}.
	 */
	public Resolution resolution() {
		return viewPlane.resolution;
	}

	/**
	 * Gets the point where the camera is located.
	 *
	 * @return the {@link Point} where the camera is located.
	 */
	public Point position() {
		return sensor.center;
	}

	/**
	 * Create a new camera which is shifted by the given {@link NonZeroVector}.
	 *
	 * @param offset The {@link NonZeroVector} by which to shift the camera.
	 * @return A new {@link Camera} shifted by the given {@link NonZeroVector}.
	 */
	public Camera shift(NonZeroVector offset) {
		return new Camera(new CameraSettings(this).position(sensor.center.add(offset)));
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
	public Iterator<Pixel<Ray[]>> iterator() {
		return new CameraIterator(this);
	}

}
