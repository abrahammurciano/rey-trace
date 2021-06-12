package lighting;

import math.compare.DoubleCompare;
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
public class NarrowSpotlight extends Spotlight {

	private final double focus;

	/**
	 * Construct a {@link Spotlight} from a {@link Colour}, a direction, and 3 doubles that represent the attenuation
	 * constants and the focus factor.
	 *
	 * @param colour    The colour of the light
	 * @param position  The position in space of the light source
	 * @param direction The direction of the spot light
	 * @param q         The quadratic factor
	 * @param l         The linear factor
	 * @param c         The constant factor
	 * @param focus     The focus factor, between 0 and 1. (The closer to 0, the wider the spotlight)
	 */
	public NarrowSpotlight(Colour colour, Point position, NormalizedVector direction, double q, double l, double c,
		double focus) {
		super(colour, position, direction, q, l, c);
		if (DoubleCompare.leq(focus, 0) || DoubleCompare.geq(focus, 1)) {
			throw new IllegalArgumentException("Divergence must be between 0 and 1.");
		}
		this.focus = focus;
	}

	@Override
	protected double factor(Point point) {
		return (super.factor(point) - focus) / (1 - focus);
	}

}
