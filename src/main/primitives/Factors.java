package primitives;

/**
 * This class represents attenuation factors for the red, green, and blue channels. These are to be used to scale
 * colours. Using these, geometries can look a certain colour.
 */
public class Factors {
	/** The maximum value of Factors. That is (1, 1, 1). */
	public static final Factors ONE = new Factors(1);
	/** The minimum value of Factors. That is (0, 0, 0). */
	public static final Factors ZERO = new Factors(0);

	final VectorBase values;

	/**
	 * Construct a factors object.
	 *
	 * @param red   The red factor.
	 * @param green The green factor.
	 * @param blue  The blue factor.
	 */
	public Factors(double red, double green, double blue) {
		this(new VectorBase(red, green, blue));
	}

	/**
	 * Construct a factors object with the same value for all channels.
	 *
	 * @param value The value for all channels.
	 */
	public Factors(double value) {
		this(new VectorBase(value, value, value));
	}

	private Factors(VectorBase values) {
		this.values = values;
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

	public Factors scale(Factors factors) {
		return new Factors(values.transform((a, b) -> a * b, factors.values, VectorBase::new));
	}
}
