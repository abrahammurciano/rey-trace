package primitives;

/**
 * An interface for a function that takes three doubles and creates some type of {@link Triple}
 */
public interface TripleCreator {
	/**
	 * A function that takes three doubles and creates some type of {@link Triple}.
	 *
	 * @param x The value of the x coordinate.
	 * @param y The value of the y coordinate.
	 * @param z The value of the z coordinate.
	 * @return The new {@link Triple} with the given coordinates.
	 */
	Triple create(double x, double y, double z);
}
