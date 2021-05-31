package lighting;

import primitives.Colour;
import primitives.NormalizedVector;
import primitives.Point;

/**
 * Abstract base class for all lights which have a source.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class DirectionalLight extends LightSource {

	private NormalizedVector direction;

	/**
	 * Construct a directional light.
	 *
	 * @param colour    The colour of the light.
	 * @param direction The direction of the light.
	 */
	public DirectionalLight(Colour colour, NormalizedVector direction) {
		super(colour);
		this.direction = direction;
	}

	/**
	 * Calculate the colour of the light that this light source is projecting onto a given point.
	 *
	 * @param point The point at which to calculate the colour of the light.
	 * @return The colour of the light at the given point.
	 */
	@Override
	public Colour colourAt(Point point) {
		return colour;
	}

	/**
	 * Calculate a {@link NormalizedVector} from the source of the light to the given point.
	 *
	 * @param point The point the vector will point to from the light source.
	 * @return The normalized vector from the light source to the given point.
	 */
	@Override
	public NormalizedVector vectorTo(Point point) {
		return direction;
	}

	@Override
	public double squareDistance(Point point) {
		return Double.POSITIVE_INFINITY;
	}
}
