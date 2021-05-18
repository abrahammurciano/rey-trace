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
	private double divergence;

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
	public SpotLight(Colour colour, Point position, double q, double l, double c, NormalizedVector direction) {
		super(colour, position, q, l, c);
		this.direction = direction;
		this.divergence = 0;
	}

	/**
	 * Construct a {@link SpotLight} from a {@link Colour}, a direction, and 3
	 * doubles that represent the attenuation constants.
	 * 
	 * @param colour    The colour of the light
	 * @param position  The position in space of the light source
	 * @param q         The quadratic factor
	 * @param l         The linear factor
	 * @param c         The constant factor
	 * @param direction The direction of the spot light
	 * @param divergence The divergence of the spot light
	 */
	public SpotLight(Colour colour, Point position, double q, double l, double c, NormalizedVector direction, double divergence) {
		this(colour, position, q, l, c, direction);
		this.direction = direction;
		if (divergence > 1 || divergence < 0) {
			throw new IllegalArgumentException("Divergence must be between 1 and 0.");
		}
		this.divergence = divergence;

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
		return super.colourAt(point).scale(dirDotL > divergence ? dirDotL : divergence);
	}
}
