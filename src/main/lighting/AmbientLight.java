package lighting;

import primitives.Colour;

/**
 * This light models the small amounts of light which reflected so many times that their source is indeterminable. All
 * geometry intersections will be affected by at least this light.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class AmbientLight extends Light {

	/**
	 * A constructor to set the colour of the ambient light.
	 *
	 * @param colour The colour of the ambient light.
	 */
	public AmbientLight(Colour colour) {
		super(colour);
	}
}
