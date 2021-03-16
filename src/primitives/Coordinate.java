package primitives;

/**
 * The {@link Coordinate} class represents a distance along a single axis.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Coordinate {
	/**
	 * The value of the {@link Coordinate} along its axis.
	 */
	final double val;

	/**
	 * Constructs a {@link Coordinate}.
	 *
	 * @param val The value of the {@link Coordinate}.
	 */
	public Coordinate(double val) {
		this.val = val;
	}

	/**
	 * Adds this {@link Coordinate} with another {@link Coordinate}. Effectively adds their values and constructs a new
	 * {@link Coordinate} with the resulting value.
	 *
	 * @param c The {@link Coordinate} to add to this {@link Coordinate}.
	 * @return A new {@link Coordinate} whose value is the sum of the values of the two coordinates.
	 */
	public Coordinate add(Coordinate c) {
		return new Coordinate(val + c.val);
	}

	/**
	 * Subtracts another {@link Coordinate} from this {@link Coordinate}. Effectively subtracts their values and constructs
	 * a new {@link Coordinate} with the resulting value.
	 *
	 * @param c The {@link Coordinate} to subtract from this {@link Coordinate}.
	 * @return A new {@link Coordinate} whose value is the difference of the values of the two coordinates.
	 */
	public Coordinate subtract(Coordinate c) {
		return new Coordinate(val - c.val);
	}

	/**
	 * Multiplies this {@link Coordinate} by a double. Effectively multiplies its value with the double and constructs a new
	 * {@link Coordinate} with the resulting value.
	 *
	 * @param d The double to multiply this {@link Coordinate} with.
	 * @return A new {@link Coordinate} whose value is the product of the values of this {@link Coordinate} and the given
	 *         double.
	 */
	public Coordinate multiply(double d) {
		return new Coordinate(val * d);
	}

	/**
	 * Multiplies this {@link Coordinate} with another {@link Coordinate}. Effectively multiplies their values and
	 * constructs a new {@link Coordinate} with the resulting value.
	 *
	 * @param c The {@link Coordinate} to multiply with this {@link Coordinate}.
	 * @return A new {@link Coordinate} whose value is the product of the values of the two coordinates.
	 */
	public Coordinate multiply(Coordinate c) {
		return multiply(c.val);
	}

	/**
	 * Compares the values of two coordinates.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Coordinate)) {
			return false;
		}
		Coordinate coordinate = (Coordinate) o;
		return Util.isZero(coordinate.val - val);
	}

	@Override
	public int hashCode() {
		return Double.hashCode(val);
	}

	/**
	 * Returns the value of the {@link Coordinate} as a string.
	 */
	@Override
	public String toString() {
		return Double.toString(val);
	}
}
