package geometries;

import java.util.function.Function;
import primitives.LineSegment;
import primitives.Point;
import primitives.Triple;

class BoundingBox {

	public static final BoundingBox INFINITE = new BoundingBox(Point.NEGATIVE_INFINITY, Point.POSITIVE_INFINITY);
	public static final BoundingBox EMPTY = new BoundingBox(Point.POSITIVE_INFINITY, Point.NEGATIVE_INFINITY);

	public final Point min;
	public final Point max;
	private final double SA;

	public BoundingBox(Point min, Point max) {
		this(assertFinite(min), assertFinite(max), null);
	}

	private BoundingBox(Point min, Point max, Object __) {
		this.min = min;
		this.max = max;
		this.SA = calcSurfaceArea();
	}

	private static Point assertFinite(Point p) {
		if (Double.isFinite(p.x) && Double.isFinite(p.y) && Double.isFinite(p.z))
			return p;
		else
			return Point.POSITIVE_INFINITY;
	}

	/**
	 * Determines if a {@link LineSegment} passes through this bounding box
	 * 
	 * @param line The {@link LineSegment}
	 * @return if the line intersects
	 */
	boolean intersects(LineSegment line) {
		return (intersectsParallelRectangles(line,(p)->p.x, (p)->p.y, (p)->p.z))
			|| (intersectsParallelRectangles(line,(p)->p.y, (p)->p.x, (p)->p.z))
			|| (intersectsParallelRectangles(line,(p)->p.z, (p)->p.x, (p)->p.y));
	}

	/**
	 * Form a new bounding box that exactly encompases this box and the parameter.
	 * 
	 * @param border The other box that must be included
	 * @return The new BoundingBox
	 */
	BoundingBox union(BoundingBox border) {
		return new BoundingBox(min.minimumest(border.min), max.maximumest(border.max));
	}

	/**
	 * Calculate surface area of the box 
	 *
	 * @return surface area
	 */
	double surfaceArea() {
		return SA;
	}

	private double calcSurfaceArea() {
		double xLength = Math.abs(min.x - max.x);
		double yLength = Math.abs(min.y - max.y);
		double zLength = Math.abs(min.z - max.z);
		return 2 * (xLength * yLength + xLength * zLength + yLength * zLength);
	}

	/**
	 * Determines if a bounding box is finite
	 * @return true if the box is finite
	 */
	boolean isFinite() {
		return assertFinite(this.min) == Point.POSITIVE_INFINITY
			&& assertFinite(this.max) == Point.POSITIVE_INFINITY;
	}

	/**
	 * Distance along the {@link LineSegment} to the plane that contains param fixed. 
	 *
	 * @param line The line
	 * @param fixed The fixed point in the plane
	 * @param getCoordinate getter for x,y or z
	 * @return distance
	 */
	private boolean intersectsRectangle(LineSegment line, double fixed, Function<Triple, Double> getCoordinate, Function<Triple, Double> free1, Function<Triple, Double> free2) {
		double distance = (fixed - getCoordinate.apply(line.start)) / getCoordinate.apply(line.direction);
		Point on = line.travel(distance);
		if (on == null) {
			return false;
		}
		return inRange(on, free1, free2);
	}

	// 🤮
	private boolean inRange(Point query, Function<Triple, Double> free1, Function<Triple, Double> free2) {
		double query1 = free1.apply(query);
		double query2 = free2.apply(query);
		double min1 = free1.apply(min);
		double min2 = free2.apply(min);
		double max1 = free1.apply(max);
		double max2= free2.apply(max);
		if ((min1 < query1 && query1 < max1) && (min2 < query2 && query2 < max2)) {
			return true;
		}
		return false;
	}

	private boolean intersectsParallelRectangles(LineSegment line, Function<Triple, Double> fixed,Function<Triple, Double> free1,Function<Triple, Double> free2) {
		return intersectsRectangle(line, fixed.apply(min), fixed, free1, free2)
			|| intersectsRectangle(line, fixed.apply(max), fixed, free1, free2);
	}


}
