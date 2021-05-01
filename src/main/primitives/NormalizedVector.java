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
		this(vector.x, vector.y, vector.z, vector.length());
	}

	/**
	 * This constructor relies on the length given being correct in order to normalize the vector. If it is incorrect,
	 * the vector won't be normalized.
	 *
	 * @param head The head of the vector. It will be stored exactly as passed so make sure it makes a vector with
	 *        length equal to one.
	 * @param length The length of the vector formed by x, y, and z.
	 * @throws ZeroVectorException if the given {@link Point} is the origin.
	 */
	private NormalizedVector(double x, double y, double z, double length) {
		super(x / length, y / length, z / length);
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
		return new NormalizedVector(-x, -y, -z, 1);
	}

	/**
	 * Gets the length of the vector.
	 *
	 * @return 1.0 since a normalized vector by definition has a length of one.
	 */
	@Override
	public double length() {
		return 1;
	}

	/**
	 * Gets the length of the vector squared.
	 *
	 * @return 1.0 since a normalized vector by definition has a length of one.
	 */
	@Override
	public double squareLength() {
		return 1;
	}
}
