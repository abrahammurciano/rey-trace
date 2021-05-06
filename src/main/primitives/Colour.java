package primitives;

/**
 * This class represents a digital colour, with the added functionality of adding and scaling colours.
 *
 */
public class Colour {

	// Internal variable for keeping track of the rgb values
	// x = Red, y = Green, z = Blue
	private Vector rgb;

	/**
	 * Construct a new colour from the given red, green, and blue values.
	 *
	 * @param red   The red value (0 to 255).
	 * @param green The green value (0 to 255).
	 * @param blue  The blue value (0 to 255).
	 */
	public Colour(int red, int green, int blue) {
		this(new Vector(red, green, blue));
	}

	private Colour(Vector rgb) {
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
	 * Get the integer value of red of this colour 
	 * @return The integer value (0-255) of red
	 */
	public int red() {
		return getInt(rgb.x);
	}

	/**
	 * Get the integer value of green of this colour 
	 * @return The integer value (0-255) of green
	 */
	public int green() {
		return getInt(rgb.y);
	}

	/**
	 * Get the integer value of blue of this colour 
	 * @return The integer value (0-255) of blue
	 */
	public int blue() {
		return getInt(rgb.z);
	}

	/**
	 * Returns the RGB value representing the color. (Bits 24-31 are blank since alpha is unused, 16-23 are red, 8-15 are green, 0-7 are
	 * blue). The imageType will be TYPE_INT_RGB
	 *
	 * @return the RGB value of the color.
	 */
	public int rgb() {
		// TODO: Check if this messes up all of our images
		return (red() << 16) ^ (green() << 8) ^ blue();
	}

	private int getInt(double value) {
		if (value < 0)
			return 0;
		if (value > 255)
			return 255;
		return (int) Math.round(value);
	}
}
