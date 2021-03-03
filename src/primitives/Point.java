package primitives;

import java.util.Arrays;

public class Point {
	private Coordinate[] coordinates;

	public static final Point ORIGIN = new Point(0, 0, 0);

	public Point(Coordinate x, Coordinate y, Coordinate z) {
		coordinates = new Coordinate[] {x, y, z};
	}

	public Point(double x, double y, double z) {
		this(new Coordinate(x), new Coordinate(y), new Coordinate(z));
	}

	public Coordinate getCoordinate(int index) {
		return coordinates[index];
	}

	public Point transform(CoordinateTransformation transformation, Point auxiliary) {
		return new Point(transformation.transform(getCoordinate(0), auxiliary.getCoordinate(0)),
				transformation.transform(getCoordinate(1), auxiliary.getCoordinate(1)),
				transformation.transform(getCoordinate(2), auxiliary.getCoordinate(2)));
	}

	public Point transform(CoordinateTransformation transformation) {
		return transform(transformation, ORIGIN);
	}

	public Point add(Vector v) {
		return transform((base, aux) -> base.add(aux), v.getHead());
	}

	public Vector vectorTo(Point target) {
		return new Vector(transform((base, aux) -> aux.subtract(base), target));
	}

	public double distance(Point target) {
		return Math.sqrt(squareDistance(target));
	}

	public double squareDistance(Point target) {
		// Construct a point whose coordinates are the squares of the differences of the coordinates
		// of the two points.
		Point squarePoint = transform((base, aux) -> {
			Coordinate diff = aux.subtract(base);
			return diff.multiply(diff);
		}, target);
		// Sum the coordinates of the square point.
		return squarePoint.sum();
	}

	public double sum() {
		return Arrays.stream(coordinates).reduce(Coordinate::add).get().getValue();
	}

	@Override
	public boolean equals(Object p) {
		if (this == p) {
			return true;
		}
		if (p == null || getClass() != p.getClass()) {
			return false;
		}
		return Arrays.equals(this.coordinates, ((Point) p).coordinates);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(coordinates);
	}

	@Override
	public String toString() {
		return "(" + getCoordinate(0) + ", " + getCoordinate(1) + ", " + getCoordinate(2) + ")";
	}
}
