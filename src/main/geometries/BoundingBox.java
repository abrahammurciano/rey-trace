package geometries;

import primitives.LineSegment;
import primitives.Point;

class BoundingBox {

	public static final BoundingBox INFINITE = new BoundingBox(Point.NEGATIVE_INFINITY, Point.POSITIVE_INFINITY);
	public static final BoundingBox EMPTY = new BoundingBox(Point.POSITIVE_INFINITY, Point.NEGATIVE_INFINITY);

	BoundingBox(Point min, Point max) {
		// TODO: implement
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
