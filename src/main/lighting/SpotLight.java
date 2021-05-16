package lighting;

import primitives.Colour;
import primitives.NormalizedVector;
import primitives.Point;

/**
 * This class is represents an directional light source that attenuates
 * with respect to distance.
 *
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class SpotLight extends PointLight {

	private NormalizedVector direction;

	/**
	 * Construct a {@link SpotLight} from a {@link Colour}, a direction, and 3
	 * doubles that represent the attenuation constants.
	 * 
	 * @param colour    The colour of the light
	 * @param position  The position in space of the light source
	 * @param direction The direction of the spot light
	 * @param a         The quadratic factor
	 * @param b         The linear factor
	 * @param c         The constant factor
	 */
	public SpotLight(Colour colour, Point position, NormalizedVector direction, double c, double l, double q) {
		super(colour, position, c, l, q);
		this.direction = direction;
	}

	/**
	 * Calculate the colour of the light that this light source is projecting onto a
	 * given point.
	 *
	 * @param point The point at which to calculate the colour of the light.
	 * @return The colour of the light at the given point.
	 */
	@Override
	public Colour colourAt(Point point) {
		double dirDotL = direction.dot(vectorTo(point));
		return super.colourAt(point).scale(dirDotL > 0 ? dirDotL : 0);
	}
}
