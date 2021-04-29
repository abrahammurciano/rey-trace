package camera;

import primitives.NormalizedVector;
import util.DoubleCompare;

/**
 * This class represents some orientation in three-dimensional space
 */
public class Orientation {
	public final NormalizedVector front;
	public final NormalizedVector up;
	public final NormalizedVector right;

	/**
	 * Constructs an orientation given the front and up vectors.
	 *
	 * @param front The vector pointing forward with respect to the desired
	 *              orientation.
	 * @param up    The vector pointing up with respect to the desired orientation.
	 * @throws IllegalArgumentException if front and up are not perpendicular.
	 */
	public Orientation(NormalizedVector front, NormalizedVector up) {
		if (DoubleCompare.neq(front.dot(up), 0)) {
			throw new IllegalArgumentException("Front and up vectors must be perpendicular.");
		}
		this.front = front;
		this.up = up;
		this.right = front.cross(up).normalized();
	}
}
