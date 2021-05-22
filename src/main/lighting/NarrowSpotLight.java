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
public class NarrowSpotLight extends SpotLight {

	private final double divergence;

	/**
	 * Construct a {@link SpotLight} from a {@link Colour}, a direction, and 3 doubles that represent the attenuation
	 * constants and the divergence factor.
	 *
	 * @param colour     The colour of the light
	 * @param position   The position in space of the light source
	 * @param direction  The direction of the spot light
	 * @param q          The quadratic factor
	 * @param l          The linear factor
	 * @param c          The constant factor
	 * @param divergence The divergence factor, between 0 and 1. (The closer to 0, the wider the spotlight)
	 */
	public NarrowSpotLight(Colour colour, Point position, double q, double l, double c, NormalizedVector direction,
		double divergence) {
		super(colour, position, direction, q, l, c);
		if (DoubleCompare.leq(divergence, 0) || DoubleCompare.geq(divergence, 1)) {
			throw new IllegalArgumentException("Divergence must be between 0 and 1.");
		}
		this.divergence = divergence;
	}

	@Override
	protected double factor(Point point) {
		// f(t): [a,b] -> [c,d]
		// a=a
		// b=1
		// c=0
		// d=1
		// f(t) = (t-a/1-a)
		return (super.factor(point) - divergence) / (1 - divergence);
	}

}
