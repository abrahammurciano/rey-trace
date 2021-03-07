package primitives;

/**
 * This class represents a special kind of {@link Vector} whose length is one.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */

public class NormalizedVector extends Vector {

	public NormalizedVector(Point head) {
		super(head);
		normalize();
	}

	/**
	 * This constructor accepts 3 doubles and returns the appropriate {@link Vector}
	 *
	 * @param x The value of the x {@link Coordinate}.
	 * @param y The value of the y {@link Coordinate}.
	 * @param z The value of the z {@link Coordinate}.
	 * @throws IllegalArgumentException if the {@link Vector} is the zero vector.
	 */
	public NormalizedVector(double x, double y, double z) {
		this(new Point(x, y, z));
	}

	/**
	 * This constructor accepts 3 {@link Coordinate}s and returns the appropriate {@link Vector}
	 *
	 * @param x The x {@link Coordinate}.
	 * @param y The y {@link Coordinate}.
	 * @param z The z {@link Coordinate}.
	 * @throws IllegalArgumentException if this {@link Vector} is the zero vector.
	 */
	public NormalizedVector(Coordinate x, Coordinate y, Coordinate z) {
		this(new Point(x, y, z));
	}

	@Override
	public double length() {
		return 1.0;
	}

}
