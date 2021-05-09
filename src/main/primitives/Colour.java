package primitives;

/**
 * This class represents a digital colour, with the added functionality of adding and scaling colours.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class Colour {

	// Internal variable for keeping track of the rgb values
	// x = Red, y = Green, z = Blue
	private VectorBase rgb;

	/** Black. */
	public static final Colour BLACK = new Colour(0, 0, 0);

	/**
	 * Construct a new colour from the given red, green, and blue values. Negative values will be treated as 0 and
	 * values over 255 will be treated as 255.
	 *
	 * @param red   The red value (0 to 255).
	 * @param green The green value (0 to 255).
	 * @param blue  The blue value (0 to 255).
	 */
	public Colour(int red, int green, int blue) {
		this(new VectorBase(red, green, blue));
	}

	private Colour(VectorBase rgb) {
		this.rgb = rgb;
	}

	/**
	 * Add two colours together and return a new colour. Adding two colours means adding their red values as the new red
	 * value, etc.
	 *
	 * @param colour The colour to add with this colour.
	 * @return The new colour which is the sum of the two colours.
	 */
	public Colour add(Colour colour) {
		return new Colour(this.rgb.add(colour.rgb));
	}

	/**
	 * Scale this colour and return a new colour. To scale a colour means to multiply the red, green, and blue values by
	 * some scale factor.
	 *
	 * @param factor The factor to scale this colour by.
	 * @return The new colour which is the result of the scale.
	 */
	public Colour scale(double factor) {
		return new Colour(this.rgb.scale(factor));
	}

	/**
	 * Get the integer value of red of this colour.
	 *
	 * @return The integer value (0-255) of red.
	 */
	public int red() {
		return getInt(rgb.x);
	}

	/**
	 * Get the integer value of green of this colour.
	 *
	 * @return The integer value (0-255) of green.
	 */
	public int green() {
		return getInt(rgb.y);
	}

	/**
	 * Get the integer value of blue of this colour.
	 *
	 * @return The integer value (0-255) of blue.
	 */
	public int blue() {
		return getInt(rgb.z);
	}

	/**
	 * Returns the RGB value representing the color. (Bits 24-31 255 i.e. fully opaque, 16-23 are red, 8-15
	 * are green, 0-7 are blue). The imageType will be TYPE_INT_RGB.
	 *
	 * @return the RGB value of the color.
	 */
	public int rgb() {
		return 0xFF000000 ^ (red() << 16) ^ (green() << 8) ^ blue();
	}

	private int getInt(double value) {
		if (value < 0)
			return 0;
		if (value > 255)
			return 255;
		return (int) Math.round(value);
	}

	/**
	 * Calculate the average between any number of colours.
	 *
	 * @param colours The colours whose average to calculate.
	 * @return The average colour of the given colours.
	 */
	public static Colour average(Colour... colours) {
		Colour result = BLACK;
		for (Colour colour : colours) {
			result = result.add(colour);
		}
		return result.scale(1d / colours.length);
	}
}
