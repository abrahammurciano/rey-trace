package primitives;

/**
 * This interface is intended to represent lambdas which take two coordinates and return a new {@link Coordinate} which
 * is a transformation of the first, possibly using the second to aid the calculation.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public interface CoordinateTransformation {
	/**
	 * A function which takes two coordinates and returns a new {@link Coordinate} which is a transformation of the first,
	 * optionally using the second to aid the calculation.
	 *
	 * @param base      The base coordinate to be transformed.
	 * @param auxiliary The auxiliary coordinate to aid the calculation.
	 * @return A new {@link Coordinate} which is a transformation of the first.
	 */
	Coordinate transform(Coordinate base, Coordinate auxiliary);
}
