package lighting;

import primitives.Colour;

/**
 * This light models the small ammounts of light which reflected so many times that their source is indeterminable. All
 * geometry intersections will be affected by at least this light.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class AmbientLight {

	/** The colour of the ambient light. */
	public final Colour colour;

	/**
	 * Construct an ambient light with the specified colour.
	 *
	 * @param colour The colour to set the ambient light to.
	 */
	public AmbientLight(Colour colour) {
		this.colour = colour;
	}
}