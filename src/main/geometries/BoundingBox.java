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

	BoundingBox(Point min, Point max) {
		this.min = min;
		this.max = max;
	}

	/**
	 * Determines if a {@link LineSegment} passes through this bounding box
	 * 
	 * @param line The {@link LineSegment}
	 * @return if the line intersects
	 */
	boolean intersects(LineSegment line) {
		if (composed(line,(p)->p.x, (p)->p.y, (p)->p.z)) return true;
		if (composed(line,(p)->p.y, (p)->p.x, (p)->p.z)) return true;
		if (composed(line,(p)->p.z, (p)->p.x, (p)->p.y)) return true;
		return false;
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
		double xLength = Math.abs(min.x - max.x);
		double yLength = Math.abs(min.y - max.y);
		double zLength = Math.abs(min.z - max.z);
		return 2 * (xLength * yLength + xLength * zLength + yLength * zLength);
	}

	boolean isFinite() {
		// TODO: implement. WTF why?
		return false;
	}

	private double intersectHelper(LineSegment line, double fixed, Function<Triple, Double> func) {
		double maybeZero = func.apply(line.direction);
		if (maybeZero == 0) {
			if (fixed == 0) return 0;
			else return -1;
		}
		return (fixed - func.apply(line.start)) / func.apply(line.direction);
	}

	// 🤮🤮🤮🤮🤮🤮🤮🤮🤮🤮🤮
	// this ends up geting the values of min and max up to 24 times, while there are only 6 of them
	// how to do better?
	private boolean inRange(Point query, Function<Triple, Double> func1, Function<Triple, Double> func2) {
		double query1 = func1.apply(query);
		double query2 = func2.apply(query);
		double min1 = func1.apply(min);
		double min2 = func2.apply(min);
		double max1 = func1.apply(max);
		double max2= func2.apply(max);
		if ((min1 < query1 && query1 < max1) && (min2 < query2 && query2 < max2)) {
			return true;
		}
		return false;
	}

	// 🤮🤮🤮
	private boolean composed(LineSegment line, Function<Triple, Double> fixed,Function<Triple, Double> free1,Function<Triple, Double> free2) {
		double tmin = intersectHelper(line, min.x, fixed);
		double tmax = intersectHelper(line, max.x, fixed);
		if (tmin != -1) {
			Point on = line.travel(tmin);
			if (inRange(on, free1, free2)) return true;
		}
		if (tmax != -1) {
			Point on = line.travel(tmax);
			if (inRange(on, free1, free2)) return true;
		}
		return false;
	}


}
