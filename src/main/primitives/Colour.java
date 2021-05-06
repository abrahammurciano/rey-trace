package primitives;

/**
 * This class represents a digital colour, with the added functionality of adding and scaling colours.
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class Colour {
	/**
	 * Construct a new colour from the given red, green, and blue values. Negative values will be treated as 0 and
	 * values over 255 will be treated as 255.
	 *
	 * @param red   The red value (0 to 255).
	 * @param green The green value (0 to 255).
	 * @param blue  The blue value (0 to 255).
	 */
	public Colour(int red, int green, int blue) {
		// TODO: implement
		// Idea: we can use a VectorBase internally to store the values, thus allowing us to delegate add and scale to
		// VectorBase (see why I wanted to call this one Vector? It's more general and we can use it everywhere. also
		// colour is a vector).
	}

	/**
	 * Add two colours together and return a new colour. Adding two colours means adding their red values as the new red
	 * value, etc.
	 *
	 * @param colour The colour to add with this colour.
	 * @return The new colour which is the sum of the two colours.
	 */
	public Colour add(Colour colour) {
		// TODO: implement
		return null;
	}

	/**
	 * Scale this colour and return a new colour. To scale a colour means to multiply the red, green, and blue values by
	 * some scale factor.
	 *
	 * @param factor The factor to scale this colour by.
	 * @return The new colour which is the result of the scale.
	 */
	public Colour scale(double factor) {
		// TODO: implement
		return null;
	}

	/**
	 * Returns the RGB value representing the color. (Bits 24-31 are alpha, 16-23 are red, 8-15 are green, 0-7 are
	 * blue).
	 *
	 * @return the RGB value of the color.
	 */
	public int rgb() {
		// BufferedImage, as used by ImageWriter, needs only the RGB value (int) to write a pixel. So no point exposing
		// the actual java colour. That is just implementation detail (if we want to use it... (bitwise operations are
		// fun)).
		// TODO: implement
		return 0;
	}
}
