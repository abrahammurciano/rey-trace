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
	 * @param vector The unnormalized vector.
	 */
	public NormalizedVector(Vector vector) {
		super(vector.scale(1 / vector.length()));
	}

	/**
	 * This private constructor does not normalize the vector. Make sure that the head is on the unit sphere before
	 * calling this.
	 *
	 * @param head The head of the vector. It will be stored exactly as passed so make sure it makes a vector with
	 *        length equal to one.
	 * @throws ZeroVectorException if the given {@link Point} is the origin.
	 */
	private NormalizedVector(Triple triple) {
		super(triple);
	}

	/**
	 * This constructor accepts 3 doubles and returns the appropriate {@link Vector}.
	 *
	 * @param x The value of the x coordinate.
	 * @param y The value of the y coordinate.
	 * @param z The value of the z coordinate.
	 * @throws ZeroVectorException if the x, y, and z are all zero.
	 */
	public NormalizedVector(double x, double y, double z) {
		this(new Vector(x, y, z));
	}


	/**
	 * Calculates a new {@link NormalizedVector} with the opposite direction to this vector.
	 *
	 * @return Since the magnitude is unchanged, the returned {@link Vector} is a {@link NormalizedVector}.
	 */
	@Override
	public NormalizedVector reversed() {
		return new NormalizedVector(transform(c -> -c));
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
