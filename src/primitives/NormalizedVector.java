package primitives;

/**
 * This class represents a special kind of {@link Vector} whose length is one.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */

public class NormalizedVector extends Vector {

	/**
	 * Constructs a {@link NormalizedVector} from a not necessarily normalized {@link Vector}
	 *
	 * @param v The unnormalized vector.
	 */
	public NormalizedVector(Vector v) {
		super(v.scale(1 / v.length()).head);
	}

	/**
	 * This private constructor does not normalize the vector. Make sure that the head is on the unit sphere before calling
	 * this.
	 *
	 * @param head The head of the vector. It will be stored exactly as passed so make sure it makes a vector with length
	 *             equal to one.
	 * @param __   This is a dummy variable to distinguish this constructor from {@link #NormalizedVector(Point)}
	 * @throws ZeroVectorException if the given {@link Point} is the origin.
	 */
	private NormalizedVector(Point head, boolean __) {
		super(head);
	}

	/**
	 * This constructor accepts 3 doubles and returns the appropriate {@link Vector}.
	 *
	 * @param x The value of the x {@link Coordinate}.
	 * @param y The value of the y {@link Coordinate}.
	 * @param z The value of the z {@link Coordinate}.
	 * @throws ZeroVectorException if the x, y, and z are all zero.
	 */
	public NormalizedVector(double x, double y, double z) {
		this(new Vector(x, y, z));
	}

	/**
	 * This constructor accepts 3 {@link Coordinate}s and returns the appropriate {@link Vector}
	 *
	 * @param x The x {@link Coordinate}.
	 * @param y The y {@link Coordinate}.
	 * @param z The z {@link Coordinate}.
	 * @throws ZeroVectorException if the x, y, and z are all zero.
	 */
	public NormalizedVector(Coordinate x, Coordinate y, Coordinate z) {
		this(new Vector(x, y, z));
	}


	/**
	 * Calculates a new {@link NormalizedVector} with the opposite direction to this vector.
	 *
	 * @return Since the magnitude is unchanged, the returned {@link Vector} is a {@link NormalizedVector}.
	 */
	@Override
	public NormalizedVector reversed() {
		return new NormalizedVector(head.transform((c1, c2) -> c1.multiply(-1)), true);
	}


	/**
	 * Gets the length of the vector.
	 *
	 * @return 1.0 since a normalized vector by definition has a length of one.
	 */
	@Override
	public double length() {
		return 1.0;
	}

}
