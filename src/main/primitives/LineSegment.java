package primitives;

import math.compare.DoubleCompare;

/**
 * A straight line segment between two points.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class LineSegment {
	/** The {@link Point} at which the ray starts. */
	public final Point start;
	/** The {@link NormalizedVector} which the ray is pointing towards. */
	public final NormalizedVector direction;
	/** The square of the distance between the source and the light source. */
	public final double squareLength;

	/**
	 * Construct a line segment between start and end.
	 *
	 * @param start The {@link Point} where the line segment starts.
	 * @param end   The {@link Point} where the line segment ends.
	 */
	public LineSegment(Point start, Point end) {
		this(start, start.vectorTo(end).normalized(), start.squareDistance(end));
	}

	/**
	 * Construct a line segment starting at start, heading in the given direction, with length equal to the square root
	 * of {@code squareLength} (which may be infinity).
	 *
	 * @param start        The {@link Point} where the line segment starts.
	 * @param direction    The direction from the start to the end of the line segment.
	 * @param squareLength The square of the length of the line segment (may be infinity).
	 */
	public LineSegment(Point start, NormalizedVector direction, double squareLength) {
		this.start = start;
		this.direction = direction;
		this.squareLength = squareLength;
	}

	/**
	 * Calculate the point along the line segment after traveling "distance" units in the line segment's direction. If
	 * the given distance squared is greater than {@code squareDistance} then null is returned.
	 *
	 * @param distance The distance to travel in the line segment's direction.
	 * @return The point on the line segment after travelling distance units, or null if distance is longer than the
	 *         line segment.
	 */
	public Point travel(double distance) {
		return withinDistance(distance) ? start.add(direction.scale(distance)) : null;
	}

	/**
	 * Returns whether or not some given distance traveled from the start along the direction is within the length of
	 * the line segment.
	 *
	 * @param distance The distance traveled from the start along the direction.
	 * @return whether or not the distance squared is less than squareDistance.
	 */
	protected boolean withinDistance(double distance) {
		return DoubleCompare.gt(distance, 0) && distance * distance < squareLength;
	}
}
