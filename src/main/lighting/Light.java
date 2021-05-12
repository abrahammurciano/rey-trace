package lighting;

import primitives.Colour;

/**
 * An abstract base class for all kinds of light.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public abstract class Light {
	/** The colour of the light. */
	public final Colour colour;

	/**
	 * A constructor to set the colour of the light.
	 *
	 * @param colour The colour of the light.
	 */
	protected Light(Colour colour) {
		this.colour = colour;
	}
}
