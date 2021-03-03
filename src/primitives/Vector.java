package primitives;

public class Vector {
	private Point head;

	public Vector(double x, double y, double z) {
		this(new Point(x, y, z));
	}

	public Vector(Coordinate x, Coordinate y, Coordinate z) {
		this(new Point(x, y, z));
	}

	public Vector(Point head) {
		if (head.equals(Point.ORIGIN)) {
			throw new IllegalArgumentException("Error: Zero vector is not allowed.");
		}
		this.head = head;
	}

	public Point getHead() {
		return head;
	}

	public Vector transform(CoordinateTransformation transformation, Vector auxiliary) {
		return new Vector(getHead().transform(transformation, auxiliary.getHead()));
	}

	public Vector transform(CoordinateTransformation transformation) {
		return new Vector(getHead().transform(transformation));
	}

	public Vector add(Vector vector) {
		return new Vector(getHead().add(vector));
	}

	public Vector subtract(Vector vector) {
		return add(vector.scale(-1));
	}

	public Vector scale(double factor) {
		return transform((c, __) -> c.multiply(factor));
	}

	public Vector crossProduct(Vector vector) {
		Coordinate[] coordinates = new Coordinate[3];
		for (int i = 0; i < coordinates.length; ++i) {
			coordinates[i] = getHead().getCoordinate(i + 1 % coordinates.length)
					.multiply(vector.getHead().getCoordinate(1 + 2 % coordinates.length))
					.subtract(getHead().getCoordinate(i + 2 % coordinates.length)
							.multiply(vector.getHead().getCoordinate(i + 1 % coordinates.length)));
		}
		return new Vector(coordinates[0], coordinates[1], coordinates[2]);
	}

	public double dotProduct(Vector vector) {
		// Construct a vector whose coordinates are the product of the coordinates of the other two.
		Vector v = transform((base, aux) -> base.multiply(aux), vector);
		return v.getHead().sum();
	}

	public double length() {
		return head.distance(Point.ORIGIN);
	}

	public double squareLength() {
		return head.squareDistance(Point.ORIGIN);
	}

	public Vector normalized() {
		return scale(1 / length());
	}

	@Override
	public boolean equals(Object v) {
		if (this == v) {
			return true;
		}
		if (v == null || getClass() != v.getClass()) {
			return false;
		}
		return head.equals(((Vector) v).head);
	}

	@Override
	public int hashCode() {
		return head.hashCode();
	}

	@Override
	public String toString() {
		return head.toString();
	}
}
