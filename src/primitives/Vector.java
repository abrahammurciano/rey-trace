package primitives;

public class Vector {
	private Point head;

	public Vector(double x, double y, double z) {
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

	public Vector add(Vector vector) {
		return new Vector(getHead().add(vector));
	}

	public Vector subtract(Vector vector) {
		return add(vector.scale(-1));
	}

	public Vector scale(double factor) {
		return new Vector(getHead().getCoordinate(0).getValue() * factor,
				getHead().getCoordinate(1).getValue() * factor,
				getHead().getCoordinate(2).getValue() * factor);
	}

	public Vector crossProduct(Vector vector) {
		double[] coordinates = new double[3];
		for (int i = 0; i < coordinates.length; ++i) {
			coordinates[i] = getHead().getCoordinate(i + 1 % coordinates.length).getValue()
					* vector.getHead().getCoordinate(1 + 2 % coordinates.length).getValue()
					- getHead().getCoordinate(i + 2 % coordinates.length).getValue()
							* vector.getHead().getCoordinate(i + 1 % coordinates.length).getValue();
		}
		return new Vector(coordinates[0], coordinates[1], coordinates[2]);
	}

	public double dotProduct(Vector vector) {
		double dotProduct = 0;
		for (int i = 0; i < 3; ++i) {
			dotProduct += getHead().getCoordinate(i).getValue()
					* vector.getHead().getCoordinate(i).getValue();
		}
		return dotProduct;
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
