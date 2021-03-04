package primitives;

/**
 * This interface is intended to represent lambdas which take two coordinates and return a new
 * {@link Coordinate} which is a transformation of the first, possibly using the second to aid the
 * calculation.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public interface CoordinateTransformation {
	Coordinate transform(Coordinate base, Coordinate auxiliary);
}
