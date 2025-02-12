package scene.camera;

import math.compare.DoubleCompare;
import primitives.NormalizedVector;

/**
 * This class represents some orientation in three-dimensional space
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
class Orientation {
	/** The vector pointing front relative to this orientation. */
	final NormalizedVector front;
	/** The vector pointing up relative to this orientation. */
	final NormalizedVector up;
	/** The vector pointing right relative to this orientation. */
	final NormalizedVector right;

	/**
	 * Constructs an orientation given the front and up vectors.
	 *
	 * @param front The vector pointing forward with respect to the desired orientation.
	 * @param up    The vector pointing up with respect to the desired orientation.
	 * @throws IllegalArgumentException if front and up are not perpendicular.
	 */
	Orientation(NormalizedVector front, NormalizedVector up) {
		if (DoubleCompare.neq(front.dot(up), 0)) {
			throw new IllegalArgumentException("Front and up vectors must be perpendicular.");
		}
		this.front = front;
		this.up = up;
		this.right = front.cross(up).normalized();
	}
}
