package geometries;

import java.util.function.ToDoubleFunction;
import primitives.LineSegment;
import primitives.Point;
import primitives.Triple;

/**
 * This class represents an axis aligned cuboid which bounds some Boundable in three dimensional space.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
class BoundingBox {

	/** Covers the entirity of 3D space. */
	static final BoundingBox INFINITE = new BoundingBox(Point.NEGATIVE_INFINITY, Point.POSITIVE_INFINITY, null);
	/**
	 * Covers absolutely no space, and calculating the union of this box with any other will always return the other
	 * box.
	 */
	static final BoundingBox EMPTY = new BoundingBox(Point.POSITIVE_INFINITY, Point.NEGATIVE_INFINITY, null);

	private final Point min;
	private final Point max;
	private final double SA;

	/**
	 * Construct a finite bounding box described by the axis aligned cuboid between the two given points.
	 *
	 * @param min The {@link Point} in the bounding box with minimal x, y, and z values.
	 * @param max The {@link Point} in the bounding box with maximal x, y, and z values.
	 *
	 * @throws IllegalArgumentException if min has any value greater than the respective value of max.
	 */
	BoundingBox(Point min, Point max) {
		this(min, max, null);
		if (min.x > max.x || min.y > max.y || min.z > max.z) {
			throw new IllegalArgumentException(
				"Error: The minimum point of a bounding box cannot have a value greater than the respective value of the maximum point.");
		}
	}

	private BoundingBox(Point min, Point max, Object __) {
		this.min = min;
		this.max = max;
		this.SA = calcSurfaceArea();
	}

	/**
	 * Create a bounding box containing only a single {@link Point}.
	 *
	 * @param point The only point contained in the bounding box.
	 */
	BoundingBox(Point point) {
		this(point, point);
	}

	/**
	 * Determines if a {@link LineSegment} passes through this bounding box
	 *
	 * @param line The {@link LineSegment}
	 * @return if the line intersects
	 */
	boolean intersects(LineSegment line) {
		return (intersectsParallelRectangles(line, p -> p.x, p -> p.y, p -> p.z))
			|| (intersectsParallelRectangles(line, p -> p.y, p -> p.x, p -> p.z))
			|| (intersectsParallelRectangles(line, p -> p.z, p -> p.x, p -> p.y));
	}

	/**
	 * Form a new bounding box that exactly encompases this box and the parameter.
	 *
	 * @param border The other box that must be included
	 * @return The new BoundingBox
	 */
	BoundingBox union(BoundingBox border) {
		return new BoundingBox(min(this.min, border.min), max(this.max, border.max));
	}

	/**
	 * Get surface area of the box.
	 *
	 * @return The surface area of the box.
	 */
	double surfaceArea() {
		return SA;
	}

	/**
	 * Determines if a bounding box is finite
	 *
	 * @return true if the box is finite
	 */
	boolean isFinite() {
		return min.isFinite() && max.isFinite();
	}


	private Point min(Point p, Point q) {
		return p.transform(Math::min, q, Point::new);
	}

	private Point max(Point p, Point q) {
		return p.transform(Math::max, q, Point::new);
	}

	private double calcSurfaceArea() {
		double xLength = max.x - min.x;
		double yLength = max.y - min.y;
		double zLength = max.z - min.z;
		return 2 * (xLength * yLength + xLength * zLength + yLength * zLength);
	}

	/**
	 * Distance along the {@link LineSegment} to the plane that contains param fixed.
	 *
	 * @param line          The line
	 * @param fixed         The fixed point in the plane
	 * @param getCoordinate getter for x,y or z
	 * @return distance
	 */
	private boolean intersectsRectangle(LineSegment line, double fixed, ToDoubleFunction<Triple> getCoordinate,
		ToDoubleFunction<Triple> free1, ToDoubleFunction<Triple> free2) {
		double distance =
			(fixed - getCoordinate.applyAsDouble(line.start)) / getCoordinate.applyAsDouble(line.direction);
		Point on = line.travel(distance);
		if (on == null) {
			return false;
		}
		return inRange(on, free1, free2);
	}

	// 🤮
	private boolean inRange(Point query, ToDoubleFunction<Triple> free1, ToDoubleFunction<Triple> free2) {
		double query1 = free1.applyAsDouble(query);
		double query2 = free2.applyAsDouble(query);
		double min1 = free1.applyAsDouble(min);
		double min2 = free2.applyAsDouble(min);
		double max1 = free1.applyAsDouble(max);
		double max2 = free2.applyAsDouble(max);
		return (min1 < query1 && query1 < max1) && (min2 < query2 && query2 < max2);
	}

	private boolean intersectsParallelRectangles(LineSegment line, ToDoubleFunction<Triple> fixed,
		ToDoubleFunction<Triple> free1, ToDoubleFunction<Triple> free2) {
		return intersectsRectangle(line, fixed.applyAsDouble(min), fixed, free1, free2)
			|| intersectsRectangle(line, fixed.applyAsDouble(max), fixed, free1, free2);
	}

}
