package primitives;

/**
 * An interface for a function that takes three doubles and returns a new {@link VectorBase}.
 */
public interface VectorBaseCreator extends TripleCreator {
	@Override
	VectorBase create(double x, double y, double z);
}
