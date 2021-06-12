package scene.camera;

import primitives.NormalizedVector;
import primitives.Point;
import rendering.Resolution;

/**
 * A settings class to simplify the construction of {@link Camera} objects.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class CameraSettings {
	private Point position;
	private NormalizedVector front;
	private NormalizedVector up;
	private double width;
	private double height;
	private double distance;
	private Resolution resolution;
	private double sensorSize;
	private int sensorPixels;
	private int antialiasing;

	/**
	 * Default constructor for CameraSettings.
	 */
	public CameraSettings() {
		position = Point.ORIGIN;
		front = NormalizedVector.I;
		up = NormalizedVector.K;
		width = 19.2;
		height = 10.8;
		distance = 10;
		resolution = new Resolution("1920x1080");
		sensorSize = 1;
		sensorPixels = 1;
		antialiasing = 3;
	}

	/**
	 * Construct a camera settings with the values of a given {@link Camera}.
	 *
	 * @param camera The camera to take the values from.
	 */
	public CameraSettings(Camera camera) {
		this();
		position(camera.position());
		front(camera.orientation.front);
		up(camera.orientation.up);
		width(camera.viewPlane.width);
		height(camera.viewPlane.height);
		distance(camera.distance);
		resolution(camera.resolution());
		sensorSize(camera.sensor.height);
		sensorPixels(camera.sensor.resolution.x);
		antialiasing(camera.viewPlane.subPixels);
	}

	/**
	 * Specify the position where the {@link Camera} will be placed. Default is the origin.
	 *
	 * @param position The {@link Point} where the {@link Camera} will be placed.
	 * @return This {@link CameraSettings}.
	 */
	public CameraSettings position(Point position) {
		this.position = position;
		return this;
	}

	/**
	 * Get the position where the {@link Camera} will be placed.
	 *
	 * @return The position where the {@link Camera} will be placed.
	 */
	public Point position() {
		return position;
	}

	/**
	 * Specify the direction the {@link Camera} will be facing. Default is the positive y-axis.
	 *
	 * @param front The direction the {@link Camera} will be facing.
	 * @return This {@link CameraSettings}.
	 */
	public CameraSettings front(NormalizedVector front) {
		this.front = front;
		return this;
	}

	/**
	 * Get the direction the {@link Camera} will be facing.
	 *
	 * @return The direction the {@link Camera} will be facing.
	 */
	public NormalizedVector front() {
		return front;
	}

	/**
	 * Specify the direction the {@link Camera} will consider "up". Default is the positive z-axis.
	 *
	 * @param up The direction to consider "up".
	 * @return This {@link CameraSettings}.
	 */
	public CameraSettings up(NormalizedVector up) {
		this.up = up;
		return this;
	}

	/**
	 * Get the direction the {@link Camera} will consider "up".
	 *
	 * @return The direction the {@link Camera} will consider "up".
	 */
	public NormalizedVector up() {
		return up;
	}

	/**
	 * Specify the width of the view plane. Default is 19.2 units.
	 *
	 * @param width The width of the view plane.
	 * @return This {@link CameraSettings}.
	 */
	public CameraSettings width(double width) {
		this.width = width;
		return this;
	}

	/**
	 * Get the width of the view plane.
	 *
	 * @return This {@link CameraSettings}.
	 */
	public double width() {
		return width;
	}

	/**
	 * Specify the height of the view plane. Default is 10.8 units.
	 *
	 * @param height The height of the view plane.
	 * @return This {@link CameraSettings}.
	 */
	public CameraSettings height(double height) {
		this.height = height;
		return this;
	}

	/**
	 * Get the height of the view plane.
	 *
	 * @return The height of the view plane.
	 */
	public double height() {
		return height;
	}

	/**
	 * Specify the width and height of the view plane. This is an alternative to calling {@link #width(double)} and
	 * {@link #height(double)} separately.
	 *
	 * @param width  The width of the view plane.
	 * @param height The height of the view plane.
	 * @return This {@link CameraSettings}.
	 */
	public CameraSettings dimensions(double width, double height) {
		return width(width).height(height);
	}

	/**
	 * Specify the distance between the {@link Camera} and the view plane. Default is 10 units.
	 *
	 * @param distance The distance between the {@link Camera} and the view plane.
	 * @return This {@link CameraSettings}.
	 */
	public CameraSettings distance(double distance) {
		this.distance = distance;
		return this;
	}

	/**
	 * Get the distance between the {@link Camera} and the view plane.
	 *
	 * @return The distance between the {@link Camera} and the view plane.
	 */
	public double distance() {
		return distance;
	}

	/**
	 * Specify the resolution of the {@link Camera}. Default is 1080p.
	 *
	 * @param resolution The resolution of the {@link Camera}.
	 * @return This {@link CameraSettings}.
	 */
	public CameraSettings resolution(Resolution resolution) {
		this.resolution = resolution;
		return this;
	}

	/**
	 * Specify the resolution of the {@link Camera}. Default is 1080p. This is an alternative to calling
	 * {@link #resolution(Resolution)}.
	 *
	 * @param resolution The resolution of the {@link Camera}.
	 * @return This {@link CameraSettings}.
	 */
	public CameraSettings resolution(String resolution) {
		return resolution(new Resolution(resolution));
	}

	/**
	 * Get the resolution of the {@link Camera}.
	 *
	 * @return The resolution.
	 */
	public Resolution resolution() {
		return resolution;
	}

	/**
	 * Specify the size of the {@link Camera}'s sensor. The larger the size, the shorter the focal range.
	 *
	 * @param sensorSize The size for the sensor.
	 * @return This {@link CameraSettings}.
	 */
	public CameraSettings sensorSize(double sensorSize) {
		this.sensorSize = sensorSize;
		return this;
	}

	/**
	 * Get the size of the sensor of the {@link Camera}.
	 *
	 * @return The sensor size.
	 */
	public double sensorSize() {
		return sensorSize;
	}

	/**
	 * Specify the number of pixels along the x and y axes of the {@link Camera}'s sensor.
	 *
	 * @param sensorPixels The number of pixels for the sensor.
	 * @return This {@link CameraSettings}.
	 */
	public CameraSettings sensorPixels(int sensorPixels) {
		this.sensorPixels = sensorPixels;
		return this;
	}

	/**
	 * Get the number of pixels along the x and y axes of the {@link Camera}'s sensor.
	 *
	 * @return The number of pixels for the sensor's x and y axes.
	 */
	public int sensorPixels() {
		return sensorPixels;
	}

	/**
	 * Specify the level of antialiasing to be used by the camera. 1 means no antialiasing, 2 is moderate, 3 gives in
	 * decent results with acceptable computation cost.
	 *
	 * @param antialiasing The level of antialiasing to be used by the camera.
	 * @return This {@link CameraSettings}.
	 */
	public CameraSettings antialiasing(int antialiasing) {
		if (antialiasing < 1) {
			throw new IllegalArgumentException("Error: Anti aliasing level must be a positive integer.");
		}
		this.antialiasing = antialiasing;
		return this;
	}

	/**
	 * Get the antialiasing level which will be used by the camera.
	 *
	 * @return The antialiasing level.
	 */
	public int antialiasing() {
		return antialiasing;
	}

}
