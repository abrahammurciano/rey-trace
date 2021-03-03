package primitives;

/**
 * The Coordinate class represents a distance along a single axis.
 *
 * @author Abraham Murciano and Eli Levin
 */

public class Coordinate {
	private double value;

	/**
	 * Constructs a coordinate.
	 *
	 * @param value The value of the coordinate.
	 */
	public Coordinate(double value) {
		this.value = value;
	}

	/**
	 * Gets the value of the coordinate.
	 *
	 * @return double The value of the coordinate.
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Adds this coordinate with another coordinate. Effectively adds their values and constructs a
	 * new coordinate with the resulting value.
	 *
	 * @param c The coordinate to add to this coordinate.
	 * @return A new coordinate whose value is the sum of the values of the two coordinates.
	 */
	public Coordinate add(Coordinate c) {
		return new Coordinate(getValue() + c.value);
	}

	/**
	 * Subtracts another coordinate from this coordinate. Effectively subtracts their values and
	 * constructs a new coordinate with the resulting value.
	 *
	 * @param c The coordinate to subtract from this coordinate.
	 * @return A new coordinate whose value is the difference of the values of the two coordinates.
	 */
	public Coordinate subtract(Coordinate c) {
		return new Coordinate(getValue() - c.value);
	}

	/**
	 * Multiplies this coordinate by a double. Effectively multiplies its value with the double and
	 * constructs a new coordinate with the resulting value.
	 *
	 * @param d The double to multiply this coordinate with.
	 * @return A new coordinate whose value is the product of the values of this coordinate and the
	 *         given double.
	 */
	public Coordinate multiply(double d) {
		return new Coordinate(getValue() * d);
	}

	/**
	 * Multiplies this coordinate with another coordinate. Effectively multiplies their values and
	 * constructs a new coordinate with the resulting value.
	 *
	 * @param c The coordinate to multiply with this coordinate.
	 * @return A new coordinate whose value is the product of the values of the two coordinates.
	 */
	public Coordinate multiply(Coordinate c) {
		return multiply(c.getValue());
	}

	/**
	 * Compares the values of two coordinates.
	 */
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

	/**
	 * Computes the hash function based on that of the coordinate's value.
	 */
	@Override
	public int hashCode() {
		return Double.hashCode(value);
	}

	/**
	 * Returns the value of the coordinate as a string.
	 */
	@Override
	public String toString() {
		return Double.toString(value);
	}
}
