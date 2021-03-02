package primitives;

public class Coordinate {
	private double value;

	public Coordinate(double value) {
		this.setValue(value);
	}

	public double getValue() {
		return value;
	}

	private void setValue(double value) {
		this.value = value;
	}

	// should be private?
	public Coordinate add(Coordinate c) {
		return new Coordinate(this.value + c.value);
	}
	
	public Coordinate subtract(Coordinate c) {
		return new Coordinate(this.value - c.value);
	}

	@Override
	public boolean equals(Object c) {
		if (this == c) {
			return true;
		}
		if (c == null || getClass() != c.getClass()) {
			return false;
		}
		return value == ((Coordinate) c).value;
	}

	@Override
	public int hashCode() {
		return Double.hashCode(value);
	}

	@Override
	public String toString() {
		return Double.toString(value);
	}
}
