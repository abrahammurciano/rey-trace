package lighting;

import primitives.Colour;
import primitives.NormalizedVector;
import primitives.Point;

/**
 * This class is represents an directional light source that attenuates with
 * respect to distance.
 *
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class SpotLight extends PointLight {

	private final NormalizedVector direction;

	/**
	 * Construct a {@link SpotLight} from a {@link Colour}, a direction, and 3
	 * doubles that represent the attenuation constants.
	 *
	 * @param colour    The colour of the light
	 * @param position  The position in space of the light source
	 * @param direction The direction of the spot light
	 * @param q         The quadratic factor
	 * @param l         The linear factor
	 * @param c         The constant factor
	 */
	public SpotLight(Colour colour, Point position, NormalizedVector direction, double q, double l, double c) {
		super(colour, position, q, l, c);
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
		return super.colourAt(point).scale(Math.max(factor(point), 0));
	}

	/**
	 * Calculate the factor by which to scale the colour at a given point, based on the direction of the spotlight.
	 *
	 * @param point The intersection point of the light at which to calculate the factor.
	 * @return The factor to scale the colour at a point.
	 */
	protected double factor(Point point) {
		return direction.dot(vectorTo(point));
	}

}
