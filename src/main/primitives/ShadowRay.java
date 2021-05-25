package primitives;

import lighting.LightSource;

public class ShadowRay extends Ray {
	/** The square of the distance between the source and the light source. */
	public final double squareDistance;

	/**
	 * Construct a shadow ray.
	 *
	 * @param source         The point where the ray starts.
	 * @param direction      The direction of the ray.
	 * @param squareDistance The square of the distance between the source and the light.
	 */
	public ShadowRay(Point source, NormalizedVector direction, LightSource light) {
		super(source, direction);
		this.squareDistance = light.squareDistance(source);
	}

}
