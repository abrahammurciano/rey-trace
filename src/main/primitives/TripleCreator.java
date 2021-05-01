package primitives;

/**
 * An interface for a function that takes three doubles and creates some type of {@link Triple}
 */
public interface TripleCreator {
	Triple create(double x, double y, double z);
}
