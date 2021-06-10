package geometries;

import primitives.LineSegment;
import primitives.Point;

class BoundingBox {

	static final BoundingBox INFINITE = new BoundingBox(Point.NEGATIVE_INFINITY, Point.POSITIVE_INFINITY);
	static final BoundingBox EMPTY = new BoundingBox(Point.POSITIVE_INFINITY, Point.NEGATIVE_INFINITY);

	BoundingBox(Point min, Point max) {
		// TODO: implement
	}

	/**
	 * Create a bounding box containing only a single {@link Point}.
	 *
	 * @param point The only point contained in the bounding box.
	 */
	BoundingBox(Point point) {
		this(point, point);
	}

	boolean intersects(LineSegment line) {
		// TODO: implement
		return false;
	}

	BoundingBox union(BoundingBox border) {
		// TODO: implement
		return null;
	}

	double surfaceArea(BoundingBox border) {
		// TODO: implement
		return 0;
	}

	boolean isFinite() {
		// TODO: implement
		return false;
	}
}
