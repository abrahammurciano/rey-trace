package primitives;

public class Factors {
	public static final Factors MAX = new Factors(1, 1, 1);
	public static final Factors MIN = new Factors(0, 0, 0);

	final VectorBase values;

	public Factors(double red, double green, double blue) {
		this.values = new VectorBase(red, green, blue);
	}

	/**
	 * Returns whether or not the values of the factors are all less than some threshold.
	 *
	 * @param threshold The lower bound of the factors.
	 * @return true if all values are less than the threshold, otherwise false.
	 */
	public boolean lt(double threshold) {
		return values.x < threshold && values.y < threshold && values.z < threshold;
	}
}
