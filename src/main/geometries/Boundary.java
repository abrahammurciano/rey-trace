package geometries;

import primitives.LineSegment;
import primitives.Point;

/**
 * This class represents an axis aligned cuboid which bounds some Intersectible in three dimensional space.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
class Boundary implements Comparable<Boundary> {

	/** Covers the entirity of 3D space. */
	static final Boundary INFINITE = new InfiniteBoundary();
	/**
	 * Covers absolutely no space, and calculating the union of this box with any other will always return the other
	 * box.
	 */
	static final Boundary EMPTY = new EmptyBoundary();

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
	Boundary(Point min, Point max) {
		this(min, max, null);
		if (min.x > max.x || min.y > max.y || min.z > max.z) {
			throw new IllegalArgumentException(
				"Error: The minimum point of a bounding box cannot have a value greater than the respective value of the maximum point.");
		}
		if (!min.isFinite() || !max.isFinite()) {
			throw new IllegalArgumentException("Error: You cannot create infinite bounding boxes.");
		}
	}

	/**
	 * Create a bounding box containing only a single {@link Point}.
	 *
	 * @param point The only point contained in the bounding box.
	 */
	Boundary(Point point) {
		this(point, point);
	}

	/**
	 * Private constructor which doesn't check that min is always less than max. This is used to create the empty
	 * bounding box.
	 *
	 * @param min The point to consider the minimum.
	 * @param max The point to consider the maximum.
	 * @param __  A dummy parameter to distinguish between this constructor and {@link #BoundingBox(Point, Point)}.
	 */
	private Boundary(Point min, Point max, Object __) {
		this.min = min;
		this.max = max;
		this.SA = calcSurfaceArea();
	}

	/**
	 * Determines if a {@link LineSegment} passes through this bounding box
	 *
	 * @param line The {@link LineSegment}
	 * @return if the line intersects
	 */
	boolean intersects(LineSegment line) {
		double tmin, tmax;

		double tx1 = (min.x - line.start.x) * line.inverse.x;
		double tx2 = (max.x - line.start.x) * line.inverse.x;

		tmin = Math.min(tx1, tx2);
		tmax = Math.max(tx1, tx2);

		double ty1 = (min.y - line.start.y) * line.inverse.y;
		double ty2 = (max.y - line.start.y) * line.inverse.y;

		tmin = Math.max(tmin, Math.min(ty1, ty2));
		tmax = Math.min(tmax, Math.max(ty1, ty2));

		double tz1 = (min.z - line.start.z) * line.inverse.z;
		double tz2 = (max.z - line.start.z) * line.inverse.z;

		tmin = Math.max(tmin, Math.min(tz1, tz2));
		tmax = Math.min(tmax, Math.max(tz1, tz2));

		// tmax must be greater than tmin for the line to intersect the box.
		// but if tmax is less than 0 then the entire line is 'behind' the box and then no intersection.
		// if tmin is less than 0 than than the line intersects (since tmax is greater than 0)
		// OR if tmin^2 < line.squareLength than the (positive) tmin is within the length of the line and therefore in the box
		return tmax > 0 && tmax >= tmin && (tmin < 0 || tmin * tmin < line.squareLength);
	}

	/**
	 * Form a new bounding box that exactly encompases this box and the parameter.
	 *
	 * @param other The other box that must be included
	 * @return The new BoundingBox
	 */
	Boundary union(Boundary other) {
		if (!other.isFinite()) {
			return other;
		}
		return new Boundary(min(this.min, other.min), max(this.max, other.max));
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


	private static Point min(Point p, Point q) {
		return p.transform(Math::min, q, Point::new);
	}

	private static Point max(Point p, Point q) {
		return p.transform(Math::max, q, Point::new);
	}

	protected double calcSurfaceArea() {
		double xLength = max.x - min.x;
		double yLength = max.y - min.y;
		double zLength = max.z - min.z;
		return 2 * (xLength * yLength + xLength * zLength + yLength * zLength);
	}

	/**
	 * Compares the surface area of this bounding box against another bounding box.
	 *
	 * @param other The other BoundingBox to compare against
	 * @return a negative integer if this box has a smaller surface area than the other bounding box, 0 if they are
	 *         equal, or a positive integer if it is larger.
	 */
	@Override
	public int compareTo(Boundary other) {
		return Double.compare(SA, other.SA);
	}

	/**
	 * Two bounding boxes are considered equal if their surface areas are the same.
	 *
	 * @param o The other object to compare.
	 * @return true if the two boxes have an equal surface area.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Boundary)) {
			return false;
		}
		return SA == ((Boundary) o).SA;
	}

	@Override
	public int hashCode() {
		return Double.hashCode(SA);
	}

	private static class InfiniteBoundary extends Boundary {
		InfiniteBoundary() {
			super(Point.NEGATIVE_INFINITY, Point.POSITIVE_INFINITY, null);
		}

		@Override
		boolean intersects(LineSegment line) {
			return true;
		}

		@Override
		Boundary union(Boundary other) {
			return this;
		}

		@Override
		protected double calcSurfaceArea() {
			return Double.POSITIVE_INFINITY;
		}

		@Override
		boolean isFinite() {
			return false;
		}
	}

	private static class EmptyBoundary extends Boundary {
		EmptyBoundary() {
			super(Point.POSITIVE_INFINITY, Point.NEGATIVE_INFINITY, null);
		}

		@Override
		boolean intersects(LineSegment line) {
			return false;
		}

		@Override
		Boundary union(Boundary other) {
			return other;
		}

		@Override
		protected double calcSurfaceArea() {
			return Double.NEGATIVE_INFINITY;
		}

		@Override
		boolean isFinite() {
			return true;
		}
	}
}
