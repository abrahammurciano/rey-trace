package primitives;

/**
 * This class represents a special kind of {@link Vector} whose length is one.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */

public class NormalizedVector extends Vector {

	/**
	 * Constructs a {@link NormalizedVector} which is a normalization of the {@link Vector} to the
	 * given {@link Point}.
	 *
	 * @param head The point towards which the vector points from the origin.
	 */
	public NormalizedVector(Point head) {
		super(head);
		normalize();
	}

	/**
	 * This constructor accepts 3 doubles and returns the appropriate {@link Vector}. The doubles
	 * MUST form a normalized vector, else the vector won't be normalized and would cause undefined
	 * behavior.
	 *
	 * @param x The value of the x {@link Coordinate}.
	 * @param y The value of the y {@link Coordinate}.
	 * @param z The value of the z {@link Coordinate}.
	 * @throws IllegalArgumentException if the {@link Vector} is the zero vector.
	 */
	private NormalizedVector(double x, double y, double z) {
		super(x, y, z);
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
	public NormalizedVector reverse() {
		return new NormalizedVector(-head().coordinate(0).value(), -head().coordinate(1).value(),
				-head().coordinate(2).value());
	}

	@Override
	public double length() {
		return 1.0;
	}

}
