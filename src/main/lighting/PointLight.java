package lighting;

import math.equations.Quadratic;
import primitives.Colour;
import primitives.NormalizedVector;
import primitives.Point;

/**
 * This class is represents an omni-directional light source that attenuates
 * with respect to distance, similar to a light bulb.
 *
 *
 * @author Eli Levin
 * @author Abraham Murciano
 */
public class PointLight extends LightSource {

	private final Quadratic factors;
	private final Point position;

	/**
	 * Construct a {@link PointLight} from a {@link Colour} and 3 doubles that represent the attenuation constants.
	 *
	 * @param colour   The colour of the light
	 * @param position The position in space of the light source
	 * @param q        The quadratic factor
	 * @param l        The linear factor
	 * @param c        The constant factor
	 */
	public PointLight(Colour colour, Point position, double q, double l, double c) {
		super(colour);
		this.position = position;
		this.factors = new Quadratic(q, l, c);
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
		double d = position.distance(point);
		return colour.scale(1 / factors.substitute(d));
	}

	/**
	 * Calculate a {@link NormalizedVector} from the source of the light to the
	 * given point.
	 *
	 * @param point The point the vector will point to from the light source.
	 * @return The normalized vector from the light source to the given point.
	 */
	@Override
	public NormalizedVector vectorTo(Point point) {
		return position.nonZeroVectorTo(point).normalized();
	}

	@Override
	public double squareDistance(Point point) {
		return position.squareDistance(point);
	}
}
