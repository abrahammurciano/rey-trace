package geometries;

import primitives.LineSegment;
import primitives.NormalizedVector;
import primitives.Point;
import primitives.Vector;

/**
 * This class represents an axis aligned cuboid which bounds some Intersectible in three dimensional space.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
class Boundary implements Comparable<Boundary> {

	private enum Intersects {
		NEVER, SOMETIMES, ALWAYS
	}

	/** Covers the entirity of 3D space. */
	static final Boundary INFINITE = new Boundary(null, Intersects.ALWAYS);
	/**
	 * Covers absolutely no space, and calculating the union of this box with any other will always return the other
	 * box.
	 */
	static final Boundary EMPTY = new Boundary(null, Intersects.NEVER);

	private final double surfaceArea;
	private final BasicSphere sphere;
	private final Intersects intersects;

	/**
	 * Construct a finite bounding box described by the axis aligned cuboid between the two given points.
	 *
	 * @param min The {@link Point} in the bounding box with minimal x, y, and z values.
	 * @param max The {@link Point} in the bounding box with maximal x, y, and z values.
	 */
	Boundary(Point min, Point max) {
		this(min, max, Intersects.SOMETIMES);
	}

	/**
	 * Create a bounding box containing only a single {@link Point}.
	 *
	 * @param point The only point contained in the bounding box.
	 */
	Boundary(Point point) {
		this(point, point, Intersects.NEVER);
	}

	/**
	 * Construct the smallest possible boundary containing p1 and p2.
	 *
	 * @param p1         A point on the surface of the boundary.
	 * @param p2         A point on the surface of the boundary.
	 * @param intersects When this boundary intersects with a ray.
	 */
	private Boundary(Point p1, Point p2, Intersects intersects) {
		this(new BasicSphere(p1, p2), intersects);
	}

	/**
	 * Private constructor which doesn't check that min is always less than max. This is used to create the empty
	 * bounding box.
	 *
	 * @param min        The point to consider the minimum.
	 * @param max        The point to consider the maximum.
	 * @param intersects When the bounding box intersects with any ray.
	 */
	private Boundary(BasicSphere sphere, Intersects intersects) {
		this.sphere = sphere;
		this.intersects = intersects;
		if (intersects == Intersects.SOMETIMES) {
			surfaceArea = sphere.surfaceArea();
		} else {
			surfaceArea = intersects == Intersects.ALWAYS ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
		}
	}

	/**
	 * Determines if a {@link LineSegment} passes through this bounding box
	 *
	 * @param line The {@link LineSegment}
	 * @return if the line intersects
	 */
	boolean intersects(LineSegment line) {
		if (intersects == Intersects.SOMETIMES) {
			return !sphere.intersect(line).isEmpty() || sphere.contains(line.start);
		} else {
			return intersects == Intersects.ALWAYS;
		}
	}

	/**
	 * Form a new bounding box that exactly encompases this box and the parameter.
	 *
	 * @param boundary The other box that must be included
	 * @return The new BoundingBox
	 */
	Boundary union(Boundary boundary) {
		if (intersects == Intersects.ALWAYS || boundary.intersects == Intersects.ALWAYS) {
			return INFINITE;
		}
		if (this == EMPTY && boundary == EMPTY) {
			return EMPTY;
		}
		if (this == EMPTY) {
			return boundary;
		}
		if (boundary == EMPTY) {
			return this;
		}
		if (sphere.center.equals(boundary.sphere.center)) {
			return new Boundary(sphere.radiusSquared > boundary.sphere.radiusSquared ? sphere : boundary.sphere,
				Intersects.SOMETIMES);
		}
		NormalizedVector centerToCenter = sphere.center.nonZeroVectorTo(boundary.sphere.center).normalized();
		Point midPoint = sphere.center.midPoint(boundary.sphere.center);
		Vector toThisEdge = centerToCenter.scale(Math.sqrt(sphere.radiusSquared), Vector::new);
		Vector toThatEdge = centerToCenter.scale(Math.sqrt(boundary.sphere.radiusSquared), Vector::new);
		Point thisP1 = sphere.center.subtract(toThisEdge);
		Point thisP2 = sphere.center.add(toThisEdge);
		Point thatP1 = boundary.sphere.center.subtract(toThatEdge);
		Point thatP2 = boundary.sphere.center.add(toThatEdge);
		Point p1 = midPoint.squareDistance(thisP1) > midPoint.squareDistance(thatP1) ? thisP1 : thatP1;
		Point p2 = midPoint.squareDistance(thisP2) > midPoint.squareDistance(thatP2) ? thisP2 : thatP2;
		return new Boundary(p1, p2);
	}

	/**
	 * Get surface area of the box.
	 *
	 * @return The surface area of the box.
	 */
	double surfaceArea() {
		return surfaceArea;
	}

	/**
	 * Determines if a bounding box is finite
	 *
	 * @return true if the box is finite
	 */
	boolean isFinite() {
		return this != INFINITE;
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
		return Double.compare(surfaceArea(), other.surfaceArea());
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
		return surfaceArea() == ((Boundary) o).surfaceArea();
	}

	@Override
	public int hashCode() {
		return Double.hashCode(surfaceArea());
	}


}
