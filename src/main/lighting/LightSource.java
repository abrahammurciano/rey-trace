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
public abstract class LightSource extends Light {
	protected LightSource(Colour colour) {
		super(colour);
	}

	/**
	 * Calculate the colour of the light that this light source is projecting onto a given point.
	 *
	 * @param point The point at which to calculate the colour of the light.
	 * @return The colour of the light at the given point.
	 */
	public abstract Colour colourAt(Point point);

	/**
	 * Calculate a {@link NormalizedVector} from the source of the light to the given point.
	 *
	 * @param point The point the vector will point to from the light source.
	 * @return The normalized vector from the light source to the given point.
	 */
	public abstract NormalizedVector vectorTo(Point point);
}
