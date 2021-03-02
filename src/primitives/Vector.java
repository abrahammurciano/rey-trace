package primitives;

public class Vector {
	private Point head;

	public Vector(double x, double y, double z) {
		this(new Point(x, y, z));
	}

	public Vector(Point head) {
		if (head == Point.ORIGIN) {
			throw new IllegalArgumentException("Error: Zero vector is not allowed.");
		}
		this.head = head;
	}

	public Point getHead() {
		return head;
	}

	public Vector add(Vector vector) {
		// TODO: implement
		return null;
	}

	public Vector subtract(Vector vector) {
		return add(vector.scale(-1));
	}

	public Vector scale(double factor) {
		// TODO: implement
		return null;
	}

	public Vector crossProduct(Vector vector) {
		// TODO: implement
		return null;
	}

	public double dotProduct(Vector vector) {
		// TODO: implement
		return 0;
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
