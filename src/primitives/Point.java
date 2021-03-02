package primitives;

import java.util.Arrays;

public class Point {
	private Coordinate[] coordinates;

	public static final Point ORIGIN = new Point(0, 0, 0);

	public Point(Coordinate x, Coordinate y, Coordinate z) {
		coordinates = new Coordinate[3];
		setCoordinate(0, x);
		setCoordinate(1, y);
		setCoordinate(2, z);
	}

	public Point(double x, double y, double z) {
		this(new Coordinate(x), new Coordinate(y), new Coordinate(z));
	}

	public Coordinate getCoordinate(int index) {
		return coordinates[index];
	}

	private void setCoordinate(int index, Coordinate coordinate) {
		coordinates[index] = coordinate;
	}

	public Point add(Vector v) {
		// TODO: implement
		return null;
	}

	public Vector vectorTo(Point target) {
		// TODO: implement
		return null;
	}

	public double distance(Point target) {
		return Math.sqrt(squareDistance(target));
	}

	public double squareDistance(Point target) {
		// TODO: implement
		return null;
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
