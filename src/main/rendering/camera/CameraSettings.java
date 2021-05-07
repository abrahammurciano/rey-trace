package rendering.camera;

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
	private Point location;
	private NormalizedVector front;
	private NormalizedVector up;
	private double width;
	private double height;
	private double distance;
	private Resolution resolution;

	/**
	 * Default constructor for CameraSettings.
	 */
	public CameraSettings() {
		location = Point.ORIGIN;
		front = NormalizedVector.J;
		up = NormalizedVector.K;
		width = 19.2;
		height = 10.8;
		distance = 10;
		resolution = new Resolution("1920x1080");
	}

	/**
	 * Construct a camera settings with the values of a given {@link Camera}.
	 *
	 * @param camera The camera to take the values from.
	 */
	public CameraSettings(Camera camera) {
		this();
		location(camera.location);
		front(camera.orientation.front);
		up(camera.orientation.up);
		width(camera.view.width);
		height(camera.view.height);
		distance(camera.distance);
		resolution(camera.resolution());
	}

	/**
	 * Specify the location where the {@link Camera} will be placed. Default is the origin.
	 *
	 * @param location The {@link Point} where the {@link Camera} will be placed.
	 * @return This {@link CameraSettings}.
	 */
	public CameraSettings location(Point location) {
		this.location = location;
		return this;
	}

	/**
	 * Get the location where the {@link Camera} will be placed.
	 *
	 * @return The location where the {@link Camera} will be placed.
	 */
	public Point location() {
		return location;
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
	 * @return This {@link CameraSettings}.
	 */
	public Resolution resolution() {
		return resolution;
	}

}