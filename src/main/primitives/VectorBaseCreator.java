package primitives;

public interface VectorBaseCreator extends TripleCreator {
	@Override
	VectorBase create(double x, double y, double z);
}
